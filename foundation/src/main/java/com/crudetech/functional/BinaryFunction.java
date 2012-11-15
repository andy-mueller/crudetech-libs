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
 * A function with two arguments.
 *
 * @param <Arg1>   The tpe of the first argument.
 * @param <Arg2>   The type of the second argument.
 * @param <Result> The result type.
 */
public interface BinaryFunction<Arg1, Arg2, Result> {
    /**
     * Executes the represented function, taking the given arguments and returning the result.
     * And implementation is expected to be idempotent, i.e. it can be called multiple times with the
     * same arguments and will return the same results
     *
     * @param lhs The first argument for the function call.
     * @param rhs The second argument for the function call.
     * @return The reult of the function call.
     */
    Result execute(Arg1 lhs, Arg2 rhs);
}
