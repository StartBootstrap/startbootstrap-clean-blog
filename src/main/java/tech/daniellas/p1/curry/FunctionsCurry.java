package tech.daniellas.p1.curry;

import java.util.function.BiFunction;
import java.util.function.Function;

class FunctionsCurry {
	// Curry any BiFunction<A,B,C>
	static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> f) {
		return a -> b -> f.apply(a, b);
	}

	// Function taking two arguments again
	static BiFunction<Integer, Integer, Integer> sum = (l, r) -> l + r;

	// And here we curry our sum function
	Function<Integer, Function<Integer, Integer>> cumCurr = curry(sum);
}
