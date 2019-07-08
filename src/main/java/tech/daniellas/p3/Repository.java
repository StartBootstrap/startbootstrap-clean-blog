package tech.daniellas.p3;

import java.util.Optional;

interface Repository {

	static class Entity {
		final String id;

		public Entity(String id) {
			this.id = id;
		}
	}

	Optional<Entity> findById(String id);

	default Entity getDefault() {
		System.out.println("Getting default value");

		return findById("DEFAULT")
		    .orElseThrow(IllegalStateException::new);
	}
}
