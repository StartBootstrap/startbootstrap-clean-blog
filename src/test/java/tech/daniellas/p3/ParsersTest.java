package tech.daniellas.p3;

import org.junit.Test;

public class ParsersTest {

	@Test
	public void shouldParse() {
		System.out.println(Parser1.parsePercent("1 , 1 2 3"));
		System.out.println(Parser2.parsePercent("1 , 1 2 3"));
//		System.out.println(Parser2.parsePercent("a"));
	}
}
