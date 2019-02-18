package tech.daniellas.p1;

import static tech.daniellas.p1.SideEffectsPure.DANGEROUS_NAMES;
import static tech.daniellas.p1.SideEffectsPure3.hasName;

import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import tech.daniellas.p1.SideEffectsImpure.Player;

public class SideEffectsPure4 {

	// This is generic handler selector. It accepts two consumers, truthy and falsy
	// and returns function accepting predicate.
	//
	// Result function returns consumer encapsulating accept call of one of provided
	// consumers depending on predicate result.
	//
	// It's functionally the same as verifyPlayer function from previous example,
	// just curried and generic.
	static <A> Function<Predicate<A>, Consumer<A>> handlerSelector(
	    Consumer<A> truthy,
	    Consumer<A> falsy) {
		return condition -> value -> {
			if (condition.test(value)) {
				truthy.accept(value);
			} else {
				falsy.accept(value);
			}
		};
	}

	// We assign printMessage method reference to Consumer<Player> to make things
	// obvious
	static Consumer<Player> messagePrinter = SideEffectsPure::printMessage;

	// We assign printWarning method reference to Consumer<Player> to make things
	// obvious
	static Consumer<Player> warningPrinter = SideEffectsPure::printWarning;

	// This is function returning Player consumer sending given message to given
	// Kafka topic
	static Consumer<Player> kafkaSender(String topic, String message) {
		return player -> System.out
		    .println(String.format("Sending Kafka message to topic %s: "
		        + message, topic, player.name));
	}

	// Here we compose two consumers using Consumer.andThen() default method, just
	// like in Function.andThen()
	static Consumer<Player> dangerousPlayerHandler = kafkaSender(
	    "security",
	    "Player %s needs an assistance")
	        .andThen(warningPrinter);

	@Test
	public void shouldSendMessageAndPrintWarning() {
		// This is our player to check
		Player player = new Player("John Rambo");

		// Here we crate handler selector function
		handlerSelector(dangerousPlayerHandler, messagePrinter)
		    // then apply to our name checking predicate
		    .apply(hasName(DANGEROUS_NAMES))
		    // here we returned consumer
		    .accept(player);
	}
}
