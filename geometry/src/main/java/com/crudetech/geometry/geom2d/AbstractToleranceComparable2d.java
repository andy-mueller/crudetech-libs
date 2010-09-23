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
package com.crudetech.geometry.geom2d;

import com.crudetech.geometry.geom.AbstractToleranceComparable;
import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom.ToleranceComparable;


public abstract class AbstractToleranceComparable2d<TolComp extends ToleranceComparable<TolComp>> extends AbstractToleranceComparable<TolComp> {
    @Override
    protected Tolerance getGlobalTolerance() {
        return Tolerance2d.getGlobalTolerance();
    }
}
