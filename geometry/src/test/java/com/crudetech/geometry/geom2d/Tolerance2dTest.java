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

import com.crudetech.geometry.geom.Tolerance;
import com.crudetech.lang.ArgumentNullException;
import org.junit.Test;

import static com.crudetech.matcher.ThrowsException.doesThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class Tolerance2dTest {
    @Test
    public void ctor(){
        Tolerance tol = new Tolerance(1e-4, 1e-3);

        assertThat(tol.getVectorTolerance(), is(1e-3));
        assertThat(tol.getPointTolerance(), is(1e-4));
    }
    @Test
    public void globalInstance(){
        Tolerance2d.setGlobalTolerance(new Tolerance(1e-4, 1e-3));
        assertThat(Tolerance2d.getGlobalTolerance().getVectorTolerance(), is(1e-3));
        assertThat(Tolerance2d.getGlobalTolerance().getPointTolerance(), is(1e-4));                
    }
    @Test
    public void globalInstanceMustNotBeNull(){
        Runnable setGlobalTol = new Runnable() {
            @Override
            public void run() {
                Tolerance2d.setGlobalTolerance(null);
            }
        };
        assertThat(setGlobalTol, doesThrow(ArgumentNullException.class));
    }
}
