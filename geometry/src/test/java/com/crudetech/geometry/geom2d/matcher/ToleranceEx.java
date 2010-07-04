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
package com.crudetech.geometry.geom2d.matcher;

import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.geometry.geom2d.Tolerance2d;

public class ToleranceEx {
    private ToleranceEx(){}

    public static Tolerance withTol(Tolerance tol){
        return tol;
    }
    public static Tolerance withGlobalTol(){
        return Tolerance2d.getGlobalTolerance();
    }
}
