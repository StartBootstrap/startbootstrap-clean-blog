package tech.daniellas.p2.fun;

import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class IdNumbersTest {

	@Test
	public void shouldGet() {
		// We call our functions providing some stub implementation of getter in form of
		// lambda expression
		Assertions.assertThat(IdNumbers.get(userId -> userId + "IdNumber", "uid"))
		    .isEqualTo(Optional.of("uidIdNumber"));
	}

	@Test
	public void shouldReturnEmptyWhenNotFound() {
		// We call our function providing some stub implementation of getter but this
		// time we expect negative result, because our getter returns null
		Assertions.assertThat(IdNumbers.get(userId -> null, "uid"))
		    .isEqualTo(Optional.empty());
	}

	@Test
	public void shouldSave() {
		// Here starts tricky part. We need to record persister result. It's BiConsumer,
		// designed to perform side effects, there is no other way to test if it was
		// executed then performing some side effect.
		AtomicReference<String> ref = new AtomicReference<>();
		// We call our function providing persister implementation setting ref variable
		// value
		Boolean saved = IdNumbers
		    .save((userId, idNumber) -> ref.set("saved"))
		    .apply("uid", "idnumber");

		Assertions.assertThat(saved).isTrue();
		Assertions.assertThat(ref.get()).isEqualTo("saved");
	}

	@Test
	public void shouldNotSaveOnEmptyIdNumber() {
		// Here we do the same as in positive scenario
		AtomicReference<String> ref = new AtomicReference<>();
		// This time we expect failure, because provided id nomber will ont pass
		// validation phase
		Boolean saved = IdNumbers
		    .save((userId, idNumber) -> ref.set("saved"))
		    .apply("uid", "");

		Assertions.assertThat(saved).isFalse();
		Assertions.assertThat(ref.get()).isNull();
	}
}
