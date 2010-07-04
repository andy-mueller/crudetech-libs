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

/**
 * This exception is thrown when it is impossible or
 * undesirable to consume or throw a checked exception.
 * <p>
 * An alternative to this exception is the {@link com.crudetech.lang.SneakyThrow}
 * class.
 */
public class UnhandledException extends RuntimeException{
    public UnhandledException() {
    }

    public UnhandledException(String s) {
        super(s);
    }

    public UnhandledException(String s, Throwable throwable) {
        super(s, throwable);
    }

    public UnhandledException(Throwable throwable) {
        super(throwable);
    }
}
