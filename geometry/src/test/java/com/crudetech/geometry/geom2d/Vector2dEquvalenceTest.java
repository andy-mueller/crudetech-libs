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
public class Vector2dEquvalenceTest extends EquivalentObjectFixture {
    public Vector2dEquvalenceTest(Object first, Object second, Object third, Object other) {
        super(first, second, third, other);
    }
    @Parameterized.Parameters
    public static Collection<Vector2d[]> createParameters(){
        Vector2d first = new Vector2d(4.2, 2.0);
        Vector2d second = new Vector2d(4.2, 2.0);
        Vector2d third = new Vector2d(4.2, 2.0);

        double tol = Tolerance2d.getGlobalTolerance().getPointTolerance();

        return asList(new Vector2d[][]{
                { first, second, third, new Vector2d(42.4, 2.0) },
                { first, second, third, new Vector2d(4.2, 42.2) },
                { first, second, third, new Vector2d(42.4, 43.3) },

                { first, new Vector2d(4.2, 2.0 + tol*0.99), third, new Vector2d(42.4, 43.3) },
                { first, new Vector2d(4.2, 2.0 + tol*0.99), third, new Vector2d(42.4, 43.3 + tol) },
        });
    }
}
