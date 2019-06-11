package tech.daniellas.streams;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class FilterSortDistinctBenchmark extends BenchmarkBase {

	// Minimal value used in filter
	static final Double MIN = 0.0;
	// Maximal value used in filter
	static final Double MAX = 10.0;

	// Using collect
	@Benchmark
	public Collection<Double> collect(Params params) {
		return params.items.stream()
		    .filter(n -> n > MIN && n < MAX)
		    .distinct()
		    .sorted()
		    .collect(Collectors.toList());
	}

	// Using collect on parallel stream
	@Benchmark
	public Collection<Double> collectPar(Params params) {
		return params.items.parallelStream()
		    .filter(n -> n > MIN && n < MAX)
		    .distinct()
		    .sorted()
		    .collect(Collectors.toList());
	}

	// Using forEach
	@Benchmark
	public Collection<Double> forEach(Params params) {
		Set<Double> set = new HashSet<>();

		for (Double item : params.items) {
			if (item > MIN && item < MAX) {
				set.add(item);
			}
		}

		List<Double> res = new ArrayList<>(set);

		Collections.sort(res);

		return res;
	}

	// Benchmark parameters
	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "10000", "100000", "1000000" })
		public int size;

		public List<Double> items;

		@Setup
		public void setUp() {
			// Generate 500 random values from range MIN >= value <= MAX + 5
			List<Double> values = IntStream.range(0, 500)
			    .mapToObj(i -> RandomUtils.nextDouble(MIN, MAX + 5.0))
			    .collect(Collectors.toList());

			// Create list from random numbers within range
			items = IntStream.range(0, size)
			    .mapToObj(i -> values.get(RandomUtils.nextInt(0, values.size() - 1)))
			    .collect(Collectors.toList());
		}
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-streams-filter-sort-distinct.json";
	}

}
