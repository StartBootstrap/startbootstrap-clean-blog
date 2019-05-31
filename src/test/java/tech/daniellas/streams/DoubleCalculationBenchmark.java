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

import com.empirica.funcs.Functions;

public class DoubleCalculationBenchmark extends BenchmarkBase {

	private static Function<Double, Double> calculation = Functions.of(Math::log)
	    .andThen(Math::sin)
	    .andThen(Math::sqrt);

	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "10000", "100000", "1000000" })
		public int size;

		public List<Double> items;

		@Setup
		public void setUp() {
			items = IntStream.range(0, size)
			    .mapToObj(i -> RandomUtils.nextDouble())
			    .collect(Collectors.toList());
		}
	}

	@Benchmark
	public Double forEach(Params params) {
		Double res = 0d;

		for (Double item : params.items) {
			res += calculation.apply(item);
		}

		return res;
	}

	@Benchmark
	public Double reduce(Params params) {
		return params.items.stream()
		    .map(calculation)
		    .reduce(0d, Double::sum);
	}

	@Benchmark
	public Double reduceParallel(Params params) {
		return params.items.parallelStream()
		    .map(calculation)
		    .reduce(0d, Double::sum);
	}

	@Benchmark
	public Double collect(Params params) {
		return params.items.stream()
		    .map(calculation)
		    .collect(Collectors.summingDouble(i -> i));
	}

	@Benchmark
	public Double collectParallel(Params params) {
		return params.items.parallelStream()
		    .map(calculation)
		    .collect(Collectors.summingDouble(i -> i));
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-streams-sum-double-calculation.json";
	}

}
