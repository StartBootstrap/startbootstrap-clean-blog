package tech.daniellas.p1;

import java.math.BigDecimal;
import java.util.function.Function;

public class Composition2 {
// Parse given string to BigDecimal
static BigDecimal parse(String value) {
	return new BigDecimal(value);
}


static <A, B> Function<A, B> applySafe(Function<A, B> fun, B def) {
	return a -> {
		try {
			return fun.apply(a);
		} catch (Exception e) {
			return def;
		}
	};
}

// Append given value with given suffix
static <A> String append(String suffix, A value) {
	return value + suffix;
}
}
