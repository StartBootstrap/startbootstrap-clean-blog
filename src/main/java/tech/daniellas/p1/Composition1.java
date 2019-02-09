package tech.daniellas.p1;

import java.math.BigDecimal;

class Composition1 {
static String formatPercent(String value) {
	try {
		BigDecimal numericValue = new BigDecimal(value);

		return numericValue.multiply(BigDecimal.valueOf(100)) + " %";
	} catch (NumberFormatException e) {
		return "0 %";
	}
}
}
