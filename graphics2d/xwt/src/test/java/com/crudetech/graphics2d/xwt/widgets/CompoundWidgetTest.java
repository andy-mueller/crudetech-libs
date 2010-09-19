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
package com.crudetech.graphics2d.xwt.widgets;

import com.crudetech.geometry.geom2d.BoundingBox2d;
import com.crudetech.geometry.geom2d.Point2d;
import org.junit.Test;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsSame.sameInstance;
import static org.mockito.Mockito.mock;

public class CompoundWidgetTest {
    @Test
    public void ctorAggregatesSubWidgets() {
        Iterable<Widget> widgets = asList(
                mock(Widget.class),
                mock(Widget.class),
                mock(Widget.class)
        );

        CompoundWidget cw = new CompoundWidget(widgets);

        assertThat(cw.getComponents(), is(equalTo(widgets)));
        assertThat(cw.getComponents(), is(not(sameInstance(widgets))));
    }
    @Test
    public void boundingBoxEnclosesAllSubWidgets(){
        Widget w1 = new RectangularBorderedWidget(10, 10, RectangularBorderedWidgetDispProps.Default);
        w1.getEcs().setLocation(new Point2d(10, 10));

        Widget w2 = new RectangularBorderedWidget(20, 10, RectangularBorderedWidgetDispProps.Default);
        w2.getEcs().setLocation(new Point2d(20, 20));

        Widget w3 = new RectangularBorderedWidget(10,  5, RectangularBorderedWidgetDispProps.Default);
        w2.getEcs().setLocation(new Point2d(35, 25));

        Widget cw = new CompoundWidget(asList(w1, w2, w3));

      //  assertThat(cw.getBoundingBox(), is(new BoundingBox2d(10, 10, 35, 20)));
    }
}
