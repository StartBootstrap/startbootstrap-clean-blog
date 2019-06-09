package tech.daniellas.streams;

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

public class DoubleCalculationBenchmark extends BenchmarkBase {

	// This if our calculation, takes Double type number, calculates logarithm, then
	// sine and then square root
	private static Function<Double, Double> calculation = Function.<Double>identity()
	    .andThen(Math::log)
	    .andThen(Math::sin)
	    .andThen(Math::sqrt);

	// Using collect with summing collector
	@Benchmark
	public Double collect(Params params) {
		return params.items.stream()
		    .map(calculation)
		    .collect(Collectors.summingDouble(i -> i));
	}

	// Using collect with summing collector on parallel stream
	@Benchmark
	public Double collectPar(Params params) {
		return params.items.parallelStream()
		    .map(calculation)
		    .collect(Collectors.summingDouble(i -> i));
	}

	// Using forEach
	@Benchmark
	public Double forEach(Params params) {
		Double res = 0d;

		for (Double item : params.items) {
			res += calculation.apply(item);
		}

		return res;
	}

	// Using reduce
	@Benchmark
	public Double reduce(Params params) {
		return params.items.stream()
		    .map(calculation)
		    .reduce(0d, Double::sum);
	}

	// Using reduce on parallel stream
	@Benchmark
	public Double reducePar(Params params) {
		return params.items.parallelStream()
		    .map(calculation)
		    .reduce(0d, Double::sum);
	}

	// Benchmark parameters
	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "10000", "100000", "1000000" })
		public int size;

		public List<Double> items;

		// We generate pseudo random doubles
		@Setup
		public void setUp() {
			items = IntStream.range(0, size)
			    .mapToObj(i -> RandomUtils.nextDouble())
			    .collect(Collectors.toList());
		}
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-streams-sum-double-calculation.json";
	}

}
