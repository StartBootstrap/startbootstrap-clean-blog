package tech.daniellas.p1;

import java.util.function.BiFunction;
import java.util.function.Function;

class CurryRight {
// Curry any BiFunction<A,B,C> from right. Note the returned functions input
// arguments type swap
static <A, B, C> Function<B, Function<A, C>> curryRight(BiFunction<A, B, C> fun) {
	return b -> a -> fun.apply(a, b);
}
}
