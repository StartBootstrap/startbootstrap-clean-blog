package tech.daniellas.p2.sorts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class SerialQuickSorterTest extends QuickSortersTestBase {

	@Override
	protected String result() {
		return "serial";
	}

	@State(Scope.Benchmark)
	public static class TestState {

		@Param({ "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000" })
		public int size;

		List<Integer> values;

		@Setup
		public void setup() throws IOException {
			values = Files.lines(Paths.get("data/sorts.txt"))
			    .map(Integer::parseInt)
			    .limit(size)
			    .collect(Collectors.toList());
		}

	}

	@Benchmark
	public Collection<Integer> shouldQuickSortSerial(TestState state) throws InterruptedException, ExecutionException {
		return SerialQuickSorter.sort(Integer::compare, state.values);
	}

}
