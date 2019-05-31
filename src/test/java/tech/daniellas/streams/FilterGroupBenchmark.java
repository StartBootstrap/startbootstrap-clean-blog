package tech.daniellas.streams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

public class FilterGroupBenchmark extends BenchmarkBase {

	private static final double MIN = 1234.0;

	private static final double MAX = MIN * 987.654;

	private static final double DIVISOR = 100.0;

	@State(Scope.Benchmark)
	public static class Params {
		@Param({ "1000", "10000", "100000", "1000000" })
		public int size;

		public List<Double> items;

		@Setup
		public void setUp() {
			items = IntStream.range(0, size)
			    .mapToObj(i -> RandomUtils.nextDouble())
			    .collect(Collectors.toList());
		}
	}

	@Benchmark
	public Map<Double, List<Double>> forEach(Params params) {
		Map<Double, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			if (item > MIN && item > MAX) {
				Double key = item / DIVISOR;
				List<Double> list = res.get(key);

				if (list != null) {
					res.get(key).add(item);
				} else {
					list = new ArrayList<>();
					list.add(item);
					res.put(key, list);
				}
			}
		}

		return res;
	}

	@Benchmark
	public Map<Double, List<Double>> forEachMerge(Params params) {
		Map<Double, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			if (item > MIN && item > MAX) {
				res.merge(item / DIVISOR, new ArrayList<>(), (k, v) -> {
					v.add(item);

					return v;
				});
			}
		}

		return res;
	}

	@Benchmark
	public Map<Double, List<Double>> collect(Params params) {
		return params.items.stream()
		    .filter(n -> n > MIN && n < MAX)
		    .collect(Collectors.groupingBy(n -> n / DIVISOR));
	}

	@Benchmark
	public Map<Double, List<Double>> collectParallel(Params params) {
		return params.items.parallelStream()
		    .filter(n -> n > MIN && n < MAX)
		    .collect(Collectors.groupingBy(n -> n / DIVISOR));
	}

	@Benchmark
	public Map<Double, List<Double>> collectParallelOptimized(Params params) {
		return params.items.parallelStream()
		    .unordered()
		    .filter(n -> n > MIN && n < MAX)
		    .collect(Collectors.groupingByConcurrent(n -> n / DIVISOR));
	}

	@Benchmark
	public io.vavr.collection.HashMap<Double, io.vavr.collection.List<Double>> reduceParallel(Params params) {
		return params.items.parallelStream()
		    .filter(n -> n > MIN && n < MAX)
		    .reduce(
		        io.vavr.collection.HashMap.empty(),
		        (m, n) -> {
			        Double key = n / DIVISOR;

			        return m.put(key, m.get(key)
			            .map(l -> l.prepend(n))
			            .getOrElse(() -> io.vavr.collection.List.of(n)));
		        },
		        (l, r) -> l.merge(r, io.vavr.collection.List::prependAll));
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-streams-filter-group.json";
	}

}
