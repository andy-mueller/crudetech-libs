package com.crudetech.concurrent;


import com.crudetech.lang.InvocationService;

import java.util.concurrent.Future;

public class InvokingAsyncService implements AsyncService {
    private final AsyncService decorated;
    private final InvocationService invocationService;

    public InvokingAsyncService(AsyncService decorated, InvocationService invocationService) {
        this.decorated = decorated;
        this.invocationService = invocationService;
    }

    @Override
    public <T, State> void executeAsnc(final AsyncCallback callback, State state) {
        AsyncCallback invokingCallback = new AsyncCallback() {
            @Override
            public <T, State> void finished(final Future<T> result, final State state) {
                invocationService.beginInvocation(new Runnable() {
                    @Override
                    public void run() {
                        callback.finished(result, state);
                    }
                });
            }
        };

        decorated.executeAsnc(invokingCallback, state);
    }

    static <T> T resultOf(Future<T> result){
        try {
            return result.get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
