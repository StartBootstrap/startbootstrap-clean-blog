package tech.daniellas.p1.hof;

import java.util.function.Predicate;

// This is our model extended by age attribute, still immutable
class Player {
	final String name;
	final int score;
	final int age;

	Player(String name, int score, int age) {
		this.name = name;
		this.score = score;
		this.age = age;
	}

	// Check player's age using given condition predicate
	static boolean hasAge(Predicate<Integer> condition, Player player) {
		return condition.test(player.age);
	}
}
