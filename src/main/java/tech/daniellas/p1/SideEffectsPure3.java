package tech.daniellas.p1;

import static tech.daniellas.p1.SideEffectsPure.DANGEROUS_NAMES;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Test;

import tech.daniellas.p1.SideEffectsPure.Player;

public class SideEffectsPure3 {

	// This is pure version accepting 2 side effects handlers and Predicate
	static Consumer<Player> verifyPlayer(
	    Consumer<Player> truthy,
	    Consumer<Player> falsy,
	    Predicate<Player> condition,
	    Player player) {
		// We check condition here
		if (condition.test(player)) {
			// It's true so return truthy
			return truthy;
		}

		// It's false
		return falsy;
	}

	// This is function returning predicate, checking if given player's name is on
	// the list
	static Predicate<Player> hasName(List<String> names) {
		return player -> names.contains(player.name);
	}

	@Test
	public void shouldPrintWarning() {
		Player player = new Player("John Rambo");

		// Prints warning. Please note that our side effects handlers positions are
		// swapped
		verifyPlayer(
		    SideEffectsPure::printWarning,
		    SideEffectsPure::printMessage,
		    hasName(DANGEROUS_NAMES),
		    player)
		        .accept(player);
	}
}
