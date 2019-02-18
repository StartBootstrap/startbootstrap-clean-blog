package tech.daniellas.p1;

import static tech.daniellas.p1.Partial.partial;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import tech.daniellas.p1.SideEffectsImpure.Player;

public class SideEffectsCompose {

	static List<String> VIP_NAMES = Arrays.asList("Donald Trump", "Barack Obama");

	static List<String> DANGEROUS_NAMES = Arrays.asList("John Rambo", "Chuck Norris");

	static Predicate<Player> hasName(List<String> names) {
		return player -> names.contains(player.name);
	}

	static Predicate<Player> hasAge(Predicate<Integer> condition) {
		return player -> condition.test(player.age);
	}

	static <A extends Comparable<A>> Predicate<A> gt(A value) {
		return a -> a.compareTo(value) > 0;
	}

	static void printWarning(Player player) {
		System.out.println("WARNING !!! " + player.name + " is attempting to enter");
	}

	static void printMessage(Player player) {
		System.out.println(player.name + " is attempting to enter");
	}

	static Consumer<Player> kafkaSender(String topic, String message) {
		return player -> System.out
		    .println(String.format("Sending Kafka message to topic %s: "
		        + message, topic, player.name));
	}

	static Function<String, Consumer<Player>> securityNotifier = partial(
	    SideEffectsCompose::kafkaSender,
	    "security");

}
