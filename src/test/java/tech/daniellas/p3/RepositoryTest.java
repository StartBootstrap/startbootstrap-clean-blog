package tech.daniellas.p3;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;

import tech.daniellas.p3.Repository.Entity;

public class RepositoryTest {
	/**
	 * This is the test implementation of Repository interface. It knows only about
	 * Entity of id1.
	 */
	Repository repo = id -> "DEFAULT".equals(id) || "id1".equals(id)
	    ? Optional.of(new Entity(id))
	    : Optional.empty();

	@Test
	public void shouldGetDefaultEagerly() {
		// Prints "Getting default value" even if we don't need to retrieve default
		Entity res = repo.findById("id1").orElse(repo.getDefault());

		assertThat(res.id).isEqualTo("id1");
	}

	@Test
	public void shouldNotGetDefault() {
		// Prints nothing, because entity exists and default provider was not evaluated
		Entity res = repo.findById("id1").orElseGet(repo::getDefault);

		assertThat(res.id).isEqualTo("id1");
	}

	@Test
	public void shouldGetDefaultLazily() {
		// Prints "Getting default value" because entity doesn't exist
		Entity res = repo.findById("id2").orElseGet(repo::getDefault);

		assertThat(res.id).isEqualTo("DEFAULT");
	}
}
