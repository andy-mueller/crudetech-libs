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
package com.crudetech.graphics2d.xwt;

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.junit.feature.Equivalent;
import com.crudetech.junit.feature.Feature;
import com.crudetech.junit.feature.Features;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.crudetech.matcher.FloatingPointMatcher.equalTo;
import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static java.lang.Math.*;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(Features.class)
public class CoordinateSystemTest {
    @Test
    public void ctorSetsTranslation() {
        CoordinateSystem coos = new CoordinateSystem(new Point2d(1, 1), 0.0);

        final double halfOfSqrtOf2 = 0.5 * sqrt(2.0);

        final Point2d ptInCos = new Point2d(halfOfSqrtOf2, halfOfSqrtOf2);
        final Point2d ptInWcs = new Point2d(1 + halfOfSqrtOf2, 1 + halfOfSqrtOf2);

        assertThat(coos.fromWorldToCoordinateSystem(ptInWcs), is(ptInCos));
        assertThat(coos.fromCoordinateSystemToWorld(ptInCos), is(ptInWcs));
    }

    @Test
    public void ctorSetRotationAndTranslation() {
        final double alpha = atan(1 / 3);

        CoordinateSystem coos = new CoordinateSystem(new Point2d(2, 1), alpha);


        final Point2d ptInCos = new Point2d(3 * cos(alpha), 3 * sin(alpha));
        final Point2d ptInWcs = new Point2d(5, 1);


        assertThat(coos.fromCoordinateSystemToWorld(ptInCos), is(ptInWcs));
        assertThat(coos.fromWorldToCoordinateSystem(ptInWcs), is(ptInCos));
    }

    @Test
    public void ctorSetRotationAndTranslation345() {
        final double alpha = atan(4 / 5);
        final double beta = RadianAngles.k90 - alpha;

        CoordinateSystem coos = new CoordinateSystem(new Point2d(2, 1), alpha);


        final Point2d ptInCos = new Point2d(3 * cos(beta), 3 * sin(beta));
        final Point2d ptInWcs = new Point2d(2, 4);


        assertThat(coos.fromCoordinateSystemToWorld(ptInCos), is(ptInWcs));
        assertThat(coos.fromWorldToCoordinateSystem(ptInWcs), is(ptInCos));
    }

    @Test
    public void ctorSetRotation45AndTranslation() {
        final double alpha = RadianAngles.k45;
        CoordinateSystem coos = new CoordinateSystem(new Point2d(2, 1), alpha);

        final Point2d ptInCos = new Point2d(3 * cos(alpha), 3 * sin(alpha));
        final Point2d ptInWcs = new Point2d(2, 4);

        assertThat(coos.fromWorldToCoordinateSystem(ptInWcs), is(ptInCos));
        assertThat(coos.fromCoordinateSystemToWorld(ptInCos), is(ptInWcs));
    }

    @Test
    public void rotationCanBeExtractedBelowPi() {
        final double alpha = RadianAngles.k135;
        CoordinateSystem coos = new CoordinateSystem(new Point2d(2, 1), alpha);

        assertThat(coos.getLocation(), is(new Point2d(2, 1)));
        assertThat(coos.getRotation(), is(equalTo(alpha, withTol(1e-6))));
    }

    @Test
    public void rotationCanBeExtractedGreaterThanPi() {
        final double alpha = RadianAngles.k210;
        CoordinateSystem coos = new CoordinateSystem(new Point2d(2, 1), alpha);

        assertThat(coos.getLocation(), is(new Point2d(2, 1)));
        assertThat(coos.getRotation(), is(equalTo(alpha, withTol(1e-6))));
    }

    @Test
    public void settingRotationAndLocationChangesProperties() {
        CoordinateSystem coos = new CoordinateSystem(new Point2d(2, 1), RadianAngles.k210);

        coos.setLocation(new Point2d(5, 8));
        coos.setRotationInRadians(RadianAngles.k30);

        assertThat(coos.getLocation(), is(new Point2d(5, 8)));
        assertThat(coos.getRotation(), is(equalTo(RadianAngles.k30, withTol(1e-6))));
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<CoordinateSystem> equivalentFeature() {
        return new Equivalent.Factory<CoordinateSystem>() {
            @Override
            public CoordinateSystem createItem() {
                return new CoordinateSystem(new Point2d(2, 1), RadianAngles.k120);
            }

            @Override
            public List<CoordinateSystem> createOtherItems() {
                return asList(
                        new CoordinateSystem(new Point2d(2, 1), RadianAngles.k30),
                        new CoordinateSystem(new Point2d(1, 2), RadianAngles.k120),
                        new CoordinateSystem(new Point2d(1, 2), RadianAngles.k30)
                );
            }
        };
    }
}
