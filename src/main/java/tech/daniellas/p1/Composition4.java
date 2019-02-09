package tech.daniellas.p1;

import static tech.daniellas.p1.Composition3.applySafe;
import static tech.daniellas.p1.Composition3.hundredMultiplier;
import static tech.daniellas.p1.Composition3.percentAppender;

import java.math.BigDecimal;

class Composition4 {

	// We use our defined functions 
	String result = percentAppender
			.compose(hundredMultiplier)
			.compose(applySafe(Composition3::parse, BigDecimal.ZERO))
			.apply("0.5");
}
