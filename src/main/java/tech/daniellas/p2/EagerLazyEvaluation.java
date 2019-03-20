package tech.daniellas.p2;

import java.util.Optional;

import org.junit.Test;

public class EagerLazyEvaluation {

// This is function returning Optional result
static Optional<Integer> find(Integer id) {
	// Optional with some value is returned if given id is not null and is greater
	// than 0
	return Optional.ofNullable(id)
	    .filter(i -> i > 0);
}

// This is function returning default value, we pretend that it takes some time
// to obtain it
static Integer defaultValue() {
	System.out.println("Heavy default value supplier");

	return Integer.MAX_VALUE;
}

@Test
public void shouldEvaluateEagerly() {
	// Prints 'Heavy default value supplier'
	find(1).orElse(defaultValue());
}

@Test
public void shouldNotEvaluate() {
	// We get some optional, so 'defaultValue' supplier is not called  
	find(1).orElseGet(EagerLazyEvaluation::defaultValue);
}

@Test
public void shouldEvaluateLazily() {
	// We get empty optional, so 'defaultValue' supplier is called  
	find(0).orElseGet(EagerLazyEvaluation::defaultValue);
}
}
