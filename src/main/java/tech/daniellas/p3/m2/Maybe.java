package tech.daniellas.p3.m2;

public abstract class Maybe<A> {
	private Maybe() {
	}
	
	public static final class Nothing<A> extends Maybe<A> {
		private Nothing() {
		}
	}
	
	public static final class Just<A> extends Maybe<A> {
		private Just() {
		}
	}
}
