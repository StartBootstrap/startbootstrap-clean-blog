package tech.daniellas.p2.sorts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;

public class ParallelQuickSorterTest extends QuickSortersTestBase {

	@Override
	protected String result() {
		return "parallel";
	}

	@State(Scope.Benchmark)
	public static class TestState {

		@Param({ "10000", "20000", "30000", "40000", "50000", "60000", "70000", "80000", "90000", "100000" })
		public int size;

		@Param({ "2", "4", "8" })
		int parallelism;

		ExecutorService executor;

		List<Integer> values;

		@Setup
		public void setup() throws IOException {
			executor = Executors.newFixedThreadPool(parallelism);
			values = Files.lines(Paths.get("data/sorts.txt"))
			    .map(Integer::parseInt)
			    .limit(size)
			    .collect(Collectors.toList());
		}

		@TearDown
		public void tearDown() {
			executor.shutdownNow();
		}
	}

	@Benchmark
	public Collection<Integer> shouldQuickSortParallel(TestState state) throws InterruptedException, ExecutionException {
		return ParallelQuickSorter.sort(Integer::compare, state.executor, state.parallelism, state.values);
	}

}
