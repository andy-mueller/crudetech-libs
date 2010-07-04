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

import java.awt.geom.Point2D;
import java.util.Collection;

import static java.util.Arrays.asList;


@RunWith(Parameterized.class)
public class Point2dEquvalenceTest extends EquivalentObjectFixture {
    public Point2dEquvalenceTest(Object first, Object second, Object third, Object other) {
        super(first, second, third, other);
    }
    @Parameterized.Parameters
    public static Collection<Point2d[]> createParameters(){
        Point2d first = new Point2d(4.2, 2.0);
        Point2d second = new Point2d(4.2, 2.0);
        Point2d third = new Point2d(4.2, 2.0);

        double tol = Tolerance2d.getGlobalTolerance().getPointTolerance();
        
        return asList(new Point2d[][]{
                { first, second, third, new Point2d(42.4, 2.0) },
                { first, second, third, new Point2d(4.2, 42.2) },
                { first, second, third, new Point2d(42.4, 43.3) },

                { first, new Point2d(4.2, 2.0 + tol*0.99), third, new Point2d(42.4, 43.3) },
                { first, new Point2d(4.2, 2.0 + tol*0.99), third, new Point2d(42.4, 43.3 + tol) },
        });
    }
}
