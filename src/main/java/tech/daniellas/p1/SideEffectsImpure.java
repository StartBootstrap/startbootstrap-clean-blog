package tech.daniellas.p1;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class SideEffectsImpure {

	// This is our player class
	static class Player {
		final String name;
		final int age;

		Player(String name, int age) {
			this.name = name;
			this.age = age;
		}

		Player(String name) {
			this(name, 0);
		}
	}

	// This is our dangerous players list
	static List<String> DANGEROUS_PLAYERS = Arrays.asList("John Rambo", "Chuck Norris");

	// This is our verification function
	static void verifyPlayer(Player player) {
		if (DANGEROUS_PLAYERS.contains(player.name)) {
			System.out.println("WARNING !!! " + player.name + " is attempting to enter");
		} else {
			System.out.println(player.name + " is attempting to enter");
		}
	}

	@Test
	public void shouldPrintWarning() {
		// Prints warning
		verifyPlayer(new Player("John Rambo"));
	}

	@Test
	public void shouldPrintMessage() {
		// Prints message
		verifyPlayer(new Player("John Doe"));
	}
}
