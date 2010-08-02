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
package com.crudetech.geometry.geom;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;

import static com.crudetech.matcher.FloatingPointMatcher.equalTo;
import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


@RunWith(Parameterized.class)
public class AngleTest {
    private static final double Tol = 1e-15;
    private final double angleInDegrees;

    public AngleTest(double angleInDegrees) {
        this.angleInDegrees = angleInDegrees;
    }

    @Parameterized.Parameters
    public static Collection<Double[]> createInputAngles() {
        return asList(new Double[][]{
                {0.0}, {30.0}, {45.0}, {60.0}, {90.0}, {120.0}, {135.0}, {150.0}, {180.0}, {210.0}, {225.0}, {240.0}, {270.0}, {300.0}, {315.0}, {330.0}, {360.0}
        });
    }

    @Test
    public void testConstantValue() throws NoSuchFieldException, IllegalAccessException {

        assertThat((Double) RadianAngles.class.getField("k" + (int) angleInDegrees).get(null), is(equalTo(angleInDegrees * Math.PI / 180, withTol(Tol))));
    }
}
