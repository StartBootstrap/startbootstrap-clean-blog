package tech.daniellas.p3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

class DecoratingStore3 {
	Map<String, String> cache = new HashMap<>();

	String getDecorated(String key) {
		// The key can be null, let's wrap it with Optional
		return Optional.ofNullable(key)
		    // Filter out blank keys
		    .filter(StringUtils::isNotBlank)
		    // Get from cache
		    .map(cache::get)
		    // Transform to upper case
		    .map(String::toUpperCase)
		    // Decorate
		    .map(DecoratingStore1::decorate)
		    // Return default value it key is blank or there is no value under given key
		    .orElse("");
	}
}
