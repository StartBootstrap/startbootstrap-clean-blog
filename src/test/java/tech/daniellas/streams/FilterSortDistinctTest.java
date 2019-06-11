package tech.daniellas.streams;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.BeforeClass;
import org.junit.Test;

public class FilterSortDistinctTest {

	private static FilterSortDistinctBenchmark.Params params;

	@BeforeClass
	public static void beforeClass() {
		params = new FilterSortDistinctBenchmark.Params();
		params.size = 10;
		params.setUp();
	}

	@Test
	public void shouldProduceSameResults() {
		FilterSortDistinctBenchmark benchmark = new FilterSortDistinctBenchmark();

		assertThat(benchmark.collect(params))
		    .isEqualTo(benchmark.forEach(params));
	}
}
