package tech.daniellas.p1;

import static java.math.BigDecimal.ZERO;
import static tech.daniellas.p1.Composition3.applySafe;
import static tech.daniellas.p1.Composition3.hundredMultiplier;
import static tech.daniellas.p1.Composition3.percentAppender;

import java.util.Arrays;
import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class PercentParserClean {

	// This function returns function removing all occurrences of given patterns
	// array only if not empty
	static Function<String, String> patternRemover(String... patterns) {
		return patterns.length != 0
		    ? str -> StringUtils.replaceEachRepeatedly(
		        str,
		        patterns,
		        // Here we map all given patterns to empty string and create array of empty
		        // string with length equal patterns length. This is
		        // StringUtils.replaceEachRepeatedly function contract, we have to provide
		        // replacement for every element in search list
		        Arrays.stream(patterns)
		            .map(c -> "")
		            .toArray(String[]::new))
		    : Function.identity();
	}

	// This is parser
	static Function<String, String> parser = applySafe(NumberUtils::createBigDecimal, ZERO)
	    .andThen(hundredMultiplier)
	    .andThen(percentAppender);

	@Test
	public void shouldRemovePatternsBeforeParse() {
		// Prints '55.00 %'
		System.out.println(parser.compose(patternRemover(" ", "\t")).apply("\t0.5 5"));

		// Prints '55.00 %'
		System.out.println(parser.compose(patternRemover("value is:", " ")).apply("value is: 0.55"));
	}

	@Test
	public void shouldFailToParse() {
		// Prints '0 %'
		System.out.println(parser.compose(patternRemover()).apply("0.5 5"));
	}
}
