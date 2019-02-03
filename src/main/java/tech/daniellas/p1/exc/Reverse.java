package tech.daniellas.p1.exc;

import java.util.function.BiFunction;

class Reverse {
// Accept BiFunction. Note that we accept A and B input types and return
// BiFunction accepting B and A types
static <A, B, C> BiFunction<B, A, C> reverse(BiFunction<A, B, C> fun) {
	// Return BiFunction defined as lambda and apply given fun to arguments reversed
	return (a, b) -> fun.apply(b, a);
}
}
