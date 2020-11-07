package tech.daniellas.streams.threads;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

import tech.daniellas.streams.BenchmarkBase;

public class ParallelFilterSortDistinctBenchmark extends BenchmarkBase {

	// Minimal value used in filter
	static final Double MIN = 0.0;
	// Maximal value used in filter
	static final Double MAX = 10.0;

	public Collection<Double> operation(Params params) {
		return params.items.parallelStream()
				.filter(n -> n > MIN && n < MAX)
				.distinct()
				.sorted()
				.collect(Collectors.toList());
	}

	@Threads(2)
	@Benchmark
	public Collection<Double> benchmarkA(Params params) {
		return operation(params);
	}

	@Threads(4)
	@Benchmark
	public Collection<Double> benchmarkB(Params params) {
		return operation(params);
	}

	@Threads(6)
	@Benchmark
	public Collection<Double> benchmarkC(Params params) {
		return operation(params);
	}

	@Threads(8)
	@Benchmark
	public Collection<Double> benchmarkD(Params params) {
		return operation(params);
	}

	@Threads(10)
	@Benchmark
	public Collection<Double> benchmarkE(Params params) {
		return operation(params);
	}

	@Threads(12)
	@Benchmark
	public Collection<Double> benchmarkF(Params params) {
		return operation(params);
	}

	@Threads(14)
	@Benchmark
	public Collection<Double> benchmarkG(Params params) {
		return operation(params);
	}

	@Threads(16)
	@Benchmark
	public Collection<Double> benchmarkH(Params params) {
		return operation(params);
	}

	// Benchmark parameters
	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "100000" })
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
		return "data/benchmark-threads-streams-filter-sort-distinct-parallel.json";
	}

}
