package tech.daniellas.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.openjdk.jmh.annotations.Benchmark;

import tech.daniellas.wfc.Calculator;
import tech.daniellas.wfc.CalculatorJ2;
import tech.daniellas.wfc.TokenizerStream;
import tech.daniellas.wfc.WordFrequency;

public class WfcBenchmark extends BenchmarkBase {

	static final String PATH = "src/test/resources/bible_ylt.txt";

	static final int LIMIT = 10;

	static final int SIZE = 0;

	static Calculator calcJ2 = new CalculatorJ2();

	@Benchmark
	public Collection<WordFrequency> java2() {
		Collection<String> words = calcJ2.extractWords(PATH, SIZE);
		Map<String, ? extends Number> wordCounts = calcJ2.countWords(words);
		Collection<WordFrequency> mostFrequentWords = calcJ2.getMostFrequentWords(wordCounts, words.size(), LIMIT);

		return mostFrequentWords;
	}

	@Benchmark
	public Collection<WordFrequency> java8() throws IOException {
		List<String> words = Files.lines(Paths.get(PATH))
		    .map(line -> line.split("\\p{javaWhitespace}+"))
		    .flatMap(wordArr -> Stream.of(wordArr).parallel().unordered())
		    .filter(word -> word.length() > SIZE)
		    .map(String::toLowerCase)
		    .collect(Collectors.toList());

		Map<String, Long> wordCounts = words.stream()
		    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return wordCounts.entrySet().stream()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(), words.size()))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public Collection<WordFrequency> java8step1() throws IOException {
		List<String> words = Files.lines(Paths.get(PATH))
		    .flatMap(TokenizerStream::of)
		    .filter(word -> word.length() > SIZE)
		    .map(String::toLowerCase)
		    .collect(Collectors.toList());

		Map<String, Long> wordCounts = words.stream()
		    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()));

		return wordCounts.entrySet().stream()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(), words.size()))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public Collection<WordFrequency> java8step2() throws IOException {
		List<String> words = Files.lines(Paths.get(PATH))
		    .flatMap(TokenizerStream::ofParallelUnordered)
		    .filter(word -> word.length() > SIZE)
		    .map(String::toLowerCase)
		    .collect(Collectors.toList());

		Map<String, Long> wordCounts = words.stream().parallel().unordered()
		    .collect(Collectors.groupingByConcurrent(Function.identity(),
		        Collectors.counting()));

		return wordCounts.entrySet().parallelStream().unordered()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(),
		        words.size()))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<WordFrequency> java8step3() throws IOException {
		CountingMap<String, Integer> wordCounts = Files.lines(Paths.get(PATH))
		    .flatMap(TokenizerStream::of)
		    .filter(word -> word.length() > SIZE)
		    .map(String::toLowerCase)
		    .collect(
		        CountingMap<String, Integer>::new,
		        (map, word) -> map.compute(word, WfcBenchmark::remapWordCount),
		        (l, r) -> {
			        throw new UnsupportedOperationException();
		        });

		return wordCounts.entrySet().stream()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(),
		        wordCounts.count))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<WordFrequency> java8step4() throws IOException {
		CountingMap<String, Integer> wordCounts = Files.lines(Paths.get(PATH))
				.parallel()
				.unordered()
		    .flatMap(TokenizerStream::ofParallelUnordered)
		    .filter(word -> word.length() > SIZE)
		    .map(String::toLowerCase)
		    .collect(
		        CountingMap<String, Integer>::new,
		        (map, word) -> map.compute(word, WfcBenchmark::remapWordCount),
		        (l, r) -> r.forEach((word, count) -> l.merge(word, count, Integer::sum)));

		return wordCounts.entrySet().parallelStream().unordered()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(),
		        wordCounts.count))
		    .collect(Collectors.toList());
	}

	static Integer remapWordCount(String word, Integer count) {
		return count == null ? 1 : count++;
	}

	static class CountingMap<A, B> extends HashMap<A, B> {
		private static final long serialVersionUID = 1L;
		int count = 0;

		@Override
		public B put(A key, B value) {
			super.put(key, value);
			count = count++;

			return value;
		}
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-wfc.json";
	}

}
