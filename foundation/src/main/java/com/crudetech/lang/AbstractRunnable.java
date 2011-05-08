////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2011, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//      Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.lang;

import static com.crudetech.lang.SneakyThrow.sneakyThrow;


/**
 * A basic {@link Runnable} implementation that takes care of checked exceptions. Please
 * note that the checked exceptions are not swallowed. Instead they are thrown under the cover,
 * and out of sight from the compiler.
 * <p>
 * This class is intended to be used in cases when you would simply catch checked exceptions
 * inside your {@link Runnable#run} implementation and rethrow it wrapped inside
 * a {@link RuntimeException}. So the following code would work:
 * <p/>
 * <pre>{@code
 *    Runnable r = new AbstractRunnable {
 *        @Override
 *        protected void doRun() throws Throwable {
 *            throw new FileNotFoundException();
 *        }
 *     }
 *
 *     try{
 *          r.run();
 *     }catch(FileNotFoundException e){
 *         // .. you will get here....
 *     }
 *
 * }</pre>
 */
public abstract class AbstractRunnable implements Runnable {
    @Override
    public void run() {
        try {
            doRun();
        } catch (Throwable e) {
            exceptionThrown(e);
            throw sneakyThrow(e);
        }
    }

    protected void exceptionThrown(Throwable throwable) {
        //no-op
    }

    protected abstract void doRun() throws Throwable;
}


