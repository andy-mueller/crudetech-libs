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
import com.crudetech.unittests.EquivalentObjectFixture;
import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class Vector3dEquivalenceTest extends EquivalentObjectFixture<Vector3d> {
    public Vector3dEquivalenceTest(Vector3d first, Vector3d second, Vector3d third, Vector3d other) {
        super(first, second, third, other);
    }
    @Parameterized.Parameters
    public static Collection<Vector3d[]> createParameters() {
        final Vector3d first = new Vector3d(1, 2, 3);
        final Vector3d second = new Vector3d(1, 2, 3);
        final Vector3d third = new Vector3d(1, 2, 3);
        return asList(new Vector3d[][]{
                {first, second, third, new Vector3d(2, 2, 3)},
                {first, second, third, new Vector3d(1, 3, 3)},
                {first, second, third, new Vector3d(1, 2, 4)},

                {new Vector3d(1.0001, 2, 3), new Vector3d(1.00000, 2, 3), new Vector3d(1.00005, 2, 3), new Vector3d(1.1, 2, 3)},

        });
    }

    private Tolerance oldTol;

    @Before
    public void before() {
        oldTol = Tolerance3d.getGlobalTolerance();
        Tolerance3d.setGlobalTolerance(new Tolerance(1e-12, 1e-3));
    }

    @After
    public void after() {
        Tolerance3d.setGlobalTolerance(oldTol);
    }
}
