package tech.daniellas.p2.sorts;

import static com.empirica.funcs.Functions.partialRight;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class SerialQuickSorter {

	// Function sorting given items using given comparator
	static <A> Collection<A> sort(Comparator<A> comparator, Collection<A> items) {
		// We have 0 or 1 items, there nothing to sort
		if (items.size() < 2) {
			return items;
		}
		// Select first element as pivot value
		A pivot = items.iterator().next();
		// This is our grouping key extractor, compare item with pivot value and then
		// normalize compare result to have only -1,0 and 1 values
		Function<A, Integer> groupKeyExtractor = partialRight(comparator::compare, pivot)
		    .andThen(SerialQuickSorter::normalizeCompareResul);

		// Group items by comparing with pivot value
		return group(groupKeyExtractor)
		    // combine grouped results by flattening recursive sort calls results
		    .andThen(flattenSorted(comparator))
		    .apply(items);
	}

	// This is our compare result normalization function
	static int normalizeCompareResul(int v) {
		if (v < 0) {
			return -1;
		}
		if (v > 0) {
			return 1;
		}

		return 0;
	}

	// Grouping function, we need to have items lower than, equal and greater than
	// pivot value
	static <A> Function<Collection<A>, Map<Integer, List<A>>> group(Function<A, Integer> keyExtractor) {
		return items -> items.stream()
		    .collect(Collectors.groupingBy(keyExtractor));
	}

	// This is function returning function flattening recursive sort calls results
	static <A> Function<Map<Integer, List<A>>, List<A>> flattenSorted(Comparator<A> comparator) {
		return m -> flatten(
		    m.containsKey(-1) ? sort(comparator, m.get(-1)) : Collections.emptyList(),
		    m.containsKey(0) ? m.get(0) : Collections.emptyList(),
		    m.containsKey(1) ? sort(comparator, m.get(1)) : Collections.emptyList());

	}

	// Here we take 3 collections and flatten them
	static <A> List<A> flatten(Collection<A> lower, Collection<A> eq, Collection<A> greater) {
		return Stream.of(lower, eq, greater)
		    .flatMap(Collection::stream)
		    .collect(Collectors.toList());
	}

}
