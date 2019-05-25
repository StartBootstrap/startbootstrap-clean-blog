package tech.daniellas.p2.fun;

import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

class IdNumbers {

	// This is private constructor. There is no reason to instantiate this clas.
	// Without it, some test coverage tools like Jacoco would report this class
	// implicit constructor as not covered line of code.
	private IdNumbers() {
	}

	// This is finder higher order function, accepts getter and user id
	static final Optional<String> get(Function<String, String> getter, String userId) {
		return getter
		    .andThen(Optional::ofNullable)
		    .apply(userId);
	}

	// This is persister higher order function, accepts persister and returns
	// BiFunction accepting data to save and returning operation result
	static final BiFunction<String, String, Boolean> save(
	    BiConsumer<String, String> persister) {
		return (userId, idNumber) -> {
			if (isIdNumberValid(idNumber)) {
				persister.accept(userId, idNumber);

				return true;
			}

			return false;
		};
	}

	// This is our validation predicate
	static boolean isIdNumberValid(String idNumber) {
		return !"".equals(idNumber);
	}

}
