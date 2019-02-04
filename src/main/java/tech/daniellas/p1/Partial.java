package tech.daniellas.p1;

import java.util.function.BiFunction;
import java.util.function.Function;

class Partial {
// This is generic function accepting two arguments function and it's first
// argument value and returning single argument function
static <A, B, C> Function<B, C> partial(BiFunction<A, B, C> fun, A a) {
	return b -> fun.apply(a, b);
}
}
