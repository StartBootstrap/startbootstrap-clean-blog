package tech.daniellas.streams;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

public class FilterGroupTest {

	private static GroupBenchmark.Params params;

	@BeforeClass
	public static void beforeClass() {
		params = new GroupBenchmark.Params();
		params.size = 5;
		params.setUp();
	}

	@Test
	public void shouldProduceSameResults() {
		GroupBenchmark benchmark = new GroupBenchmark();

		assertThat(benchmark.forEach(params))
		    .isEqualTo(benchmark.forEachMerge(params))
		    .isEqualTo(benchmark.collect(params))
		    .isEqualTo(benchmark.collectPar(params))
		    .isEqualTo(benchmark.collectParOpt(params));

		assertThat(assertThat(benchmark.forEach(params).keySet())
		    .isEqualTo(benchmark.reducePar(params).keySet().toJavaSet()));
	}
}
