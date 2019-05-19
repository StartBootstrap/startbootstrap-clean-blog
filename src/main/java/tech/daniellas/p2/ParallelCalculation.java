package tech.daniellas.p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.function.Function;

public class ParallelCalculation {

	static <A, B> List<B> calculate(
	    Function<List<Callable<List<B>>>, List<Future<List<B>>>> future,
	    int parallelism,
	    Function<A, B> operation,
	    List<A> items) {
		if (items.isEmpty()) {
			return Collections.emptyList();
		}
		if (items.size() < parallelism) {
			return map(operation, items);
		}
		int step = items.size() / parallelism;
		List<Callable<List<B>>> tasks = new ArrayList<>(parallelism);

		for (int i = 0; i < parallelism; i++) {
			int start = step * i;
			int end;

			if (i < parallelism - 1) {
				end = start + step;
			} else {
				end = items.size();
			}
			tasks.add(() -> calculate(operation, items, start, end));
		}

		List<B> result = new ArrayList<>(items.size());
		List<Future<List<B>>> results = future.apply(tasks);

		for (int i = 0; i < results.size(); i++) {
			result.addAll(futureGet(results.get(i)));
		}

		return result;
	}
	
	static <A, B> List<B> calculate(Function<A, B> operation, List<A> items, int start, int end) {
		return map(operation, items.subList(start, end));
	}

	static <A, B> List<B> map(Function<A, B> mapper, List<A> items) {
		List<B> result = new ArrayList<>(items.size());

		for (A a : items) {
			result.add(mapper.apply(a));
		}

		return result;
	}

	static <A> A futureGet(Future<A> f) {
		try {
			return f.get();
		} catch (InterruptedException | ExecutionException e) {
			throw new IllegalStateException(e);
		}
	}

}
