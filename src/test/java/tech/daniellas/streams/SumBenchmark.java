package tech.daniellas.streams;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public class SumBenchmark {

	@Test
	public void runBenchmarks() throws RunnerException {
		Options options = new OptionsBuilder()
		    .include(this.getClass().getSimpleName())
		    .mode(Mode.AverageTime)
		    .timeUnit(TimeUnit.MICROSECONDS)
		    .warmupIterations(2)
		    .warmupTime(new TimeValue(1, TimeUnit.SECONDS))
		    .forks(1)
		    .measurementIterations(2)
		    .measurementTime(new TimeValue(1, TimeUnit.SECONDS))
		    .result("data/benchmark-streams.json")
		    .resultFormat(ResultFormatType.JSON)
		    .build();

		new Runner(options).run();
	}

	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "10000", "100000", "1000000" })
		public int size;

		public List<Integer> list;

		@Setup
		public void setUp() {
			list = IntStream.range(0, size)
			    .mapToObj(i -> i)
			    .collect(Collectors.toList());
		}
	}

	@Benchmark
	public int sumViaForEach(Params params) {
		int res = 0;

		for (Integer item : params.list) {
			res += item;
		}

		return res;
	}

	@Benchmark
	public int sumViaStreamReduce(Params params) {
		return params.list.stream()
		    .reduce(0, Integer::sum);
	}

	@Benchmark
	public int sumViaParallelStreamReduce(Params params) {
		return params.list.parallelStream()
		    .reduce(0, Integer::sum);
	}

	@Benchmark
	public int sumViaStreamCollect(Params params) {
		return params.list.stream()
		    .collect(Collectors.summingInt(i -> i));
	}

	@Benchmark
	public int sumViaParallelStreamCollect(Params params) {
		return params.list.parallelStream()
		    .collect(Collectors.summingInt(i -> i));
	}

}
