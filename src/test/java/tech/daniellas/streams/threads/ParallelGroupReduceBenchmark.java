package tech.daniellas.streams.threads;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.junit.Ignore;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Threads;

import io.vavr.collection.HashMap;
import io.vavr.collection.List;
import io.vavr.collection.Map;
import tech.daniellas.streams.FastBenchmarkBase;

@Ignore
public class ParallelGroupReduceBenchmark extends FastBenchmarkBase {

	// This is grouping divisor value
	static final double DIVISOR = 100.0;

	private Map<Long, List<Double>> operation(Params params) {
		return params.items.parallelStream()
				.reduce(
						HashMap.empty(),
						(m, n) -> {
							Long key = Math.round(n / DIVISOR);
							
							return m.put(key, m.get(key)
									.map(l -> l.prepend(n))
									.getOrElse(() -> List.of(n)));
						},
						(l, r) -> l.merge(r, List::prependAll));
	}

	@Threads(2)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsA(Params params) {
		return operation(params);
	}

	@Threads(4)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsB(Params params) {
		return operation(params);
	}

	@Threads(6)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsC(Params params) {
		return operation(params);
	}

	@Threads(8)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsD(Params params) {
		return operation(params);
	}

	@Threads(10)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsE(Params params) {
		return operation(params);
	}

	@Threads(12)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsF(Params params) {
		return operation(params);
	}

	@Threads(14)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsG(Params params) {
		return operation(params);
	}

	@Threads(16)
	@Benchmark
	public Map<Long, List<Double>> collectParThreadsH(Params params) {
		return operation(params);
	}

	// Benchmark parameters
	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "100000" })
		public int size;

		public java.util.List<Double> items;

		@Setup
		public void setUp() {
			items = IntStream.range(0, size)
					.mapToObj(i -> RandomUtils.nextDouble())
					.collect(Collectors.toList());
		}
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-threads-streams-group-reduce-parallel.json";
	}

}
