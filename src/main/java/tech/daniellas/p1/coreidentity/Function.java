package tech.daniellas.p1.coreidentity;

@FunctionalInterface
public interface Function<T, R> {

  R apply(T t);

  // Returns identity function
  static <T> Function<T, T> identity() {
    return t -> t;
  }
}
