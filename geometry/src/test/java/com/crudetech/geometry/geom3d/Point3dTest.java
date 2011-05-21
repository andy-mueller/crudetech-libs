////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2011, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//      Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.geometry.geom3d;

import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.crudetech.matcher.FloatingPointMatcher.equalTo;
import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(Features.class)
public class Point3dTest {
    @Test
    public void ctorSetsCoordinates(){
        Point3d pt = new Point3d(3, 4, 5);

        assertThat(pt.getX(), is(equalTo(3.0, withTol(1e-6))));
        assertThat(pt.getY(), is(equalTo(4.0, withTol(1e-6))));
        assertThat(pt.getZ(), is(equalTo(5.0, withTol(1e-6))));
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<Point3d> equivalentFeature(){
        return new Equivalent.Factory<Point3d>() {
            @Override
            public Point3d createItem() {
                return new Point3d(1.0, 2.0, 3.0);
            }

            @Override
            public List<Point3d> createOtherItems() {
                final double tol = Tolerance3d.getGlobalTolerance().getPointTolerance()*1.00000001;
                return asList(
                        new Point3d(1+tol, 2, 3),
                        new Point3d(1, 2+tol, 3),
                        new Point3d(1, 2, 3+tol),
                        new Point3d(1+tol, 2+tol, 3),
                        new Point3d(1, 2+tol, 3+tol),
                        new Point3d(1+tol, 2, 3+tol),
                        new Point3d(1+tol, 2+tol, 3+tol)
                );
            }
        };
    }
}
