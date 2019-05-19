package tech.daniellas.p2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.Function;

public class RecursiveParallelCalculation {

	static <A, B> List<B> calculate(
	    ExecutorService executor,
	    int parallelism,
	    Function<A, B> operation,
	    List<A> items) {
		if (items.isEmpty()) {
			return Collections.emptyList();
		}
		if (items.size() < parallelism) {
			return map(operation, items);
		}

		return merge(futureGet(calculate(executor, parallelism, operation, items, 0)));
	}

	static <A, B> Future<Pair<List<B>>> calculate(
	    ExecutorService executor,
	    int parallelism,
	    Function<A, B> operation,
	    List<A> items,
	    int splitPhase) {
		if (canSplit(parallelism, splitPhase)) {
			executor.submit(() -> {
				Pair<List<A>> pair = split(items);
				
				Future<Pair<List<B>>> left = calculate(executor, parallelism, operation, pair.left, splitPhase + 1);
				Future<Pair<List<B>>> right = calculate(executor, parallelism, operation, pair.right, splitPhase + 1);
			});
		}

		return new CompletedFuture<>(
		    new Pair<>(
		        map(operation, items),
		        Collections.emptyList()));
	}

	static boolean canSplit(int parallelism, int splitPhase) {
		return 2 << splitPhase < parallelism;
	}

	static <A> Pair<List<A>> split(List<A> items) {
		int pivot = items.size() / 2;

		return new Pair<>(items.subList(0, pivot), items.subList(pivot, items.size()));
	}

	static <A> List<A> merge(Pair<List<A>> pair) {
		List<A> result = new ArrayList<>(pair.left.size() + pair.right.size());

		result.addAll(pair.left);
		result.addAll(pair.right);

		return result;
	}

	static class Pair<A> {
		final A left;
		final A right;

		public Pair(A left, A right) {
			this.left = left;
			this.right = right;
		}
	}

	static class CompletedFuture<A> implements Future<A> {

		final A value;

		public CompletedFuture(A value) {
			this.value = value;
		}

		@Override
		public boolean cancel(boolean mayInterruptIfRunning) {
			throw new UnsupportedOperationException();
		}

		@Override
		public boolean isCancelled() {
			return false;
		}

		@Override
		public boolean isDone() {
			return true;
		}

		@Override
		public A get() throws InterruptedException, ExecutionException {
			return value;
		}

		@Override
		public A get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
			throw new UnsupportedOperationException();
		}

	}

	static <A, B> List<B> mapRange(Function<A, B> operation, List<A> items, int start, int end) {
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
