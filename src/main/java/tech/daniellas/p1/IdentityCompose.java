package tech.daniellas.p1;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class IdentityCompose {

	// This is our composed function. Java compiler is not able to infer argument
	// types so we need to help it via <String> type declaration
	static Function<String, String> function = Function.<String>identity()
	    // We use method references matching expected Function<String,String> signature
	    .andThen(StringUtils::trim)
	    .andThen(StringUtils::capitalize);

	@Test
	public void shouldTrimCapitalize() {
		// Prints 'Abc'
		System.out.println(function.apply(" abc "));
	}

	// Here we do the same but usual way. If number of composed functions would be
	// higher, it wouldn't be easy to read and we would probably use intermediate
	// variables. Not a big deal for compiler but for our minds it's unnecessary
	// overhead, we need to clearly name every one.
	@Test
	public void shouldTrimCapitalizeOldWay() {
		// Prints 'Abc'
		System.out.println(StringUtils.capitalize(StringUtils.trim(" abc ")));
	}
}
