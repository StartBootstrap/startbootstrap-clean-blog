package tech.daniellas.p1.corecompose;

import java.util.Objects;

@FunctionalInterface
public interface Function<T, R> {

	// Abstract apply method
	R apply(T t);
	
	// Default andThen implementation returning function applying given 'after'
	// function after it's own 'apply' will be called
	default <V> Function<T, V> andThen(Function<? super R, ? extends V> after) {
		// Check whether after is non null
		Objects.requireNonNull(after);
	
		// Apply given 'after' function after this 'apply' is called
		return (T t) -> after.apply(apply(t));
	}
	
	// Default compose implementation returning function applying given 'before'
	// function before it's own apply will be called
	default <V> Function<V, R> compose(Function<? super V, ? extends T> before) {
		// Check whether before is non null
		Objects.requireNonNull(before);
		
		// Apply given 'before' function before this 'apply' is called
		return (V v) -> apply(before.apply(v));
	}
}
