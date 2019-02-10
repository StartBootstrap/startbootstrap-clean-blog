package tech.daniellas.p1;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class IdentityCompose {

	// This is our composed function. Java compiler is not able to infer argument
	// types so we need to help it via <String> type declaration
	static Function<String, String> function = Function.<String>identity()
	    // We use method reference matching expected Function<String,String> signature
	    .andThen(StringUtils::trim)
	    .andThen(StringUtils::capitalize);

	@Test
	public void shouldTrimCapitalize() {
		// Prints 'Abc'
		System.out.println(function.apply(" abc "));
	}
}
