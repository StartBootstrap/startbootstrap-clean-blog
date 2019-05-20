package tech.daniellas.p2.monads;

import java.util.Optional;

import org.junit.Test;

public class OptionalTest {

	static Optional<Double> divide(Double value, Double divisor) {
		// We are not able to divide by zero, so return EMPTY
		if (divisor == 0) {
			return Optional.empty();
		}

		return Optional.of(value / divisor);
	}

	@Test
	public void shouldDivide() {
		Optional<Double> result = Optional.ofNullable(10d).flatMap(v -> divide(v, 2d));

		// Prints "Optional[5.0]"
		System.out.println(result);
	}

	@Test
	public void shouldFailToDivide() {
		Optional<Double> result = Optional.ofNullable(10d).flatMap(v -> divide(v, 0d));

		// Prints "Optional.empty"
		System.out.println(result);
	}
}
