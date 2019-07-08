package tech.daniellas.p3.m5;

import java.util.Objects;

public abstract class Maybe<A> {

	private static final Maybe<?> NOTHING = new Nothing<>();

	private Maybe() {
	}

	@SuppressWarnings("unchecked")
	public static <A> Maybe<A> nothing() {
		return (Maybe<A>) NOTHING;
	}

	public static <A> Maybe<A> just(A value) {
		Objects.requireNonNull(value);

		return new Just<A>(value);
	}

	public static <A> Maybe<A> maybe(A value) {
		if (value != null) {
			return just(value);
		}

		return nothing();
	}

	public static final class Nothing<A> extends Maybe<A> {

		private Nothing() {
		}
	}

	public static final class Just<A> extends Maybe<A> {

		private final A value;

		private Just(A value) {
			this.value = value;
		}
	}
}
