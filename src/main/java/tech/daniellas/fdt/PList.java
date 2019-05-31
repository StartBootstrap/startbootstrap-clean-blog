package tech.daniellas.fdt;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

public abstract class PList<A> implements Iterable<A> {

	private final static PList<?> NIL = new Nil<>();

	public abstract boolean empty();

	public abstract A head();

	public abstract PList<A> tail();

	@SuppressWarnings("unchecked")
	public static <A> PList<A> nil() {
		return (PList<A>) NIL;
	}

	public static <A> PList<A> cons(A head, PList<A> tail) {
		return new Cons<A>(head, tail);
	}

	@SafeVarargs
	private static <A> PList<A> cons(int i, A... as) {
		if (i < as.length) {
			return new Cons<>(as[i], cons(i + 1, as));
		}

		return nil();
	}

	@SafeVarargs
	public static <A> PList<A> cons(A... as) {
		return cons(0, as);
	}

	public int size() {
		if (empty()) {
			return 0;
		}

		return 1 + tail().size();
	}

	public PList<A> prepend(A i) {
		if (empty()) {
			return cons(i, nil());
		}

		return cons(i, this);
	}

	public PList<A> append(A i) {
		if (empty()) {
			return cons(i, nil());
		}

		return cons(head(), tail().append(i));
	}

	public PList<A> append(PList<A> l) {
		if (empty()) {
			return l;
		}

		return concat(this, l);
	}

	public PList<A> prepend(PList<A> l) {
		if (empty()) {
			return l;
		}

		return concat(l, this);
	}

	
	public <B> B foldRight(BiFunction<A, B, B> f, B b) {
		if (empty()) {
			return b;
		}

		return f.apply(head(), tail().foldRight(f, b));
	}

	public <B> B foldLeft(BiFunction<B, A, B> f, B b) {
		if (empty()) {
			return b;
		}

		return tail().foldLeft(f, f.apply(b, head()));
	}

	public PList<A> reverse() {
		return foldLeft((b, a) -> b.prepend(a), nil());
	}

	public <B> PList<B> map(Function<? super A, ? extends B> f) {
		if (empty()) {
			return nil();
		}

		return cons(f.apply(head()), tail().map(f));
	}

	public static <A> PList<A> concat(PList<A> l, PList<A> r) {
		if (l.empty()) {
			return r;
		}

		return l.foldRight((a, b) -> b.prepend(a), r);
	}

	public <B> PList<B> flatMap(Function<? super A, ? extends PList<B>> f) {
		if (empty()) {
			return nil();
		}

		return concat(f.apply(head()), tail().flatMap(f));
	}

	public PList<A> filter(Predicate<A> p) {
		if (empty()) {
			return nil();
		}

		return foldRight((i, a) -> p.test(i) ? a.prepend(i) : a, nil());
	}

	public Stream<A> stream() {
		if (empty()) {
			return Stream.empty();
		}

		return Stream.concat(Stream.of(head()), tail().stream());
	}

	public Stream<A> iteratorStream() {
		return StreamSupport.stream(spliterator(), false);
	}

	@Override
	public Iterator<A> iterator() {
		return new PListIterator<>(this);
	}

	@ToString
	@EqualsAndHashCode(callSuper = false)
	private static final class Nil<A> extends PList<A> {

		@Override
		public boolean empty() {
			return true;
		}

		@Override
		public A head() {
			throw new NoSuchElementException();
		}

		@Override
		public PList<A> tail() {
			throw new NoSuchElementException();
		}
	}

	@ToString
	@EqualsAndHashCode(callSuper = false)
	@RequiredArgsConstructor
	private static final class Cons<A> extends PList<A> {

		private final A head;

		private final PList<A> tail;

		@Override
		public boolean empty() {
			return false;
		}

		@Override
		public A head() {
			return head;
		}

		@Override
		public PList<A> tail() {
			return tail;
		}
	}

	@AllArgsConstructor
	private static class PListIterator<A> implements Iterator<A> {

		private PList<A> current;

		@Override
		public boolean hasNext() {
			return !current.empty();
		}

		@Override
		public A next() {
			A item = current.head();

			current = current.tail();

			return item;
		}

	}
}
