package tech.daniellas.p2.monads;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

import org.junit.Test;

public class CompletableFutureTest {

	static CompletableFuture<Double> divide(Double value, Double divisor) {
		// We are not able to divide by zero, so return failed future
		if (divisor == 0) {
			CompletableFuture<Double> future = CompletableFuture.supplyAsync(() -> value);

			future.completeExceptionally(new IllegalArgumentException());

			return future;
		}

		return CompletableFuture.completedFuture(value / divisor);
	}

	static CompletableFuture<Double> multiply(Double value, Double divisor) {
		return CompletableFuture.completedFuture(value * divisor);
	}

	@Test
	public void shouldDivide() throws InterruptedException, ExecutionException {
		// Here we start concurrent computation using system ForkJoin thread pool
		CompletableFuture.supplyAsync(() -> 10d)
		    .thenCompose(v -> divide(v, 2d)) // Then apply another computation once result arrives
		    .thenCompose(v -> multiply(v, 10d))// Then apply another computation
		    .thenAccept(System.out::println); // Prints "50.0"
	}

	@Test
	public void shouldFailToDivide() throws InterruptedException, ExecutionException {
		// Here we start concurrent computation using system ForkJoin thread pool
		CompletableFuture.supplyAsync(() -> 10d)
		    .thenCompose(v -> divide(v, 0d))// Then we apply another computation once result arrives
		    .thenCompose(v -> multiply(v, 10d)) // Then apply another computation
		    .handle((value, exception) -> {
			    // In case of failed future, value is null and exception contains error cause
			    if (value == null) {
				    return exception.getMessage();
			    }

			    return String.valueOf(value);
		    })
		    .thenAccept(System.out::println); // Prints "java.lang.IllegalArgumentException"
	}
}
