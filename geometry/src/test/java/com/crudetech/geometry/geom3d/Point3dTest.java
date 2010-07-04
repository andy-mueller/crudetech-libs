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

import org.junit.Test;

import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static com.crudetech.matcher.FloatingPointMatcher.equalTo;


public class Point3dTest {
    @Test
    public void ctorSetsCoordinates(){
        Point3d pt = new Point3d(3, 4, 5);

        assertThat(pt.getX(), is(equalTo(3.0, withTol(1e-6))));
        assertThat(pt.getY(), is(equalTo(4.0, withTol(1e-6))));
        assertThat(pt.getZ(), is(equalTo(5.0, withTol(1e-6))));
    }
}
