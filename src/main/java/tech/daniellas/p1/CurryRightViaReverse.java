package tech.daniellas.p1;

import static tech.daniellas.p1.FunctionsCurry.curry;
import static tech.daniellas.p1.Reverse.reverse;

import java.util.function.BiFunction;
import java.util.function.Function;

public class CurryRightViaReverse {
// Curry any BiFunction<A,B,C> using reverse
static <A, B, C> Function<B, Function<A, C>> curryRight(BiFunction<A, B, C> fun) {
	return curry(reverse(fun));
}
}