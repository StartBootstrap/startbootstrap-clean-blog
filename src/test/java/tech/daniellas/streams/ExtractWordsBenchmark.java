package tech.daniellas.streams;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Ignore;
import org.openjdk.jmh.annotations.Benchmark;

import tech.daniellas.wfc.TokenizerStream;

@Ignore
public class ExtractWordsBenchmark extends BenchmarkBase {

	static final String PATH = "src/test/resources/bible_ylt.txt";

	@Benchmark
	public List<String> split() throws IOException {
		return Files.lines(Paths.get(PATH))
		    .flatMap(line -> Arrays.stream(line.split("\\p{javaWhitespace}+")))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<String> splitPar() throws IOException {
		return Files.lines(Paths.get(PATH)).parallel()
		    .flatMap(line -> Arrays.stream(line.split("\\p{javaWhitespace}+")))
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<String> splitParPar() throws IOException {
		return Files.lines(Paths.get(PATH)).parallel()
		    .flatMap(line -> Arrays.stream(line.split("\\p{javaWhitespace}+")).parallel())
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<String> tokenizer() throws IOException {
		return Files.lines(Paths.get(PATH))
		    .flatMap(TokenizerStream::of)
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<String> tokenizerPar() throws IOException {
		return Files.lines(Paths.get(PATH)).parallel()
		    .flatMap(TokenizerStream::of)
		    .collect(Collectors.toList());
	}

	@Benchmark
	public List<String> tokenizerParPar() throws IOException {
		return Files.lines(Paths.get(PATH)).parallel()
		    .flatMap(l -> TokenizerStream.of(l).parallel())
		    .collect(Collectors.toList());
	}

	@Override
	protected String reportPath() {
		return "data/benchmark-extract-words.json";
	}

}
