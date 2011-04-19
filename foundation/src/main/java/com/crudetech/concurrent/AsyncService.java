package com.crudetech.concurrent;

import java.util.concurrent.Callable;

public interface AsyncService {
    <T, State> void executeAsync(Callable<T> task, AsyncCallback<T, State> callback, State state);
    <State> void executeAsync(Runnable task, AsyncCallback<Void, State> callback, State state);
    void executeAsync(Runnable task);
}
