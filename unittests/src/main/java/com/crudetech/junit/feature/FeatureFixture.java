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
package com.crudetech.junit.feature;

/**
 * A tagging interface for generic feature fixtures, i.e. an unit test class that
 * can be reused and composed using a {@link Features} suite runner. The minimum
 * requirement for an implementation is a constructor that takes a factory
 * object as an argument , and of course some tests to execute.
 * <p>
 * Have a look at the {@link Features} suite runner for more details.
 */
public interface FeatureFixture {
}
