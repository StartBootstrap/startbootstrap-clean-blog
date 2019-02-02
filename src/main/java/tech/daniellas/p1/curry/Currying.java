package tech.daniellas.p1.curry;

import java.util.function.BiFunction;
import java.util.function.Function;

class Currying {
	// Function taking two arguments
	static BiFunction<Integer, Integer, Integer> sum = (l, r) -> l + r;

	// This is how we apply two arguments function
	Integer sumRes = sum.apply(1, 2);

	// Curried
	static Function<Integer, Function<Integer, Integer>> sumCurr = l -> r -> l + r;

	// And this is how we apply curried function
	Integer sumCurrRes = sumCurr.apply(1).apply(2);
}
