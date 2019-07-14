package tech.daniellas.p3;

import java.util.Optional;
import java.util.function.Supplier;

import org.junit.Test;

public class Mapping {

	static <A> Runnable valuePrinter(A value) {
		return () -> System.out.println(value);
	}

	@Test
	public void shouldMimicIfPresentOrElse() {
		// Prints 1
		Optional.of(1)
		    .map(Mapping::valuePrinter)
		    .orElseGet(() -> valuePrinter("Or else"))
		    .run();

		// Prints 'Or else'
		Optional.ofNullable(null)
		    .map(Mapping::valuePrinter)
		    .orElseGet(() -> valuePrinter("Or else"))
		    .run();
	}

	static Supplier<Integer> negate(Integer n) {
		return () -> -n;
	}

	@Test
	public void shouldMapToFunction() {
		Integer v = Optional.of(1)
		    .filter(n -> n < 0)
		    .map(Mapping::negate)
		    .orElse(() -> 1)
		    .get();
		
		System.out.println(v);
	}
}
