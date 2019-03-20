package tech.daniellas.p2.sorts;

import static com.empirica.funcs.Functions.partialRight;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class ParallelQuickSorter {

	static <A> Collection<A> sort(
	    Comparator<A> comparator,
	    Executor executor,
	    int parallelism,
	    Collection<A> items) {
		try {
			return sort(comparator, executor, parallelism, 0, items).get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

	private static <A> Future<Collection<A>> sort(
	    Comparator<A> comparator,
	    Executor executor,
	    int parallelism,
	    int splitPhase,
	    Collection<A> items) {
		if (items.size() < 2) {
			return CompletableFuture.completedFuture(items);
		}

		A pivot = items.iterator().next();
		Function<A, Integer> groupKeyExtractor = partialRight(comparator::compare, pivot)
		    .andThen(ParallelQuickSorter::normalizeCompareResult);

		if (canSplit(splitPhase, parallelism)) {
			return CompletableFuture.supplyAsync(
			    () -> groupBy(groupKeyExtractor)
			        .andThen(flattenSorted(comparator, executor, parallelism, splitPhase + 1))
			        .apply(items),
			    executor);
		}

		return CompletableFuture.completedFuture(groupBy(groupKeyExtractor)
		    .andThen(flattenSorted(comparator, executor, parallelism, splitPhase))
		    .apply(items));
	}

	private static boolean canSplit(int splitPhase, int parallelism) {
		return 2 << splitPhase <= parallelism;
	}

	private static Integer normalizeCompareResult(Integer v) {
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

	private static <A> Function<Map<Integer, List<A>>, Collection<A>> flattenSorted(
	    Comparator<A> comparator,
	    Executor executor,
	    int parallelism,
	    int splitPhase) {
		return m -> flatten(
		    m.containsKey(-1) ? sort(comparator, executor, parallelism, splitPhase, m.get(-1))
		        : CompletableFuture.completedFuture(Collections.emptyList()),
		    m.containsKey(0) ? CompletableFuture.completedFuture(m.get(0))
		        : CompletableFuture.completedFuture(Collections.emptyList()),
		    m.containsKey(1) ? sort(comparator, executor, parallelism, splitPhase, m.get(1))
		        : CompletableFuture.completedFuture(Collections.emptyList()));
	}

	private static <A> List<A> flatten(Future<Collection<A>> lt, Future<Collection<A>> eq, Future<Collection<A>> gt) {
		return Stream.of(lt, eq, gt)
		    .map(ParallelQuickSorter::futureGet)
		    .flatMap(Collection::stream)
		    .collect(Collectors.toList());
	}

	private static <A> A futureGet(Future<A> f) {
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}
}
