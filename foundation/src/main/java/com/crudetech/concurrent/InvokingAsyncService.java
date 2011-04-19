package com.crudetech.concurrent;


import com.crudetech.lang.InvocationService;

import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class InvokingAsyncService implements AsyncService {
    private final AsyncService decorated;
    private final InvocationService invocationService;

    public InvokingAsyncService(AsyncService decorated, InvocationService invocationService) {
        this.decorated = decorated;
        this.invocationService = invocationService;
    }

    static <T> T resultOf(Future<T> result) {
        try {
            return result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public <T, State> void executeAsync(Callable<T> task, final AsyncCallback<T, State> callback, State state) {
        AsyncCallback<T, State> invokingCallback = new AsyncCallback<T, State>() {
            @Override
            public void finished(final Future<T> result, final State state) {
                invocationService.beginInvocation(new Runnable() {
                    @Override
                    public void run() {
                        callback.finished(result, state);
                    }
                });
            }
        };

        decorated.executeAsync(task, invokingCallback, state);
    }

    @Override
    public <State> void executeAsync(Runnable task, AsyncCallback<Void, State> callback, State state) {
        executeAsync(Executors.callable(task, (Void)null), callback, state);
    }

    @Override
    public void executeAsync(Runnable task) {
        AsyncCallback<Void, Void> nullCallback = new AsyncCallback<Void, Void>() {
            @Override
            public void finished(Future<Void> result, Void aVoid) {
            }
        };
        executeAsync(task, nullCallback, null);
    }
}
