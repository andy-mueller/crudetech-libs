package com.crudetech.concurrent;


import java.util.concurrent.Future;

public interface AsyncCallback<T, State> {
    void finished(Future<T> result, State state);
}
