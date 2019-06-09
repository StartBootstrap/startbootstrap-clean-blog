package tech.daniellas.wfc;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@EqualsAndHashCode(of = { "word", "count" })
public final class WordFrequency {
	public final String word;
	public final int count;
	public final int totalCount;
}
