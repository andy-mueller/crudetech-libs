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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class Vector2dTest {
    @Test
    public void defaultCtorSetsCoordinates(){
        Vector2d p = new Vector2d();

        assertThat(p.getX(), is(0.0));
        assertThat(p.getY(), is(0.0));
    }
    @Test
    public void  ctorWithParametersSetsCoordinates(){
        Vector2d p = new Vector2d(4.2, 42.0);

        assertThat(p.getX(), is(4.2));
        assertThat(p.getY(), is(42.0));
    }
    @Test
    public void xAxisConstantIsxAxis(){
        assertThat(Vector2d.xAxis, is(new Vector2d(1.0, 0)));
    }
    @Test
    public void yAxisConstantIsxAxis(){
        assertThat(Vector2d.yAxis, is(new Vector2d(0, 1.0)));
    }
    @Test
    public void addingVectorAddsCoordinates(){
        Vector2d v1 = new Vector2d(3.54, 5.33);
        Vector2d v2 = new Vector2d(6.46, 4.67);

        Vector2d v = v1.add(v2);

        assertThat(v, is(new Vector2d(10.0, 10.0)));
    }
    @Test
    public void toPointCreatePointWithSameCoordinates(){
        Point2d p = new Vector2d(3.54, 5.33).toPoint2d();

        assertThat(p, is(new Point2d(3.54, 5.33)));
    }
    @Test
    public void scalarProductMultipliesToCoordinates(){
        Vector2d v = new Vector2d(3.2, 4.5);

        Vector2d sv = v.scalarProductWith(2);

        assertThat(sv, is(new Vector2d(6.4, 9.0)));
    }
    @Test
    public void getLength(){
        Vector2d v = new Vector2d(3.0, 4.0);

        assertThat(v.getLength(), is(5.0));
    }
}                               
