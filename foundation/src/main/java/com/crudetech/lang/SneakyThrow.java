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


/**
 * Helper class to throw checked exceptions without being forced to declare
 * a throws clause. A typical use case for this class is every time where
 * you are forced by the compiler to catch an exception and simply would wrap it into a
 * {@link RuntimeException} an rethrow it. By using sneaky throws, you can simply
 * rethrow the checked exception.
 * <p>
 * <pre>
 * import static com.crudetech.lang.SneakyThrow.sneakyThrow;
 * // ...
 * try {
 *     doRun();
 * } catch (Exception e) {
 *     throw sneakyThrow(e);
 * }
 * </pre>
 * <p>
 * Please note, that this technique causes the following problem, when used behind a proxy.
 * Most proxy frameworks throw a {@link java.lang.reflect.UndeclaredThrowableException},
 * if the proxied object throws a checked exception, that was not declared.
 */
public class SneakyThrow {
    private SneakyThrow(){}
    public static RuntimeException sneakyThrow(Throwable t) {
        if (t == null) throw new IllegalArgumentException("t");
        SneakyThrow.<RuntimeException>sneakyThrowImp(t);
        return null;
    }

    @SuppressWarnings("unchecked")
    private static <T extends Throwable> void sneakyThrowImp(Throwable t) throws T {
        throw (T) t;
    }
}