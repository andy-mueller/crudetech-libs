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

import com.crudetech.lang.AbstractRunnable;
import org.junit.Test;

import static com.crudetech.matcher.ThrowsException.doesThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class BoundingBox2dTest {
    @Test
    public void constructionSetsProps() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(2, 4), new Point2d(4, 6));

        assertThat(bb.getLowerLeft(), is(new Point2d(2, 4)));
    }

    @Test
    public void ctorThrowsWhenPointsAreEqual() {
        Runnable ctor = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                new BoundingBox2d(new Point2d(2, 4), new Point2d(2, 4));
            }
        };

        assertThat(ctor, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void ctorThrowsWhenBoxIsNegative() {
        Runnable ctor = new AbstractRunnable() {
            @Override
            protected void doRun() throws Throwable {
                new BoundingBox2d(new Point2d(2, 4), new Point2d(1, 1));
            }
        };

        assertThat(ctor, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void addingPoinTopRightCreatesExtendedBox() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));
        BoundingBox2d newBB = bb.add(new Point2d(6, 6));

        assertThat(newBB.getLowerLeft(), is(new Point2d(1, 1)));
        assertThat(newBB.getUpperRight(), is(new Point2d(6, 6)));
    }

    @Test
    public void addingPointBottomLeftCreatesExtendedBox() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));
        BoundingBox2d newBB = bb.add(new Point2d(0, 0));

        assertThat(newBB.getLowerLeft(), is(new Point2d(0, 0)));
        assertThat(newBB.getUpperRight(), is(new Point2d(5, 5)));
    }
    @Test
    public void addingPointOnBoundaryCreatesSameSizeBox() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));
        BoundingBox2d newBB = bb.add(new Point2d(1, 1));

        assertThat(newBB.getLowerLeft(), is(new Point2d(1, 1)));
        assertThat(newBB.getUpperRight(), is(new Point2d(5, 5)));
    }
    @Test
    public void boxContainsPoint(){
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));

        assertThat(bb.contains(new Point2d(2, 2)), is(true));
        assertThat(bb.contains(new Point2d(0, 0)), is(false));

        assertThat(bb.contains(new Point2d(1, 2)), is(true));
        assertThat(bb.contains(new Point2d(5, 2)), is(true));

        assertThat(bb.contains(new Point2d(1, 4)), is(true));
        assertThat(bb.contains(new Point2d(5, 4)), is(true));
    }
}
