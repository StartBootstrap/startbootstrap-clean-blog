package tech.daniellas.p2;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.junit.Test;

public class AdditionMonoidTest {
	@Test
	public void calculateSum() {
		List<Integer> numbers = IntStream.range(0, 1_000_000)
		    .mapToObj(i -> i)
		    .collect(Collectors.toList());

		numbers.stream()
		    .parallel()
		    .reduce(0, Integer::sum);
	}
}
