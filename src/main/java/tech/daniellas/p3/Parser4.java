package tech.daniellas.p3;

import java.math.BigDecimal;

import org.apache.commons.lang3.StringUtils;

import io.vavr.control.Try;

class Parser4 {
	static Try<BigDecimal> createBigDecimal(String str) {
		return Try.success(str)
		    .map(BigDecimal::new);
	}

	static Try<String> parsePercent(String str) {
		// Create successful try from given string
		return Try.success(str)
		    .filter(StringUtils::isNotBlank)
		    .map(value -> value.replace(" ", ""))
		    .map(value -> value.replace(',', '.'))
		    .flatMap(Parser4::createBigDecimal)
		    .map(value -> value.multiply(BigDecimal.valueOf(100)))
		    .map(value -> value.setScale(2))
		    .map(value -> value + " %");
	}
}
