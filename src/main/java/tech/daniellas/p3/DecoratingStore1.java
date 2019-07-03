package tech.daniellas.p3;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;

class DecoratingStore1 {
	// This is our values store
	Map<String, String> store = new HashMap<>();

	/**
	 * Get value from store by key and return decorated, return empty string in case
	 * of missing value or blank key
	 * 
	 * @param key
	 *          of the value
	 * @return decorated value
	 */
	String getDecorated(String key) {
		// Early check of blank key
		if (StringUtils.isBlank(key)) {
			return "";
		}
		// Get value from store by key
		String value = store.get(key);

		// Decorate and return if value is present
		if (value != null) {
			return decorate(value.toUpperCase());
		}

		// Return default value if value is absent
		return "";
	}

	static String decorate(String str) {
		return "== " + str + " ==";
	}
}
