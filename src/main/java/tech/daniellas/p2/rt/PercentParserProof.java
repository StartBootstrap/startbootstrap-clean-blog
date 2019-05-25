package tech.daniellas.p2.rt;

import java.math.BigDecimal;
import java.util.function.Function;

import org.apache.commons.lang3.math.NumberUtils;
import org.assertj.core.api.Assertions;
import org.junit.Test;

public class PercentParserProof {
	/**
	 * Return function applying given fun safely, returning given def value in case
	 * of exception being thrown on fun application.
	 * 
	 * @param fun
	 *          to apply
	 * @param def
	 *          value to return in case of failure
	 * @return either fun application result or default value
	 */
	static <A, B> Function<A, B> applySafe(Function<A, B> fun, B def) {
		return value -> {
			try {
				return fun.apply(value);
			} catch (Exception e) {
				return def;
			}
		};
	}

	// This is our percent parsing function. We safely apply String to BigDecimal
	// conversion, and then multiply by 100 and then append with ' %'
	static Function<String, String> percentParser = applySafe(
	    NumberUtils::createBigDecimal, BigDecimal.ZERO)
	        .andThen(number -> number.multiply(new BigDecimal(100)))
	        .andThen(string -> string + " %");

	// Here we prove correctness for number input
	@Test
	public void numbersProof() {
		// Our input value
		String inputValue = "0.5";

		// Apply percentParser to inputValue
		Assertions.assertThat(percentParser.apply(inputValue))
		    .isEqualTo(
		        // Full implementation
		        applySafe(NumberUtils::createBigDecimal, BigDecimal.ZERO)
		            .andThen(number -> number.multiply(new BigDecimal(100)))
		            .andThen(string -> string + " %")
		            .apply(inputValue))
		    .isEqualTo(
		        // Replace applySafe with result
		        Function.<BigDecimal>identity()
		            .andThen(number -> number.multiply(new BigDecimal(100)))
		            .andThen(string -> string + " %")
		            .apply(new BigDecimal("0.5")))
		    .isEqualTo(
		        // Replace multiplication with result
		        Function.identity()
		            .andThen(string -> string + " %")
		            .apply(new BigDecimal("50.0")))
		    .isEqualTo(
		        // Replace string append with result
		        Function.identity()
		            .apply("50.0 %"))
		    // Replace with final result
		    .isEqualTo("50.0 %");
	}

	// Here we prove correctness for non number input
	@Test
	public void nonNumbersProof() {
		// Our input value
		String inputValue = "x";

		// Apply percentParser to inputValue
		Assertions.assertThat(percentParser.apply(inputValue))
		    .isEqualTo(
		        // Full implementation
		        applySafe(NumberUtils::createBigDecimal, BigDecimal.ZERO)
		            .andThen(number -> number.multiply(new BigDecimal(100)))
		            .andThen(string -> string + " %")
		            .apply(inputValue))
		    .isEqualTo(
		        // Replace applySafe with result
		        Function.<BigDecimal>identity()
		            .andThen(number -> number.multiply(new BigDecimal(100)))
		            .andThen(string -> string + " %")
		            .apply(new BigDecimal("0")))
		    .isEqualTo(
		        // Replace multiplication with result
		        Function.identity()
		            .andThen(string -> string + " %")
		            .apply(new BigDecimal("0")))
		    .isEqualTo(
		        // Replace string append with result
		        Function.identity()
		            .apply("0 %"))
		    // Replace with final result
		    .isEqualTo("0 %");
	}
}
