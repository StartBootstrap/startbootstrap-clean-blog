package tech.daniellas.p2.coupled;

import java.util.HashMap;
import java.util.Optional;

class IdNumberHolder {

	// This is our in memory store
	final HashMap<String, String> store;

	// Constructor
	IdNumberHolder(HashMap<String, String> store) {
		this.store = store;
	}

	// Simple validation logic
	boolean isIdNumberValid(String idNumber) {
		return !"".equals(idNumber);
	}

	// Finding method
	Optional<String> get(String userId) {
		return Optional.ofNullable(store.get(userId));
	}

	// Persisting method
	boolean save(String userId, String idNumber) {
		// We do some validation logic first. We don't care about null checks for
		// brevity.
		if (isIdNumberValid(idNumber)) {
			store.put(userId, idNumber);

			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		// Here we instantiate our class providing it's cooperator. In real world, we
		// would use dependency injection framework.
		IdNumberHolder idNumberHolder = new IdNumberHolder(new HashMap<>());

		// We save valid user and id number
		idNumberHolder.save("user", "1234");

		// Prints Optional[1234]
		System.out.println(idNumberHolder.get("user"));
	}
}
