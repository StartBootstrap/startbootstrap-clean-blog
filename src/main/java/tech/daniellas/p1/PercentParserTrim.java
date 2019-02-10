package tech.daniellas.p1;

import static java.math.BigDecimal.ZERO;
import static tech.daniellas.p1.Composition3.applySafe;
import static tech.daniellas.p1.Composition3.hundredMultiplier;
import static tech.daniellas.p1.Composition3.percentAppender;

import java.util.function.Function;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;
import org.junit.Test;

public class PercentParserTrim {

	// Let's create basic percent parser but this time we use
	// NumberUtils.createBigDecimal() function from Commons Lang, so we can erase
	// and stop maintaining our parse function.
	static Function<String, String> parser = applySafe(NumberUtils::createBigDecimal, ZERO)
	    .andThen(hundredMultiplier)
	    .andThen(percentAppender);

	// Use compose with String trimming function from Commons Lang to trim argument
	// before percentParser application
	static Function<String, String> trimmingParser = parser.compose(StringUtils::trim);

	@Test
	public void shouldPrintPercent() {
		// Prints '50 %'
		System.out.println(trimmingParser.apply(" 0.5 "));
	}

	@Test
	public void shouldPrintZero() {
		// Prints '0 %'
		System.out.println(trimmingParser.apply(" x "));
	}
}
