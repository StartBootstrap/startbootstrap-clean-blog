package tech.daniellas.p3;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

class Parser2 {
	static String parsePercent(String str) {
		// Create Optional from given string
		return Optional.ofNullable(str)
		    // Filter out blank strings
		    .filter(StringUtils::isNotBlank)
		    // Remove white spaces
		    .map(value -> value.replace(" ", ""))
		    // Replace comma with dot
		    .map(value -> value.replace(',', '.'))
		    // Create BigDecimal
		    .map(BigDecimal::new)
		    // Multiply by 100
		    .map(value -> value.multiply(BigDecimal.valueOf(100)))
		    // Set scale to 2 fraction digits
		    .map(value -> value.setScale(2))
		    // Append with %
		    .map(value -> value + " %")
		    // We should never get here, so we throw IllegalStateException
		    .orElseThrow(IllegalStateException::new);
	}
}
