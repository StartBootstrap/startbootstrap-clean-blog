package tech.daniellas.p3.m4;

public abstract class Maybe<A> {
	private static final Maybe<?> NOTHING = new Nothing<>();
	
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
