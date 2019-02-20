package tech.daniellas.p1;

import static tech.daniellas.p1.Composition3.hundredMultiplier;

import java.math.BigDecimal;
import java.util.function.Function;

import org.junit.Test;

public class Identity {

	@Test
	public void andThenIdentityShouldPrintArgumentUnchanged() {
		// This is our hunderMultiplier function composed with identity() function via
		// andThen
		Function<BigDecimal, BigDecimal> composed = hundredMultiplier
		    .andThen(Function.identity());

		// Prints '100'
		System.out.println(composed.apply(BigDecimal.ONE));
	}

	@Test
	public void composeIdentityShouldPrintArgumentUnchanged() {
		// This is our hunderMultiplier function composed with identity() function via
		// compose
		Function<BigDecimal, BigDecimal> composed = hundredMultiplier
		    .compose(Function.identity());

		// Prints '100'
		System.out.println(composed.apply(BigDecimal.ONE));
	}
}
