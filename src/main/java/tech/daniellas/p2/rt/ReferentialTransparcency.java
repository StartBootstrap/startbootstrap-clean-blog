package tech.daniellas.p2.rt;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ReferentialTransparcency {

	// This is example pure function
	static int increment(int value) {
		return value + 1;
	}

	@Test
	public void pureFunctionShouldBeReferantiallyTransparent() {
		// This is our input value
		int inputValue = 1;

		// This is value calculated by function
		int calculatedValue = increment(inputValue);
		// This is value of expression equivalent to increment function
		int expressionValue = inputValue + 1;
		// This is constant value equal increment function result for defined above input
		// value
		int constantValue = 2;

		Assertions.assertThat(calculatedValue)
		    .isEqualTo(expressionValue)
		    .isEqualTo(constantValue);
	}
}
