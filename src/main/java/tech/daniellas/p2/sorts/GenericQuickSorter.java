package tech.daniellas.p2.sorts;

import static com.empirica.funcs.Functions.partialRight;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class GenericQuickSorter {

	static <A> Collection<A> sort(
	    Comparator<A> comparator,
	    Function<Supplier<Collection<A>>, Supplier<Collection<A>>> futureSupplier,
	    int parallelism,
	    Collection<A> items) {
		return sort(comparator, futureSupplier, parallelism, 0, items).get();
	}

	static <A> Supplier<Collection<A>> sort(
	    Comparator<A> comparator,
	    Function<Supplier<Collection<A>>, Supplier<Collection<A>>> futureSupplier,
	    int parallelism,
	    int splitPhase,
	    Collection<A> items) {
		if (items.size() < 2) {
			return () -> items;
		}

		A pivot = items.iterator().next();
		Function<A, Integer> groupKeyExtractor = partialRight(comparator::compare, pivot)
		    .andThen(GenericQuickSorter::normalizeCompareResult);

		if (canSplit(splitPhase, parallelism)) {
			return futureSupplier.apply(() -> groupBy(groupKeyExtractor)
			    .andThen(flattenSorted(comparator, futureSupplier, parallelism, splitPhase + 1))
			    .apply(items));
		}

		return () -> groupBy(groupKeyExtractor)
		    .andThen(flattenSorted(comparator, futureSupplier, parallelism, splitPhase + 1))
		    .apply(items);
	}

	static boolean canSplit(int splitPhase, int parallelism) {
		return 2 << splitPhase <= parallelism;
	}

	static Integer normalizeCompareResult(Integer v) {
		if (v < 0) {
			return -1;
		}
		if (v > 0) {
			return 1;
		}

		return 0;
	}

	private static <A> Function<Collection<A>, Map<Integer, List<A>>> groupBy(Function<A, Integer> keyExtractor) {
		return items -> items.stream()
		    .collect(Collectors.groupingBy(keyExtractor));
	}

	static <A> Function<Map<Integer, List<A>>, Collection<A>> flattenSorted(
	    Comparator<A> comparator,
	    Function<Supplier<Collection<A>>, Supplier<Collection<A>>> futureSupplier,
	    int parallelism,
	    int splitPhase) {
		return map -> flatten(
		    getLt(comparator, futureSupplier, parallelism, splitPhase, map),
		    getEq(comparator, futureSupplier, parallelism, splitPhase, map),
		    getGt(comparator, futureSupplier, parallelism, splitPhase, map));
	}

	static <A> Supplier<Collection<A>> getLt(
	    Comparator<A> comparator,
	    Function<Supplier<Collection<A>>, Supplier<Collection<A>>> futureSupplier,
	    int parallelism,
	    int splitPhase,
	    Map<Integer, List<A>> map) {
		return map.containsKey(-1) ? sort(comparator, futureSupplier, parallelism, splitPhase, map.get(-1))
		    : Collections::emptyList;
	}

	static <A> Supplier<Collection<A>> getEq(
	    Comparator<A> comparator,
	    Function<Supplier<Collection<A>>, Supplier<Collection<A>>> futureSupplier,
	    int parallelism,
	    int splitPhase,
	    Map<Integer, List<A>> map) {
		return map.containsKey(0) ? () -> map.get(0) : Collections::emptyList;
	}

	static <A> Supplier<Collection<A>> getGt(
	    Comparator<A> comparator,
	    Function<Supplier<Collection<A>>, Supplier<Collection<A>>> futureSupplier,
	    int parallelism,
	    int splitPhase,
	    Map<Integer, List<A>> map) {
		return map.containsKey(1) ? sort(comparator, futureSupplier, parallelism, splitPhase, map.get(1))
		    : Collections::emptyList;
	}

	static <A> List<A> flatten(Supplier<Collection<A>> lt, Supplier<Collection<A>> eq, Supplier<Collection<A>> gt) {
		return Stream.of(lt, eq, gt)
		    .map(Supplier::get)
		    .flatMap(Collection::stream)
		    .collect(Collectors.toList());
	}
}
