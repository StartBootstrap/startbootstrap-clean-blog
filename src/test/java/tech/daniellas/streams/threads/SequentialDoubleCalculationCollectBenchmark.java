package tech.daniellas.streams.threads;

import java.util.List;
import java.util.function.Function;
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

public class SequentialDoubleCalculationCollectBenchmark extends BenchmarkBase {

	private static Function<Double, Double> calculation = Function.<Double>identity()
	    .andThen(Math::log)
	    .andThen(Math::sin)
	    .andThen(Math::sqrt);


	private Double operation(Params params) {
		return params.items.stream()
		    .map(calculation)
		    .collect(Collectors.summingDouble(Double::doubleValue));
	}

	@Threads(2)
	@Benchmark
	public Double benchmarkA(Params params) {
		return operation(params);
	}

	@Threads(4)
	@Benchmark
	public Double benchmarkB(Params params) {
		return operation(params);
	}

	@Threads(6)
	@Benchmark
	public Double benchmarkC(Params params) {
		return operation(params);
	}

	@Threads(8)
	@Benchmark
	public Double benchmarkD(Params params) {
		return operation(params);
	}

	@Threads(10)
	@Benchmark
	public Double benchmarkE(Params params) {
		return operation(params);
	}

	@Threads(12)
	@Benchmark
	public Double benchmarkF(Params params) {
		return operation(params);
	}

	@Threads(14)
	@Benchmark
	public Double benchmarkG(Params params) {
		return operation(params);
	}

	@Threads(16)
	@Benchmark
	public Double benchmarkH(Params params) {
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
		return "data/benchmark-threads-streams-sum-double-calculation-sequential-collect.json";
	}

}
