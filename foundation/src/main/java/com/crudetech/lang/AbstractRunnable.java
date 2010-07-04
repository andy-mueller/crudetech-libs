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

import static com.crudetech.lang.SneakyThrow.sneakyThrow;


/**
 * A basic {@link Runnable} implementation that takes care of checked exceptions. Please
 * note that the checked exceptions are not swallowed. Instead they are thrown under the cover,
 * and out of sight from the compiler.
 */
public abstract class AbstractRunnable implements Runnable{
    @Override
    public void run() {
        try {
            doRun();
        } catch (Throwable e) {
            throw sneakyThrow(e);
        }
    }

    protected abstract void doRun() throws Throwable;
}
