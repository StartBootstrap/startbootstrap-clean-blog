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

import org.openjdk.jmh.annotations.Benchmark;

import tech.daniellas.wfc.Calculator;
import tech.daniellas.wfc.CalculatorJ2;
import tech.daniellas.wfc.CalculatorJ8Prl;
import tech.daniellas.wfc.TokenizerStream;
import tech.daniellas.wfc.WordFrequency;

public class WfcBenchmark extends BenchmarkBase {

	// Our source text file
	static final String PATH = "src/test/resources/bible_ylt.txt";
	// Number of word frequencies we want to display
	static final int LIMIT = 10;
	// Word size threshold
	static final int WORD_SIZE = 0;
	// Java 2 based version of calculator
	static Calculator calcJ2 = new CalculatorJ2();
	// Java 8 based version of calculator
	static Calculator calcJ8 = new CalculatorJ8Prl();

	// Using Java 2 based version calculator
	@Benchmark
	public Collection<WordFrequency> java2() {
		Collection<String> words = calcJ2.extractWords(PATH, WORD_SIZE);
		Map<String, ? extends Number> wordCounts = calcJ2.countWords(words);
		Collection<WordFrequency> mostFrequentWords = calcJ2.getMostFrequentWords(
		    wordCounts,
		    words.size(),
		    LIMIT);

		return mostFrequentWords;
	}

	// Using Java 8 based calculator
	@Benchmark
	public Collection<WordFrequency> java8() throws IOException {
		Collection<String> words = calcJ8.extractWords(PATH, WORD_SIZE);
		Map<String, ? extends Number> wordCounts = calcJ8.countWords(words);
		Collection<WordFrequency> mostFrequentWords = calcJ8.getMostFrequentWords(
		    wordCounts,
		    words.size(),
		    LIMIT);

		return mostFrequentWords;
	}

	@Benchmark
	public Collection<WordFrequency> java8step1() throws IOException {
		List<String> words = Files.lines(Paths.get(PATH))
		    .flatMap(TokenizerStream::of)
		    .filter(word -> word.length() > WORD_SIZE)
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
		    .filter(word -> word.length() > WORD_SIZE)
		    .map(String::toLowerCase)
		    .collect(Collectors.toList());

		Map<String, Long> wordCounts = words.stream()
		    .parallel()
		    .unordered()
		    .collect(Collectors.groupingByConcurrent(Function.identity(),
		        Collectors.counting()));

		return wordCounts.entrySet().parallelStream()
		    .unordered()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(), words.size()))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public Collection<WordFrequency> java8step3() throws IOException {
		CountingMap<String, Integer> wordCounts = Files.lines(Paths.get(PATH))
		    .flatMap(TokenizerStream::of)
		    .filter(word -> word.length() > WORD_SIZE)
		    .map(String::toLowerCase)
		    // Custom grouping collector
		    .collect(
		        // Use CountingMap
		        CountingMap<String, Integer>::new,
		        // Increment map value if key exists, put 1 otherwise
		        (map, word) -> map.compute(word, WfcBenchmark::remapWordCount),
		        // We use sequential stream, so this combiner will never be called
		        (l, r) -> {
			        throw new UnsupportedOperationException();
		        });

		return wordCounts.entrySet().stream()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(), wordCounts.count))
		    .collect(Collectors.toList());
	}

	static Integer remapWordCount(String word, Integer count) {
		return count == null ? 1 : count + 1;
	}

	static class CountingMap<K, V> extends HashMap<K, V> {
		private static final long serialVersionUID = 1L;
		int count = 0;

		@Override
		public V put(K key, V value) {
			V res = super.put(key, value);
			count++;

			return res;
		}
	}

	@Benchmark
	public Collection<WordFrequency> java8step4() throws IOException {
		CountingMap<String, Integer> wordCounts = Files.lines(Paths.get(PATH))
		    .parallel()
		    .unordered()
		    .flatMap(TokenizerStream::ofParallelUnordered)
		    .filter(word -> word.length() > WORD_SIZE)
		    .map(String::toLowerCase)
		    .collect(
		        CountingMap<String, Integer>::new,
		        (map, word) -> map.compute(word, WfcBenchmark::remapWordCount),
		        (l, r) -> r.forEach((word, count) -> l.merge(word, count, Integer::sum)));

		return wordCounts.entrySet().parallelStream()
		    .unordered()
		    .sorted(Map.Entry.comparingByValue(Collections.reverseOrder()))
		    .limit(LIMIT)
		    .map(e -> new WordFrequency(e.getKey(), e.getValue().intValue(),
		        wordCounts.count))
		    .collect(Collectors.toList());
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-wfc.json";
	}

}
