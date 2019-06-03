package tech.daniellas.wfc;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation using Java standard library 1.8 - parallel variant.
 */
public final class CalculatorJ8Prl implements Calculator {

	@Override
	public Collection<String> extractWords(String path, int lengthTreshold) {
		try (Stream<String> fileLines = Files.lines(Paths.get(path))) {
			return fileLines.parallel().unordered()
			    .map(line -> line.split("\\p{javaWhitespace}+"))
			    .flatMap(wordArr -> Stream.of(wordArr).parallel().unordered())
			    .filter(word -> word.length() > lengthTreshold)
			    .map(String::toLowerCase)
			    .collect(Collectors.toList());
		} catch (IOException e) {
			throw new UncheckedIOException(e);
		}
	}

	@Override
	public Map<String, ? extends Number> countWords(Collection<String> words) {
		return words.parallelStream().unordered().collect(
		    Collectors.groupingByConcurrent(Function.identity(), Collectors.counting()));
	}

	@Override
	public Collection<WordFrequency> getMostFrequentWords(
	    Map<String, ? extends Number> wordCounts, int totalWordCount, int limit) {
		return wordCounts.entrySet().parallelStream().unordered()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(limit)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(), totalWordCount))
		    .collect(Collectors.toList());
	}
}
