package tech.daniellas.p1;

import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

class FunctionsCurryExamples {
// Curry any BiFunction<A,B,C>
static <A, B, C> Function<A, Function<B, C>> curry(BiFunction<A, B, C> fun) {
	return a -> b -> fun.apply(a, b);
}

// Function taking two arguments again
static BiFunction<Integer, Integer, Integer> sum = (a, b) -> a + b;

// Here we create stream of 1,2,3 integers and map it with curried sum function
// with first argument equal 1
List<Integer> resCurried = Stream.of(1, 2, 3)
		.map(curry(sum).apply(1))
		.collect(Collectors.toList());

// This is equivalent using lambda expression
List<Integer> res = Stream.of(1, 2, 3)
		.map(i -> sum.apply(1, i))
		.collect(Collectors.toList());

// Or we can create our mapping function in advance. We call it inc after
// increment because sum(1,i) is just incrementation
static Function<Integer, Integer> inc = curry(sum).apply(1);

// And use our inc function in stream
List<Integer> resInc = Stream.of(1, 2, 3)
		.map(inc)
		.collect(Collectors.toList());
}
