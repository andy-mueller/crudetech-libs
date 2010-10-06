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

import com.crudetech.lang.ArgumentOutOfBoundsException;
import org.junit.Test;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNot.not;
import static org.hamcrest.core.IsSame.sameInstance;


public class Polygon2dTest {
    @Test
    public void polygonIsConstructedByAClosedRangeOfPoints(){
        Iterable<Point2d> pts = asList(new Point2d(0,0), new Point2d(10,0), new Point2d(10,10), new Point2d(0,0));
        Polygon2d poly = new Polygon2d(pts);

        Iterable<Point2d> result = poly.getCornerPoints();

        assertThat(pts, is(not(sameInstance(result))));
        assertThat(result, is(equalTo(new Point2d(0,0), new Point2d(10,0), new Point2d(10,10))));
    }
    @Test
    public void numCorners(){
        Iterable<Point2d> pts = asList(new Point2d(0,0), new Point2d(10,0), new Point2d(10,10), new Point2d(0,0));
        Polygon2d poly = new Polygon2d(pts);

        assertThat(poly.getNumberOfCorners(), is(3));
    }
    
    @Test
    public void polygonIsConstructedByAnUnclosedRangeOfPoints(){
        Iterable<Point2d> pts = asList(new Point2d(0,0), new Point2d(10,0), new Point2d(10,10));
        Polygon2d poly = new Polygon2d(pts);

        Iterable<Point2d> result = poly.getCornerPoints();

        assertThat(result, is(not(sameInstance(pts))));
        assertThat(result, is(equalTo(new Point2d(0,0), new Point2d(10,0), new Point2d(10,10))));
    }

    @Test
    public void constructionWithLessThanThreePointsThrows(){
        Runnable constructionWithLessThanThreePointsThrows = new Runnable() {
            @Override
            public void run() {
                Iterable<Point2d> pts = asList(new Point2d(0,0), new Point2d(10,0));
                new Polygon2d(pts);
            }
        };

        assertThat(constructionWithLessThanThreePointsThrows, doesThrow(ArgumentOutOfBoundsException.class));
    }

    //ctor checks intersection
}
