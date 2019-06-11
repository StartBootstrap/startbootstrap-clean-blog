package tech.daniellas.streams;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;

import org.junit.Test;

public class WfcTest {

	@Test
	public void shouldProduceSameResults() throws IOException {
		WfcBenchmark benchmark = new WfcBenchmark();

		assertThat(benchmark.java2())
		    .isEqualTo(benchmark.java8())
		    .isEqualTo(benchmark.java8step1())
		    .isEqualTo(benchmark.java8step2())
		    .isEqualTo(benchmark.java8step3())
		    .isEqualTo(benchmark.java8step4());

	}
}
