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

	// This is grouping divisor value
	static final double DIVISOR = 100.0;

	// Using grouping collector
	@Benchmark
	public Map<Long, List<Double>> collect(Params params) {
		return params.items.stream()
		    .collect(Collectors.groupingBy(n -> Math.round(n / DIVISOR)));
	}

	// Using grouping collector with linked list
	@Benchmark
	public Map<Long, List<Double>> collectLinked(Params params) {
		return params.items.stream()
		    .collect(Collectors.groupingBy(
		        n -> Math.round(n / DIVISOR),
		        Collectors.toCollection(LinkedList::new)));
	}

	// Using parallel stream
	@Benchmark
	public Map<Long, List<Double>> collectPar(Params params) {
		return params.items.parallelStream()
		    .collect(Collectors.groupingBy(n -> Math.round(n / DIVISOR)));
	}

	// Using parallel stream with linked list
	@Benchmark
	public Map<Long, List<Double>> collectParLinked(Params params) {
		return params.items.parallelStream()
		    .collect(Collectors.groupingBy(
		        n -> Math.round(n / DIVISOR),
		        Collectors.toCollection(LinkedList::new)));
	}

	// Using parallel unordered stream, concurrent grouping collector with linked
	// list
	@Benchmark
	public Map<Long, List<Double>> collectParOpt(Params params) {
		return params.items.parallelStream()
		    .unordered()
		    .collect(Collectors.groupingByConcurrent(
		        n -> Math.round(n / DIVISOR),
		        Collectors.toCollection(LinkedList::new)));
	}

	// Using forEach
	@Benchmark
	public Map<Long, List<Double>> forEach(Params params) {
		Map<Long, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			Long key = Math.round(item / DIVISOR);
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

	// Using forEach and linked list
	@Benchmark
	public Map<Long, List<Double>> forEachLinked(Params params) {
		Map<Long, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			Long key = Math.round(item / DIVISOR);
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

	// Using forEach, linked list and map's merge method
	@Benchmark
	public Map<Long, List<Double>> forEachMerge(Params params) {
		Map<Long, List<Double>> res = new HashMap<>();

		for (Double item : params.items) {
			res.merge(
			    Math.round(item / DIVISOR),
			    new LinkedList<>(Collections.singletonList(item)),
			    (k, v) -> {
				    v.add(item);

				    return v;
			    });
		}

		return res;
	}

	// Using parallel stream reduction with immutable map and list
	@Benchmark
	public io.vavr.collection.HashMap<Long, io.vavr.collection.List<Double>> reducePar(Params params) {
		return params.items.parallelStream()
		    .reduce(
		        io.vavr.collection.HashMap.empty(),
		        (m, n) -> {
			        Long key = Math.round(n / DIVISOR);

			        return m.put(key, m.get(key)
			            .map(l -> l.prepend(n))
			            .getOrElse(() -> io.vavr.collection.List.of(n)));
		        },
		        (l, r) -> l.merge(r, io.vavr.collection.List::prependAll));
	}

	// Using parallel unordered stream reduction with immutable map and list
	@Benchmark
	public io.vavr.collection.HashMap<Long, io.vavr.collection.List<Double>> reduceParUnord(Params params) {
		return params.items.parallelStream()
		    .unordered()
		    .reduce(
		        io.vavr.collection.HashMap.empty(),
		        (m, n) -> {
			        Long key = Math.round(n / DIVISOR);

			        return m.put(key, m.get(key)
			            .map(l -> l.prepend(n))
			            .getOrElse(() -> io.vavr.collection.List.of(n)));
		        },
		        (l, r) -> l.merge(r, io.vavr.collection.List::prependAll));
	}

	// Benchmark parameters
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

	@Override
	protected String reportPath() {
		return "data/benchmark-streams-group.json";
	}

}
