package tech.daniellas.p1;

import java.math.BigDecimal;

public class Composition1 {
static String formatPercent(String value) {
	try {
		return new BigDecimal(value).divide(BigDecimal.valueOf(100)) + " %";
	} catch (NumberFormatException e) {
		return "0 %";
	}
}
}
