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
package com.crudetech.graphics2d.xwt;

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.unittests.EquivalentObjectFixture;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static java.util.Arrays.asList;

@RunWith(Parameterized.class)
public class CoordinateSystemEquivalenceTest extends EquivalentObjectFixture<CoordinateSystem> {
    public CoordinateSystemEquivalenceTest(CoordinateSystem first, CoordinateSystem second, CoordinateSystem third, CoordinateSystem other) {
        super(first, second, third, other);
    }
    @Parameterized.Parameters
    public static Collection<CoordinateSystem[]> createParameters(){
        final CoordinateSystem first = new CoordinateSystem(new Point2d(2, 1), RadianAngles.k120);
        final CoordinateSystem second = new CoordinateSystem(new Point2d(2, 1), RadianAngles.k120);
        final CoordinateSystem third = new CoordinateSystem(new Point2d(2, 1), RadianAngles.k120);
        return asList(new CoordinateSystem[][]{
                {first, second, third, new CoordinateSystem(new Point2d(2, 1), RadianAngles.k30)},
                {first, second, third, new CoordinateSystem(new Point2d(1, 2), RadianAngles.k120)},
                {first, second, third, new CoordinateSystem(new Point2d(1, 2), RadianAngles.k30)},
        });
    }
}
