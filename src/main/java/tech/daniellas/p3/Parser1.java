package tech.daniellas.p3;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

class Parser1 {
	static String parsePercent(String str) {
		// We don't accept blank strings
		if (StringUtils.isBlank(str)) {
			throw new IllegalArgumentException("Number may not be blank");
		}
		// Remove white spaces
		String trimmed = str.replace(" ", "");
		// Replace comma with dot
		String replaced = trimmed.replace(',', '.');
		// Create BigDecimal
		BigDecimal percent = new BigDecimal(replaced)
		    // Multiply by 100
		    .multiply(BigDecimal.valueOf(100))
		    // Set scale to 2 fraction digits
		    .setScale(2);

		// Return appended with %
		return percent + " %";
	}
}
