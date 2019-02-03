package tech.daniellas.p1.partial;

import java.util.function.BiFunction;
import java.util.function.Function;

class PartialViaCurry {
// Curry any BiFunction<A,B,C>
static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> fun) {
	return a -> b -> fun.apply(a, b);
}
	
// Here we implement partial via curry
static <A, B, C> Function<B, C> partial(BiFunction<A, B, C> fun, A a) {
	return curry(fun).apply(a);
}
}
