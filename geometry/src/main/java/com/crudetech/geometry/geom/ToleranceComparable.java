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
package com.crudetech.geometry.geom;

/**
 * Implemented by all geometric objects in this package, demonstrating
 * their tolerance based equality handling. Tis enables generic operable
 * service classes like {@link ToleranceComparer}.
 */
public interface ToleranceComparable<TolComp extends ToleranceComparable<TolComp>> {
    int hashCode(Tolerance tol);
    boolean equals(TolComp rhs, Tolerance tol);
}
