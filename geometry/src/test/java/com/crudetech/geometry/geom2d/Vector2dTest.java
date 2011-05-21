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

import com.crudetech.geometry.geom.RadianAngles;
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
public class Vector2dTest {
    @Test
    public void defaultCtorSetsCoordinates() {
        Vector2d p = new Vector2d();

        assertThat(p.getX(), is(0.0));
        assertThat(p.getY(), is(0.0));
    }

    @Test
    public void ctorWithParametersSetsCoordinates() {
        Vector2d p = new Vector2d(4.2, 42.0);

        assertThat(p.getX(), is(4.2));
        assertThat(p.getY(), is(42.0));
    }

    @Test
    public void xAxisConstantIsxAxis() {
        assertThat(Vector2d.xAxis, is(new Vector2d(1.0, 0)));
    }

    @Test
    public void yAxisConstantIsyAxis() {
        assertThat(Vector2d.yAxis, is(new Vector2d(0, 1.0)));
    }

    @Test
    public void addingVectorAddsCoordinates() {
        Vector2d v1 = new Vector2d(3.54, 5.33);
        Vector2d v2 = new Vector2d(6.46, 4.67);

        Vector2d v = v1.add(v2);

        assertThat(v, is(new Vector2d(10.0, 10.0)));
    }

    @Test
    public void toPointCreatePointWithSameCoordinates() {
        Point2d p = new Vector2d(3.54, 5.33).toPoint2d();

        assertThat(p, is(new Point2d(3.54, 5.33)));
    }

    @Test
    public void scalarProductMultipliesToCoordinates() {
        Vector2d v = new Vector2d(3.2, 4.5);

        Vector2d sv = v.scalarProductWith(2);

        assertThat(sv, is(new Vector2d(6.4, 9.0)));
    }

    @Test
    public void getLength() {
        Vector2d v = new Vector2d(3.0, 4.0);

        assertThat(v.getLength(), is(5.0));
    }

    @Test
    public void angleToForLessPi() {
        Vector2d v = new Vector2d(-1, 1);

        assertThat(Vector2d.xAxis.angleTo(v), is(equalTo(RadianAngles.k135, withTol(1e-12))));
    }

    @Test
    public void angleToForGreaterPi() {
        Vector2d v = new Vector2d(-1, -1);

        assertThat(Vector2d.xAxis.angleTo(v), is(equalTo(RadianAngles.k225, withTol(1e-12))));
    }

    @Test
    public void angleToFor0() {
        Vector2d v = new Vector2d(1, 0);

        assertThat(Vector2d.xAxis.angleTo(v), is(equalTo(RadianAngles.k0, withTol(1e-12))));
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<Vector2d> equivalent = new Equivalent.Factory<Vector2d>() {
        @Override
        public Vector2d createItem() {
            return new Vector2d(4.2, 2.0);
        }

        @Override
        public List<Vector2d> createOtherItems() {
            final double tol = Tolerance2d.getGlobalTolerance().getVectorTolerance()*1.0000001;

            return asList(
                    new Vector2d(4.2+tol, 2.0),
                    new Vector2d(4.2+tol, 2.0),
                    new Vector2d(4.2, 2.0+tol),
                    new Vector2d(4.2+tol, 2.0+tol)
            );
        }
    };
}                               
