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
import com.crudetech.lang.AbstractRunnable;
import com.crudetech.lang.ArgumentNullException;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static com.crudetech.matcher.ThrowsException.doesThrow;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

@RunWith(Features.class)
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
    public void addingPointTopRightCreatesExtendedBox() {
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
    public void addingNullReturnsEquivalentBox() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));
        BoundingBox2d newBB = bb.add((Point2d) null);

        assertThat(newBB, is(new BoundingBox2d(1, 1, 4, 4)));
    }

    @Test
    public void addingNullBoxReturnsEquivalentBox() {
        final BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));
        Runnable doAdd = new Runnable() {
            @Override
            public void run() {
                bb.add((BoundingBox2d) null);
            }
        };

        assertThat(doAdd, doesThrow(ArgumentNullException.class));
    }

    @Test
    public void addingEmptyBoxArrayReturnsEquivalentBox() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));
        BoundingBox2d newBB = bb.add(new BoundingBox2d[]{});

        assertThat(newBB, is(new BoundingBox2d(1, 1, 4, 4)));
    }

    @Test
    public void boxContainsPoint() {
        BoundingBox2d bb = new BoundingBox2d(new Point2d(1, 1), new Point2d(5, 5));

        assertThat(bb.contains(new Point2d(2, 2)), is(true));
        assertThat(bb.contains(new Point2d(0, 0)), is(false));

        assertThat(bb.contains(new Point2d(1, 2)), is(true));
        assertThat(bb.contains(new Point2d(5, 2)), is(true));

        assertThat(bb.contains(new Point2d(1, 4)), is(true));
        assertThat(bb.contains(new Point2d(5, 4)), is(true));
    }

    @Test
    public void addingBox() {
        BoundingBox2d bb1 = new BoundingBox2d(10, 10, 20, 20);
        BoundingBox2d bb2 = new BoundingBox2d(20, 20,40, 20);
        BoundingBox2d bb3 = new BoundingBox2d(60, 40, 20, 20);

        BoundingBox2d newBB = bb1.add(bb2, bb3);

        assertThat(newBB, is(new BoundingBox2d(10, 10, 70, 50)));
    }
    @Test
    public void locationIsLowerLeft(){
        BoundingBox2d bb = new BoundingBox2d(10, 10, 20, 20);

        assertThat(bb.getLocation(), is(new Point2d(10, 10)));
    }

    @Feature(Equivalent.class)
    public static Equivalent.Factory<BoundingBox2d> bb2dEquFactory = new Equivalent.Factory<BoundingBox2d>() {
        @Override
        public BoundingBox2d createItem() {
            return new BoundingBox2d(3, 4, 20, 10);
        }

        @Override
        public List<BoundingBox2d> createOtherItems() {
            return asList(
                    new BoundingBox2d(3, 5, 20, 10),
                    new BoundingBox2d(3, 4, 30, 10),
                    new BoundingBox2d(3, 4, 20, 20)
            );
        }
    };
}
