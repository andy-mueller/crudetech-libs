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
public class BoundingBoxEquivalenceTest extends EquivalentObjectFixture<BoundingBox2d>{
    public BoundingBoxEquivalenceTest(BoundingBox2d first, BoundingBox2d second, BoundingBox2d third, BoundingBox2d other) {
        super(first, second, third, other);
    }
    @Parameterized.Parameters
    public static Collection<BoundingBox2d[]> createData(){
        final BoundingBox2d first = new BoundingBox2d(3, 4, 20, 10);
        final BoundingBox2d second = new BoundingBox2d(3, 4, 20, 10);
        final BoundingBox2d third = new BoundingBox2d(3, 4, 20, 10);

        return asList(new BoundingBox2d[][]{
                {first, second, third, new BoundingBox2d(3, 5, 20, 10)},
                {first, second, third, new BoundingBox2d(3, 4, 30, 10)},
                {first, second, third, new BoundingBox2d(3, 4, 20, 20)},
        });
    }
}
