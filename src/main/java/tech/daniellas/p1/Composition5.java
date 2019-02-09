package tech.daniellas.p1;

import java.math.BigDecimal;
import java.util.function.Function;

class Composition5 {

	// We need this function, but it's generic enough to be reusable
	static <A, B> Function<A, B> applySafe(Function<A, B> fun, B def) {
		return value -> {
			try {
				return fun.apply(value);
			} catch (Exception e) {
				return def;
			}
		};
	}
	
	// We can use lambda expressions if we feel that composing parts will never be reused
	String result = applySafe((String value) -> new BigDecimal(value), BigDecimal.ZERO)
	    .andThen(decimal -> decimal.multiply(BigDecimal.valueOf(100)))
	    .andThen(string -> string + " %")
	    .apply("0.5");

}
