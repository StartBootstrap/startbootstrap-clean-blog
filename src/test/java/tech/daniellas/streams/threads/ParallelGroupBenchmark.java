package tech.daniellas.streams.threads;

import java.util.List;
import java.util.Map;
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

public class ParallelGroupBenchmark extends BenchmarkBase {

	// This is grouping divisor value
	static final double DIVISOR = 100.0;

	private Map<Long, List<Double>> operation(Params params) {
		return params.items.parallelStream()
				.collect(Collectors.groupingBy(n -> Math.round(n / 100.0)));
	}

	@Threads(2)
	@Benchmark
	public Map<Long, List<Double>> benchmarkA(Params params) {
		return operation(params);
	}

	@Threads(4)
	@Benchmark
	public Map<Long, List<Double>> benchmarkB(Params params) {
		return operation(params);
	}

	@Threads(6)
	@Benchmark
	public Map<Long, List<Double>> benchmarkC(Params params) {
		return operation(params);
	}

	@Threads(8)
	@Benchmark
	public Map<Long, List<Double>> benchmarkD(Params params) {
		return operation(params);
	}

	@Threads(10)
	@Benchmark
	public Map<Long, List<Double>> benchmarkE(Params params) {
		return operation(params);
	}

	@Threads(12)
	@Benchmark
	public Map<Long, List<Double>> benchmarkF(Params params) {
		return operation(params);
	}

	@Threads(14)
	@Benchmark
	public Map<Long, List<Double>> benchmarkG(Params params) {
		return operation(params);
	}

	@Threads(16)
	@Benchmark
	public Map<Long, List<Double>> benchmarkH(Params params) {
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
			items = IntStream.range(0, size)
					.mapToObj(i -> RandomUtils.nextDouble())
					.collect(Collectors.toList());
		}
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-threads-streams-group-parallel.json";
	}

}
