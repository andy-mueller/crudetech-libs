////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2010, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//     Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.lang;

import javax.swing.*;
import java.util.concurrent.*;

/**
 * When implemented, it offers several methods to dispatch a {@link Runnable} to another thread.
 * A typical use case for this interface is to abstract away calls to
 * {@link javax.swing.SwingUtilities#invokeLater(java.lang.Runnable runnable)}.
 * <p>
 * This interface serves as a lightweight alternative for a full blown {@link java.util.concurrent.ExecutorService}
 * implementation.
 */
public interface InvocationService {
    /**
     * Returns true, if the call in thread is not the target thread.
     *
     * @return A boolean indicating if an invocation is required.
     */
    boolean isInvocationRequired();

    /**
     * Invokes the passed in {@link Callable} implementation asynchronously in the target
     * thread. Synchronisation and/or results can be obtained from the returned
     * {@link Future} implementation.
     * @param callable The callable to be executed
     * @param <T>
     * @return  A Future implementation for the results an synchronization.
     */
    <T> Future<T> beginInvocation(Callable<T> callable);

    /**
     * Invokes the passed in {@link Runnable} implementation asynchronously in the target
     * thread. Synchronisation can be obtained from the returned
     * @link Future} implementation.
     * @param runnable The runnable to be executed.
     * @return 
     */
    Future<Void> beginInvocation(Runnable runnable);

    /**
     * A service that invokes {@link Runnable}s on the AWT event dispatching thread.
     */
    static final InvocationService Awt = new InvocationService() {
        @Override
        public boolean isInvocationRequired() {
            return !SwingUtilities.isEventDispatchThread();
        }

        @Override
        public <T> Future<T> beginInvocation(Callable<T> callable) {
            FutureTask<T> task = new FutureTask<T>(callable);
            SwingUtilities.invokeLater(task);
            return task;
        }

        @Override
        public Future<Void> beginInvocation(Runnable runnable) {
             return beginInvocation(Executors.callable(runnable, (Void)null));
        }
    };
    /**
     * A service that invokes {@link Runnable}s on the calling thread. This implementations
     * is to be used mainly in unit tests.
     */
    static final InvocationService Simple = new InvocationService() {
        @Override
        public boolean isInvocationRequired() {
            return false;
        }
        @Override
        public <T> Future<T> beginInvocation(Callable<T> callable) {
            FutureTask<T> task = new FutureTask<T>(callable);
            task.run();
            return task;
        }

        @Override
        public Future<Void> beginInvocation(Runnable runnable) {
            return beginInvocation(Executors.callable(runnable, (Void)null));
        }
    };
}