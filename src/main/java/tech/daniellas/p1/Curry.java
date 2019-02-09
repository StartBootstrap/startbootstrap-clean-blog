package tech.daniellas.p1;

import java.util.function.BiFunction;
import java.util.function.Function;

public class Curry {
// Curry any BiFunction<A,B,C>
static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> fun) {
	return a -> b -> fun.apply(a, b);
}

// Function taking two arguments again
static BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;

// And here we curry our sum function
Function<Integer, Function<Integer, Integer>> cumCurr = curry(sum);
}
