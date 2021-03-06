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
package com.crudetech.geometry.geom2d;

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
import static org.hamcrest.core.IsEqual.equalTo;


@RunWith(Features.class)
public class Point2dTest {
    @Test
    public void defaultCtorSetsCoordinates() {
        Point2d p = new Point2d();

        assertThat(p.getX(), is(0.0));
        assertThat(p.getY(), is(0.0));
    }

    @Test
    public void ctorWithParametersSetsCoordinates() {
        Point2d p = new Point2d(4.2, 42.0);

        assertThat(p.getX(), is(4.2));
        assertThat(p.getY(), is(42.0));
    }

    @Test
    public void distanceToCalculatesLinearDistance() {
        Point2d p = new Point2d(1, 1);
        Point2d p2 = new Point2d(4, 5);

        assertThat(p.distanceTo(p2), is(equalTo(5.0, withTol(Tolerance2d.getGlobalTolerance().getPointTolerance()))));
    }

    @Test
    public void addingVectorAddsVectorCoordinates() {
        Point2d pt = new Point2d(3.54, 5.33);
        Vector2d v = new Vector2d(6.46, 4.67);

        Point2d rv = pt.add(v);

        assertThat(rv, is(equalTo(new Point2d(10.0, 10.0))));
    }

    @Test
    public void subtractingPointComputesVectorFromThatPointToThisPoint() {
        Point2d to = new Point2d(10.0, 10.0);
        Point2d from = new Point2d(3.54, 5.33);

        Vector2d d = to.subtract(from);

        assertThat(d, is(new Vector2d(6.46, 4.67)));
    }

    @Test
    public void originConstantIsOrigin() {
        assertThat(Point2d.Origin, is(new Point2d(0, 0)));
    }

    @Test
    public void toPointCreatePointWithSameCoordinates() {
        Vector2d vector2d = new Point2d(3.54, 5.33).toVector2d();

        assertThat(vector2d, is(new Vector2d(3.54, 5.33)));
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<Point2d> equivalent = new Equivalent.Factory<Point2d>() {
        @Override
        public Point2d createItem() {
            return new Point2d(4.2, 2.0);
        }

        @Override
        public List<Point2d> createOtherItems() {
            double tol = Tolerance2d.getGlobalTolerance().getPointTolerance();

            return asList(
                    new Point2d(42.4, 2.0),
                    new Point2d(4.2, 3.0),
                    new Point2d(4.2, 2.0+tol),
                    new Point2d(4.2+tol, 2.0),
                    new Point2d(4.2+tol, 2.0+tol),

                    new Point2d(4.22, 42.2)
            );
        }
    };
}
