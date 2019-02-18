package tech.daniellas.p1;

import java.util.function.BiFunction;
import java.util.function.Function;

class PartialRight {
// This is generic function accepting two arguments function and it's first
// argument value and returning single argument function
static <A, B, C> Function<A, C> partialRight(BiFunction<A, B, C> fun, B b) {
	return a -> fun.apply(a, b);
}
}
