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
package com.crudetech.functional;

/**
 * A function with no arguments.
 *
 * @param <TResult> The result type.
 */
public interface NullaryFunction<TResult> {
    /**
     * Executes the represented function and returns the result.
     * And implementation is expected to be idempotent, i.e. it can be called multiple times
     * and will return the same results
     *
     * @return The result of this function.
     */
    TResult execute();
}
