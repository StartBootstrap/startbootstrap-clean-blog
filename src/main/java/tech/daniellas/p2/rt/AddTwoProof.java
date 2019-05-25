package tech.daniellas.p2.rt;

import static tech.daniellas.p2.rt.ReferentialTransparcency.increment;

import org.assertj.core.api.Assertions;
import org.junit.Test;

/**
 * This is our 'theorem' - function adding 2 to any number can be expressed via
 * double application of increment function.
 */
public class AddTwoProof {

	// This is our function we want to prove
	static int addTwo(int value) {
		// We claim that add 2 can be implemented by double application of increment
		// function
		return increment(increment(value));
	}

	@Test
	public void proof() {
		// This is input value we use in our proof
		int x = 0;

		// We start assertion with function under scrutiny call
		Assertions.assertThat(addTwo(x))
		    // Replace by implementation
		    .isEqualTo(increment(increment(x)))
		    // Replace inner increment function with result
		    .isEqualTo(increment(x + 1))
		    // Replace increment function with result again
		    .isEqualTo(x + 1 + 1)
		    // Replace with evaluated result
		    .isEqualTo(2);
	}
}
