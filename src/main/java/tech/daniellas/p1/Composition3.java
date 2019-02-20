package tech.daniellas.p1;

import static tech.daniellas.p1.Partial.partial;

import java.math.BigDecimal;
import java.util.function.Function;

class Composition3 {

	// This time we handle errors with applySafe higher order function returning
	// function performing safe application. Note that value argument is moved from
	// arguments list to lambda expression in function body.
	static <A, B> Function<A, B> applySafe(Function<A, B> fun, B def) {
		return value -> {
			try {
				return fun.apply(value);
			} catch (Exception e) {
				return def;
			}
		};
	}

	// Parse given string to BigDecimal
	static BigDecimal parse(String value) {
		return new BigDecimal(value);
	}

	// Multiply function
	static BigDecimal multiply(BigDecimal multiplicand, BigDecimal value) {
		return value.multiply(multiplicand);
	}

	// This time we use partial application to create function multiplying by 100
	static Function<BigDecimal, BigDecimal> hundredMultiplier = partial(
	    Composition3::multiply,
	    BigDecimal.valueOf(100));

	// Function appending given value of any type with given suffix. We can do this
	// because every time we write 'a + "string"' Java compiler converts it to
	// 'a.toString() + "string"'
	static <A> String append(String suffix, A value) {
		return value + suffix;
	}

	// Create partially applied function from `append` with suffix ' %'
	static Function<BigDecimal, String> percentAppender = partial(
	    Composition3::append,
	    " %");

	// Create safe parsing function returning 0 in case of error
	String result = applySafe(Composition3::parse, BigDecimal.ZERO)
	    // Then multiply by 100
	    .andThen(hundredMultiplier)
	    // Then append % sign
	    .andThen(percentAppender)
	    // Call safe parsing function with 0.5
	    .apply("0.5");
}
