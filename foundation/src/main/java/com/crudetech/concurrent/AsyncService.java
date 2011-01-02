package com.crudetech.concurrent;

public interface AsyncService {
    <T, State> void executeAsnc(AsyncCallback callback, State state);
}
