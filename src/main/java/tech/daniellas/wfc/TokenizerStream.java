package tech.daniellas.wfc;

import java.util.Iterator;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.StringTokenizer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class TokenizerStream {

	// Stream factory method
	public static Stream<String> of(String str) {
		// Here we create stream from iterator
		return StreamSupport.stream(
		    Spliterators.spliteratorUnknownSize(
		        new StringTokenizerIterator(new StringTokenizer(str)),
		        Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL),
		    false);
	}

	// Parallel stream factory method
	public static Stream<String> ofParallelUnordered(String str) {
		return StreamSupport.stream(
		    Spliterators.spliteratorUnknownSize(
		        new StringTokenizerIterator(new StringTokenizer(str)),
		        Spliterator.ORDERED | Spliterator.IMMUTABLE | Spliterator.NONNULL),
		    false)
		    .parallel()
		    .unordered();
	}

	// This is iterator implementation
	static class StringTokenizerIterator implements Iterator<String> {
		final StringTokenizer tokenizer;

		StringTokenizerIterator(StringTokenizer tokenizer) {
			this.tokenizer = tokenizer;
		}

		@Override
		public boolean hasNext() {
			return tokenizer.hasMoreElements();
		}

		@Override
		public String next() {
			return tokenizer.nextToken();
		}
	}
}
