package tech.daniellas.p1;

import java.util.function.BiFunction;

class Reverse {
// Accept BiFunction<A,B,C>. Note that we return BiFunction<B,A,C> - with input
// types reversed
static <A, B, C> BiFunction<B, A, C> reverse(BiFunction<A, B, C> fun) {
	// Return BiFunction defined as lambda and apply given fun to arguments reversed
	return (a, b) -> fun.apply(b, a);
}
}
