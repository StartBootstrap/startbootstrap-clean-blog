package tech.daniellas.p1;

import static tech.daniellas.p1.Partial.partial;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

import org.junit.Test;

import io.atlassian.fugue.Pair;
import tech.daniellas.p1.SideEffectsImpure.Player;

public class SideEffectsCompose {

	// This is our dangerous players names list
	static List<String> DANGEROUS_NAMES = Arrays.asList(
	    "John Rambo",
	    "John Rambo Jr.",
	    "Chuck Norris");

	// This is our VIP players names list
	static List<String> VIP_NAMES = Arrays.asList(
	    "Donald Trump",
	    "Barack Obama");

	// This is Player predicate checking whether it's name is on given names list
	static Predicate<Player> hasName(List<String> names) {
		return player -> names.contains(player.name);
	}

	// This is Player age predicate checking whether Player's age matches given
	// condition
	static Predicate<Player> hasAge(Predicate<Integer> condition) {
		return player -> condition.test(player.age);
	}

	// This is generic 'lower then' predicate. It accepts any Comparable type
	// because this is all we need to make it work.
	static <A extends Comparable<A>> Predicate<A> lt(A value) {
		return a -> a.compareTo(value) < 0;
	}

	// This is function returning Player consumer printing given message formatted
	// including Player's name. We dropped two separate functions 'printMessage' and
	// 'printWarning' because they were doing the same thing.
	static Consumer<Player> print(String message) {
		return player -> System.out.println(String.format(message, player.name));
	}

	// This is function returning Player consumer sending formatted message to given
	// Kafka topic
	static Consumer<Player> kafkaSender(String topic, String message) {
		return player -> System.out.println(
		    String.format(
		        "Sending message to %s: " + message,
		        topic,
		        player.name));
	}

	// Here we partially apply 'kafkaSender' to 'security' topic name. We will
	// always send security messages to the same topic. As the result, we have
	// function accepting message only.
	static Function<String, Consumer<Player>> securityNotifier = partial(
	    SideEffectsCompose::kafkaSender,
	    "security");

	// This is our first Player condition/consumer pair. Pair is just the tuple
	// holding left and right values. We store predicate on the left and consumer on
	// the right. We use io.atlassian.fugue.Pair implementation from nice
	// Atlassian's Fugue library.
	static Pair<Predicate<Player>, Consumer<Player>> dangerousPlayersHandler = Pair.pair(
	    // This is the condition
	    hasName(DANGEROUS_NAMES),
	    // This is the consumer composed from two consumers via 'andThen'
	    securityNotifier
	        .apply("Security assistance needed for dangerous player %s")
	        .andThen(print("Warning !!! Dangerous player: %s")));

	// This is VIP Players handler
	static Pair<Predicate<Player>, Consumer<Player>> vipPlayersHandler = Pair.pair(
	    // This is the condition
	    hasName(VIP_NAMES),
	    // This is the consumer
	    securityNotifier
	        .apply("Security assistance needed for VIP player %s"));

	// This is young Players handler
	static Pair<Predicate<Player>, Consumer<Player>> youngPlayersHandler = Pair.pair(
	    hasAge(lt(21)),
	    print("Young player: %s"));

	// This is handlers list we will use for Players verification
	static List<Pair<Predicate<Player>, Consumer<Player>>> handlers = Arrays.asList(
	    dangerousPlayersHandler,
	    vipPlayersHandler,
	    youngPlayersHandler);

	// This is generic function responsible for handlers resolution. It returns
	// Optional because there can be no matching handler found.
	static <A> Optional<Consumer<A>> resolveHandler(
	    List<Pair<Predicate<A>, Consumer<A>>> handlers,
	    A value) {
		// We create Stream from handlers list
		return handlers.stream()
		    // then filter by applying predicate on the left side to given value
		    .filter(p -> p.left().test(value))
		    // then map to pair's right side holding consumer
		    .map(Pair::right)
		    // and finally reduce all matching consumers to single one via 'andThen'
		    .reduce(Consumer::andThen);
	}

	// This is our generic noop consumer doing nothing. We will use it in case of no
	// match found.
	static <A> void nil(A value) {
	}

	@Test
	public void shouldHandleDangerousPlayer() {
		Player player = new Player("John Rambo", 60);

		// Here we resolve consumer, we have either Optional with some value or empty
		// one.
		resolveHandler(handlers, player)
		    // or return nil in case of no match
		    .orElse(SideEffectsCompose::nil)
		    // then apply it to player
		    .accept(player);
	}

	@Test
	public void shouldHandleVIPPlayer() {
		Player player = new Player("Barack Obama", 60);

		// Here we will handle VIP player
		resolveHandler(handlers, player)
		    .orElse(SideEffectsCompose::nil)
		    .accept(player);
	}

	@Test
	public void shouldHandleYoungDangerousPlayer() {
		Player player = new Player("John Rambo Jr.", 15);

		// Here we will handle dangerous young player
		resolveHandler(handlers, player)
		    .orElse(SideEffectsCompose::nil)
		    .accept(player);
	}

}
