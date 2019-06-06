package tech.daniellas.streams;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class IntegerSumBenchmark extends BenchmarkBase {

	// These are benchmarks parameters
	@State(Scope.Benchmark)
	public static class Params {
		// Run with given size parameters of
		@Param({ "1000", "10000", "100000", "1000000" })
		public int size;

		// Items to run benchmark on
		public List<Integer> items;

		// Setup test data, will be run once and will not affect our results
		@Setup
		public void setUp() {
			items = IntStream.range(0, size)
			    .mapToObj(i -> i)
			    .collect(Collectors.toList());
		}
	}

	// Plain old forEach implementation
	@Benchmark
	public int forEach(Params params) {
		int res = 0;

		for (Integer item : params.items) {
			res += item;
		}

		return res;
	}

	// Using summing collector
	@Benchmark
	public int collect(Params params) {
		return params.items.stream()
		    .collect(Collectors.summingInt(i -> i));
	}

	// Using summing collector on parallel stream
	@Benchmark
	public int collectPar(Params params) {
		return params.items.parallelStream()
		    .collect(Collectors.summingInt(i -> i));
	}

	// Using reduce
	@Benchmark
	public int reduce(Params params) {
		return params.items.stream()
		    .reduce(0, Integer::sum);
	}

	// Using reduce on parallel stream
	@Benchmark
	public int reducePar(Params params) {
		return params.items.parallelStream()
		    .reduce(0, Integer::sum);
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-streams-sum-int.json";
	}

}
