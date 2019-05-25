package tech.daniellas.p2.decoupled;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashMap;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;

import org.mockito.Mockito;
import org.springframework.jdbc.core.JdbcTemplate;

class IdNumberHolder {

	// This is our finder function
	final Function<String, String> getter;

	// This is our persisting consumer
	final BiConsumer<String, String> persister;

	// Constructor
	IdNumberHolder(
	    Function<String, String> getter,
	    BiConsumer<String, String> persister) {
		this.getter = getter;
		this.persister = persister;
	}

	// Simple validation logic
	boolean isIdNumberValid(String idNumber) {
		return !"".equals(idNumber);
	}

	// Finding method
	Optional<String> get(String userId) {
		// We apply getter to user id and then apply Optional.ofNullable() function
		return getter
		    .andThen(Optional::ofNullable)
		    .apply(userId);
	}

	// Persisting method
	boolean save(String userId, String idNumber) {
		// We do some validation logic first. We don't care about null checks for
		// brevity.
		if (isIdNumberValid(idNumber)) {
			persister.accept(userId, idNumber);

			return true;
		}

		return false;
	}

	public static void main(String[] args) {
		/* -----	In memory implementation	-----*/
		// This is our in memory store
		HashMap<String, String> inMemoryStore = new HashMap<String, String>();
		// Here we instantiate our class providing it's cooperator. In real world, we
		// would use dependency injection framework.
		IdNumberHolder inMemoryHolder = new IdNumberHolder(
		    inMemoryStore::get,
		    inMemoryStore::put);

		// We save valid user and id number
		inMemoryHolder.save("user", "1234");

		// Prints Optional[1234]
		System.out.println(inMemoryHolder.get("user"));

		/* -----	JDBC implementation		-----*/
		// This is out JDBC store using Spring Framework JdbcTemplate. We mock it using
		// Mockito to skip JDBC boilerplate.
		JdbcTemplate jdbcStore = mock(JdbcTemplate.class);
		when(jdbcStore.queryForObject(
		    Mockito.anyString(),
		    Mockito.any(Class.class),
		    Mockito.any(Object[].class)))
		        .thenReturn("1234");
		// Here we instantiate our class providing JDBC based cooperators
		IdNumberHolder jdbcHolder = new IdNumberHolder(
		    userId -> jdbcStore.queryForObject(
		        "select id_number from user_idnumber where user_id=?",
		        String.class,
		        userId),
		    (userId, idNumber) -> jdbcStore.update(
		        "update user_idnumber set id_number=? where user_id=?",
		        idNumber,
		        userId));

		// We save valid user and id number
		jdbcHolder.save("user", "1234");

		// Prints Optional[1234]
		System.out.println(jdbcHolder.get("user"));
	}
}
