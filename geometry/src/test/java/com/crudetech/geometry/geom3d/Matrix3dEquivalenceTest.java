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

import com.crudetech.unittests.EquivalentObjectFixture;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class Matrix3dEquivalenceTest extends EquivalentObjectFixture<Matrix3d> {
    public Matrix3dEquivalenceTest(Matrix3d first, Matrix3d second, Matrix3d third, Matrix3d other) {
        super(first, second, third, other);
    }

    @Parameterized.Parameters
    public static Collection<Matrix3d[]> createParameters() {
        double[][] rawMx = new double[][]{
                {3, 4, 32, 3},
                {4, 1, 45, 4},
                {3, 333, 4, 66},
                {3, 4, 32, 3},
        };

        return asList(new Matrix3d[][]{
                {new Matrix3d(rawMx), new Matrix3d(rawMx), new Matrix3d(rawMx), Matrix3d.Identity},
        });
    }
}
