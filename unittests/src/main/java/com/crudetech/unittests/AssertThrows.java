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
package com.crudetech.unittests;

import java.text.MessageFormat;

import static org.junit.Assert.fail;

/**
 *  Simple helper for testing the presence of exceptions in a safe manner. However, as it is in the
 *  old junit assert style, consider using the corresponding <code>ThrowsException</code> hamcrest
 *  matcher instead.
 */
public class AssertThrows {

    public static void assertThrows(Runnable runnable, Class<? extends Exception> exceptionClass) {
        try {
            runnable.run();
        } catch (Exception e) {
            if(exceptionClass.isAssignableFrom(e.getClass())){
                return;
            }
            fail(MessageFormat.format("\nExpected: Exception of type {0}.\nGot:      Exception of type {1}", exceptionClass, e.getClass()));
            return;
        }
        fail(MessageFormat.format("No exception at all was thrown! Expected type was {0}.", exceptionClass));
    }

}
