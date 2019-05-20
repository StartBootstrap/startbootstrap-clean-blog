package tech.daniellas.p2.monads;

import static tech.daniellas.p2.monads.CompletableFutureTest.divide;
import static tech.daniellas.p2.monads.CompletableFutureTest.multiply;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;

import org.junit.Test;

public class CompletableFutureWithSameThreadExecutorTest {

	// Test Executor implementation
	static final Executor sameThreadExecutor = r -> r.run();

	// Actually, we don't even have to use lambda expression, we can use type
	// inference and method references like here:
	static final Executor anotherSameThreadExecutor = Runnable::run;

	@Test
	public void shouldDivide() throws InterruptedException, ExecutionException {
		// Here we start concurrent computation using same thread executor
		CompletableFuture.supplyAsync(() -> 10d, sameThreadExecutor)
		    .thenCompose(v -> divide(v, 2d)) // Then apply another computation once result arrives
		    .thenCompose(v -> multiply(v, 10d))// Then apply another computation
		    .thenAccept(System.out::println); // Prints "50.0"
	}

	@Test
	public void shouldFailToDivide() throws InterruptedException, ExecutionException {
		// Here we start concurrent computation using same thread executor
		CompletableFuture.supplyAsync(() -> 10d, anotherSameThreadExecutor)
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
