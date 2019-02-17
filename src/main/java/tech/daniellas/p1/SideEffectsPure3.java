package tech.daniellas.p1;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Predicate;

import tech.daniellas.p1.SideEffectsImpure.Player;

public class SideEffectsPure3 {

	// This is function returning predicate, checking if given player's name is on
	// the list
	static Predicate<Player> hasName(List<String> names) {
		return player -> names.contains(player.name);
	}

	// This is pure version accepting 2 side effects handlers and predicate
	static Consumer<Player> verifyPlayer(
	    Consumer<Player> truthy,
	    Consumer<Player> falsy,
	    Predicate<Player> condition) {
		return player -> {
			// We check condition here
			if (condition.test(player)) {
				// It's true - call truthy
				truthy.accept(player);
			} else {
				// It's false - call falsy
				falsy.accept(player);
			}
		};
	}
}
