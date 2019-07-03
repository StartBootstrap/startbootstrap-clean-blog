package tech.daniellas.p3;

import java.math.BigDecimal;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

class Parser3 {
	static Optional<BigDecimal> createBigDecimal(String str) {
		try {
			return Optional.ofNullable(str).map(BigDecimal::new);
		} catch (NumberFormatException e) {
			return Optional.empty();
		}
	}

	static Optional<String> parsePercent(String str) {
		return Optional.ofNullable(str)
		    .filter(StringUtils::isNotBlank)
		    .map(value -> value.replace(" ", ""))
		    .map(value -> value.replace(',', '.'))
		    .flatMap(Parser3::createBigDecimal)
		    .map(value -> value.multiply(BigDecimal.valueOf(100)))
		    .map(value -> value.setScale(2))
		    .map(value -> value + " %");
	}
}
