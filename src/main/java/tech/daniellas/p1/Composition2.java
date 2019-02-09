package tech.daniellas.p1;

import java.math.BigDecimal;
import java.util.function.Function;

class Composition2 {

	// Parse given string to BigDecimal
	static BigDecimal parse(String value) {
		return new BigDecimal(value);
	}
	
	// Apply given fun to given value. Return def value in case of error
	static <A, B> B applySafe(Function<A, B> fun, B def, A value) {
		try {
			return fun.apply(value);
		} catch (Exception e) {
			return def;
		}
	}
	
	// Multiply function
	static BigDecimal multiply(BigDecimal value, BigDecimal multiplicand) {
		return value.multiply(multiplicand);
	}
	
	// Append given value with given suffix
	static <A> String append(String suffix, A value) {
		return value + suffix;
	}
	
	// Call our functions
	String result = append(" %", 
			multiply(
					// We use parse function method reference as first argument
					applySafe(Composition2::parse, BigDecimal.ZERO, "0.5"), 
					BigDecimal.valueOf(100)));

}
