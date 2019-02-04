package tech.daniellas.p1;

import static tech.daniellas.p1.FunctionsCurry.curry;

import java.util.function.BiFunction;
import java.util.function.Function;

class PartialViaCurry {
// Here we implement partial via curry
static <A, B, C> Function<B, C> partial(BiFunction<A, B, C> fun, A a) {
	return curry(fun).apply(a);
}
}
