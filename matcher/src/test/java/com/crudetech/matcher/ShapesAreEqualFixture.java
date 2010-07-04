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
package com.crudetech.matcher;

import org.junit.Test;

import java.awt.*;
import java.awt.geom.RoundRectangle2D;

import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static com.crudetech.matcher.ShapesAreEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class ShapesAreEqualFixture {
    @Test
    public void shapesAreEqual(){
        Shape s1 = new RoundRectangle2D.Double(0, 0, 100, 50, 10, 10);
        Shape s2 = new RoundRectangle2D.Double(0, 0, 100, 50, 10, 10);

        assertThat(s1, is(equalTo(s2, withTol(1e-4))));
    }

    @Test
    public void shapesAreNotEqual(){
        Shape s1 = new RoundRectangle2D.Double(0, 0, 100, 50, 10, 10);
        Shape s2 = new RoundRectangle2D.Double(0, 0, 100, 50, 10, 10.01);

        assertThat(s1, is(not(equalTo(s2, withTol(1e-3)))));
    }
}
