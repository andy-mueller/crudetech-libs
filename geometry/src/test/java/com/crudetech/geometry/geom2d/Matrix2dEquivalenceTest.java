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

import com.crudetech.unittests.EquivalentObjectFixture;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;


@RunWith(Parameterized.class)
public class Matrix2dEquivalenceTest extends EquivalentObjectFixture<Matrix2d> {
    public Matrix2dEquivalenceTest(Matrix2d first, Matrix2d second, Matrix2d third, Matrix2d other) {
        super(first, second, third, other);
    }

    @Parameterized.Parameters
    public static Collection<Matrix2d[]> createParameters() {
        double[][] rawMx = new double[][]{
                {3, 4, 32},
                {4, 1, 45},
                {3, 333, 4}

        };

        return asList(new Matrix2d[][]{
                {new Matrix2d(rawMx), new Matrix2d(rawMx), new Matrix2d(rawMx), Matrix2d.Identity},
        });
    }
}
