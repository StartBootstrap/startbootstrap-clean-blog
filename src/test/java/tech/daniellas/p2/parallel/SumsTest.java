package tech.daniellas.p2.parallel;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.stream.LongStream;

import org.junit.Ignore;
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

@Ignore
public class SumsTest {

	@Test
	public void runBenchmarks() throws RunnerException {
		Options options = new OptionsBuilder()
		    .include(this.getClass().getSimpleName())
		    .mode(Mode.AverageTime)
		    .timeUnit(TimeUnit.MICROSECONDS)
		    .warmupIterations(2)
		    .warmupTime(new TimeValue(2, TimeUnit.SECONDS))
		    .forks(1)
		    .measurementIterations(3)
		    .measurementTime(new TimeValue(3, TimeUnit.SECONDS))
		    .result("target/benchmark-sums.csv")
		    .resultFormat(ResultFormatType.CSV)
		    .build();

		new Runner(options).run();
	}

	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "10000", })
		public long size;

		public LinkedList<Long> linkedList;

		public ArrayList<Long> arrayList;

		@Setup
		public void setUp() {
			linkedList = LongStream.range(0l, size)
			    .collect(LinkedList::new, List::add, (l, r) -> {
			    });
			arrayList = LongStream.range(0l, size)
			    .collect(ArrayList::new, List::add, (l, r) -> {
			    });
		}
	}

	@Benchmark
	public Long iterateSequentialSum(Params params) {
		return LongStream.iterate(0l, i -> i + 1)
		    .limit(params.size)
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long iterateParallelSum(Params params) {
		return LongStream.iterate(0l, i -> i + 1)
		    .limit(params.size)
		    .parallel()
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long rangeSequentialSum(Params params) {
		return LongStream.range(0l, params.size)
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long rangeParallelSum(Params params) {
		return LongStream.range(0l, params.size)
		    .parallel()
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long linkedListSequentialSum(Params params) {
		return params.linkedList.stream()
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long linkedListParallelSum(Params params) {
		return params.linkedList.stream()
		    .parallel()
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long arrayListSequentialSum(Params params) {
		return params.arrayList.stream()
		    .reduce(0l, Long::sum);
	}

	@Benchmark
	public Long arrayListParallelSum(Params params) {
		return params.arrayList.stream()
		    .parallel()
		    .reduce(0l, Long::sum);
	}
}
