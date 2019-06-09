package tech.daniellas.wfc;

import java.util.Collection;
import java.util.Map;

public interface Calculator {
	Collection<String> extractWords(String path, int lengthTreshold);

	Map<String, ? extends Number> countWords(Collection<String> words);

	Collection<WordFrequency> getMostFrequentWords(
	    Map<String, ? extends Number> wordCounts, 
	    int totalWordCount, 
	    int limit);
}
