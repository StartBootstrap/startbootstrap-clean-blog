package tech.daniellas.p2.sorts;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import org.openjdk.jmh.runner.options.TimeValue;

public abstract class QuickSortersTestBase {

	protected abstract String result();

	@Test
	public void runBenchmarks() throws RunnerException {
		Options options = new OptionsBuilder()
		    .include(this.getClass().getSimpleName())
		    .mode(Mode.AverageTime)
		    .timeUnit(TimeUnit.MILLISECONDS)
		    .warmupIterations(3)
		    .warmupTime(new TimeValue(5, TimeUnit.SECONDS))
		    .forks(1)
		    .measurementIterations(5)
		    .measurementTime(new TimeValue(5, TimeUnit.SECONDS))
		    .result("reports/benchmark-" + result() + ".csv")
		    .resultFormat(ResultFormatType.CSV)
		    .build();

		new Runner(options).run();
	}

}
