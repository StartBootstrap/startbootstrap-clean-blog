package tech.daniellas.p2.rt;

import static tech.daniellas.p2.rt.ReferentialTransparcency.increment;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class AddTwoProof {

	// This is our function we want to prove.
	static int addTwo(int value) {
		// We claim that add 2 can be implemented by double composition of increment
		// function
		return increment(increment(value));
	}

	@Test
	public void proof() {
		// This is input value we use in our proof
		int inputValue = 1;

		// We start assertion with function under scrutiny call
		Assertions.assertThat(addTwo(inputValue))
		    // Replace by implementation
		    .isEqualTo(increment(increment(inputValue)))
		    // Replace inner increment function with result
		    .isEqualTo(increment(2))
		    // Replace increment function with result again
		    .isEqualTo(3);
	}
}
