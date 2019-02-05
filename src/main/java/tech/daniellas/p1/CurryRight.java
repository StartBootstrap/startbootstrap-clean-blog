package tech.daniellas.p1;

import java.util.function.BiFunction;
import java.util.function.Function;

public class CurryRight {
// Curry any BiFunction<A,B,C> from right. Note the returned BiFunction input
// arguments type swap
static <A, B, C> Function<B, Function<A, C>> curryRight(BiFunction<A, B, C> fun) {
	return b -> a -> fun.apply(a, b);
}
}
