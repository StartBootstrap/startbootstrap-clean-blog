package tech.daniellas.streams;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public abstract class BenchmarkBase {

	@Test
	public void runBenchmarks() throws RunnerException {
		Options options = new OptionsBuilder()
		    .include(this.getClass().getSimpleName())
		    .forks(1)
		    .mode(Mode.Throughput)
		    .timeUnit(TimeUnit.SECONDS)
		    .warmupIterations(3)
		    .warmupTime(new TimeValue(5, TimeUnit.SECONDS))
		    .measurementIterations(10)
		    .measurementTime(new TimeValue(5, TimeUnit.SECONDS))
		    .result(reportPath())
		    .resultFormat(ResultFormatType.JSON)
		    .build();

		new Runner(options).run();
	}

	protected abstract String reportPath();

}
