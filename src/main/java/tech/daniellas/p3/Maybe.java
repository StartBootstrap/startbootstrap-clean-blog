package tech.daniellas.p3;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public abstract class Maybe<A> {

	private static final Maybe<?> NOTHING = new Nothing<>();

	private Maybe() {
	}

	public abstract boolean isPresent();

	public abstract A get();

	public abstract A orElse(A other);

	public abstract A orElseGet(Supplier<? extends A> other);

	public abstract void ifPresentOrElse(Consumer<? super A> c, Runnable r);

	public abstract <B> Maybe<B> map(Function<? super A, ? extends B> f);

	public abstract <B> Maybe<B> flatMap(Function<? super A, Maybe<B>> f);

	public <B> Maybe<B> mapViaFlatMap(Function<? super A, ? extends B> f) {
		return flatMap(f.andThen(Maybe::just));
	}

	public <B> Maybe<B> mapViaFlatMap2(Function<? super A, ? extends B> f) {
		return flatMap(a -> Maybe.just(f.apply(a)));
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

		@Override
		public boolean isPresent() {
			return false;
		}

		@Override
		public A get() {
			throw new NoSuchElementException();
		}

		@Override
		public A orElse(A other) {
			return other;
		}

		@Override
		public A orElseGet(Supplier<? extends A> other) {
			return other.get();
		}

		@Override
		public void ifPresentOrElse(Consumer<? super A> c, Runnable r) {
			r.run();
		}

		@Override
		public <B> Maybe<B> map(Function<? super A, ? extends B> f) {
			return nothing();
		}

		@Override
		public <B> Maybe<B> flatMap(Function<? super A, Maybe<B>> f) {
			return nothing();
		}
	}

	public static final class Just<A> extends Maybe<A> {

		private final A value;

		private Just(A value) {
			this.value = value;
		}

		@Override
		public boolean isPresent() {
			return true;
		}

		@Override
		public A get() {
			return value;
		}

		@Override
		public A orElse(A other) {
			return value;
		}

		@Override
		public A orElseGet(Supplier<? extends A> other) {
			return value;
		}

		@Override
		public void ifPresentOrElse(Consumer<? super A> c, Runnable r) {
			c.accept(value);
		}

		@Override
		public <B> Maybe<B> map(Function<? super A, ? extends B> f) {
			return maybe(f.apply(value));
		}

		@Override
		public <B> Maybe<B> flatMap(Function<? super A, Maybe<B>> f) {
			return f.apply(value);
		}
	}
}
