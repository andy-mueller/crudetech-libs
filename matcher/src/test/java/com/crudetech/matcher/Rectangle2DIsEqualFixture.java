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

import java.awt.geom.Rectangle2D;

import static com.crudetech.matcher.FloatingPointMatcher.withTol;
import static com.crudetech.matcher.Rectangle2DIsEqual.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;


public class Rectangle2DIsEqualFixture {
    @Test
    public void rectanglesAreEqual(){
        Rectangle2D r1 = new Rectangle2D.Double(4, 3, 100, 20);
        Rectangle2D r2 = new Rectangle2D.Float(4, 3, 100, 20);

        assertThat(r1, is(equalTo(r2, withTol(1e-6))));
    }
    @Test
    public void rectanglesNotAreEqual(){
        Rectangle2D r1 = new Rectangle2D.Double(4.001f, 3, 100, 20);
        Rectangle2D r2 = new Rectangle2D.Float(4, 3, 100, 20);

        assertThat(r1, is(not(equalTo(r2, 1e-6))));
    }

}
