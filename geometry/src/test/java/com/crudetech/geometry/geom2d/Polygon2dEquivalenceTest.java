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
public class Polygon2dEquivalenceTest extends EquivalentObjectFixture<Polygon2d> {
    public Polygon2dEquivalenceTest(Polygon2d first, Polygon2d second, Polygon2d third, Polygon2d other) {
        super(first, second, third, other);
    }

    @Parameterized.Parameters
    public static Collection<Polygon2d[]> createData() {
        final Polygon2d first = new Polygon2d(asList(new Point2d(0, 0), new Point2d(10, 0), new Point2d(10, 10)));
        final Polygon2d second = new Polygon2d(asList(new Point2d(0, 0), new Point2d(10, 0), new Point2d(10, 10)));
        final Polygon2d third = new Polygon2d(asList(new Point2d(0, 0), new Point2d(10, 0), new Point2d(10, 10)));

        return asList(new Polygon2d[][]{
                {first, second, third, new Polygon2d(asList(new Point2d(10, 0), new Point2d(10, 0), new Point2d(10, 10)))},
                {first, second, third, new Polygon2d(asList(new Point2d(0, 0), new Point2d(8, 0), new Point2d(10, 10)))},
                {first, second, third, new Polygon2d(asList(new Point2d(0, 0), new Point2d(10, 0), new Point2d(8, 10)))},
                {first, second, third, new Polygon2d(asList(new Point2d(0, 0), new Point2d(10, 0), new Point2d(10, 10), new Point2d(3, 3)))},
        });
    }
}
