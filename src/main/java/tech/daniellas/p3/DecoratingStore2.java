package tech.daniellas.p3;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

class DecoratingStore2 {
	Map<String, String> cache = new HashMap<>();

	String getDecorated(String key) {
		// Early check of blank key
		if (StringUtils.isBlank(key)) {
			return "";
		}

		// cache.get can return null, perfect match for Optional
		return Optional.ofNullable(cache.get(key))
		    // Transform to upper case
		    .map(String::toUpperCase)
		    // Decorate using function from previous implementation
		    .map(DecoratingStore1::decorate)
		    // Return default value if there is no value under given key
		    .orElse("");
	}
}
