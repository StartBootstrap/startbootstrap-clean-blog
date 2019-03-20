package tech.daniellas.p2.sorts;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.apache.commons.lang3.RandomUtils;

public class TestData {

	public static void main(String[] args) throws IOException {
		Files.write(Paths.get("data/sorts.txt"), IntStream.range(0, 100_000)
		    .mapToObj(i -> RandomUtils.nextInt())
		    .map(String::valueOf)
		    .collect(Collectors.toList()));
	}

}
