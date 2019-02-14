package tech.daniellas.p1;

import static tech.daniellas.p1.SideEffectsPure.DANGEROUS_NAMES;
import static tech.daniellas.p1.SideEffectsPure3.hasName;

import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Predicate;

import org.junit.Test;

import tech.daniellas.p1.SideEffectsPure.Player;

public class SideEffectsPure4 {

	// This is generic handler selector. It accepts two consumers, truthy and falsy
	// and returns function accepting predicate plus tested value and returning
	// consumer - one of provided depending on predicate result.
	// It's functionally the same as verifyPlayer function from previous example,
	// just curried and generic.
	static <A> BiFunction<Predicate<A>, A, Consumer<A>> handlerSelector(
	    Consumer<A> truthy,
	    Consumer<A> falsy) {
		return (predicate, value) -> predicate.test(value) ? truthy : falsy;
	}

	// We assign printMessage method reference to Consumer<Player> to make things
	// obvious
	static Consumer<Player> messagePrinter = SideEffectsPure::printMessage;

	// We assign printWarning method reference to Consumer<Player> to make things
	// obvious
	static Consumer<Player> warningPrinter = SideEffectsPure::printWarning;

	// This is function returning Player consumer sending Kafka message to given
	// topic
	static Consumer<Player> kafkaSender(String topic) {
		return player -> System.out
		    .println(String.format("Sending Kafka message to topic %s: "
		        + "WARNING !!! Player %s needs an assistance", topic, player.name));
	}

	// Here we compose two consumers using Consumer.andThen() default method, just
	// like in Function.andThen()
	static Consumer<Player> dangerousPlayerHandler = kafkaSender("security")
	    .andThen(warningPrinter);

	@Test
	public void shouldSendMessageAndPrintWarning() {
		// This is our player to check
		Player player = new Player("John Rambo");

		// Here we crate handler selector function
		handlerSelector(dangerousPlayerHandler, messagePrinter)
		    // then apply to our name checking predicate and player
		    .apply(hasName(DANGEROUS_NAMES), player)
		    // here we have returned player's consumer, so we pass player to be processed
		    .accept(player);
	}
}
