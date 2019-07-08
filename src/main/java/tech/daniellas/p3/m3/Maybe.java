package tech.daniellas.p3.m3;

public abstract class Maybe<A> {
	private Maybe() {
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
