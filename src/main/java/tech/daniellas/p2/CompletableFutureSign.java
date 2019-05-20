package tech.daniellas.p2;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.function.Function;

public class CompletableFutureSign<T> {
// CompletionStage is the interface implemented by CompletableFuture
<U> CompletableFuture<U> thenCompose(Function<? super T, ? extends CompletionStage<U>> fn) 
{
    return null;
}
}
