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
package com.crudetech.geometry.geom3d;

import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.lang.ArgumentNullException;

/**
 * The global tolerance that is implicitly used by all 3d operations. Refer to the documentation of
 * {@link com.crudetech.geometry.geom.Tolerance} for more details.
 * <p>
 *  Changing the global tolerance will affect all object comparison and hashing operations inside this package, e.g.
 * you will not be able to find your objects inside a hash table anymore.
 * <p/>
 * Setting the global tolerance is not thread safe!
 * */
public final class Tolerance3d {
    private Tolerance3d(){}
    private static Tolerance globalTolerance = new Tolerance(1e-6, 1e-6);
                              /**
     * The global tolerance instance that is implicitly used by every object instance for comparison.
     *
     * @return
     */
    public static Tolerance getGlobalTolerance() {
        return globalTolerance;
    }

    /**
     * Sets the global tolerance object.
     *
     * @param globalTolerance The new global tolerance. Must not be null!
     */
    public static void setGlobalTolerance(Tolerance globalTolerance) {
        if (globalTolerance == null) throw new ArgumentNullException("Global tolerance must not be null!!");
        Tolerance3d.globalTolerance = globalTolerance;
    }
}
