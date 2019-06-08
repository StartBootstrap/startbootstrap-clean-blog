package tech.daniellas.streams;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
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

public class GroupBenchmark extends BenchmarkBase {

	static final double DIVISOR = 100.0;

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
			Double key = item / DIVISOR;
			List<Double> list = res.get(key);

			if (list != null) {
				list.add(item);
			} else {
				list = new ArrayList<>();
				list.add(item);
				res.put(key, list);
			}
		}

		return res;
	}

	@Benchmark
	public Map<Double, List<Double>> forEachLinked(Params params) {
		Map<Double, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			Double key = item / DIVISOR;
			List<Double> list = res.get(key);

			if (list != null) {
				list.add(item);
			} else {
				list = new LinkedList<>();
				list.add(item);
				res.put(key, list);
			}
		}

		return res;
	}

	@Benchmark
	public Map<Double, List<Double>> forEachMerge(Params params) {
		Map<Double, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			res.merge(
			    item / DIVISOR,
			    new LinkedList<>(Collections.singletonList(item)),
			    (k, v) -> {
				    v.add(item);

				    return v;
			    });
		}

		return res;
	}

	@Benchmark
	public Map<Double, List<Double>> collect(Params params) {
		return params.items.stream()
		    .collect(Collectors.groupingBy(n -> n / DIVISOR));
	}

	@Benchmark
	public Map<Double, List<Double>> collectLinked(Params params) {
		return params.items.stream()
		    .collect(Collectors.groupingBy(
		        n -> n / DIVISOR,
		        Collectors.toCollection(LinkedList::new)));
	}

	@Benchmark
	public Map<Double, List<Double>> collectPar(Params params) {
		return params.items.parallelStream()
		    .collect(Collectors.groupingBy(n -> n / DIVISOR));
	}

	@Benchmark
	public Map<Double, List<Double>> collectParLinked(Params params) {
		return params.items.parallelStream()
		    .collect(Collectors.groupingBy(
		        n -> n / DIVISOR,
		        Collectors.toCollection(LinkedList::new)));
	}

	@Benchmark
	public Map<Double, List<Double>> collectParOpt(Params params) {
		return params.items.parallelStream()
		    .unordered()
		    .collect(Collectors.groupingByConcurrent(
		        n -> n / DIVISOR,
		        Collectors.toCollection(LinkedList::new)));
	}

	@Benchmark
	public io.vavr.collection.HashMap<Double, io.vavr.collection.List<Double>> reducePar(Params params) {
		return params.items.parallelStream()
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

	@Benchmark
	public io.vavr.collection.HashMap<Double, io.vavr.collection.List<Double>> reduceParUnord(Params params) {
		return params.items.parallelStream()
		    .unordered()
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
		return "data/benchmark-streams-group.json";
	}

}
