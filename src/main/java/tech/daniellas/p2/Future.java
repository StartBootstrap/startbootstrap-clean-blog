package tech.daniellas.p2;

import java.util.concurrent.ExecutionException;

public interface Future<T> {
T get() throws InterruptedException, ExecutionException;
}
