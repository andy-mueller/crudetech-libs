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

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.BoundingBox2d;
import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.CoordinateSystem;
import com.crudetech.graphics2d.xwt.CoordinateSystemStack;
import com.crudetech.graphics2d.xwt.GraphicsStream2d;
import com.crudetech.graphics2d.xwt.GraphicsStream2dStub;
import org.junit.Test;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import static com.crudetech.matcher.RangeIsEqual.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.sameInstance;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;


public class AbstractWidgetTest {
    private class WidgetStub extends AbstractWidget {
        private int drawEcsCount = 0;            
        @Override
        protected void drawEcs(GraphicsStream2d stream) {
            ++drawEcsCount;
        }

        @Override
        public BoundingBox2d getBoundingBox() {
            return new BoundingBox2d(new Point2d(2, 3), new Point2d(6, 99));
        }
    }

    @Test
    public void ctorPlacesWidgetOnOriginWithNoRotation() {
        AbstractWidget w = new WidgetStub();

        assertThat(w.getEcs(), is(new CoordinateSystem(Point2d.Origin, RadianAngles.k0)));
    }

    @Test
    public void newInstanceHasDefaultDispProps() {
        AbstractWidget w = new WidgetStub();
        assertThat(w.getDisplayProperties().iterator().hasNext(), is(false));
    }

    @Test
    public void setDispProps() {
        AbstractWidget w = new WidgetStub();

        WidgetDisplayProperties props = new WidgetDisplayProperties();
        w.setDisplayProperties(props);

        assertThat(w.getDisplayProperties(), is(sameInstance(props)));
    }

    @Test
    public void paintPushesCoordinateSystem() {
        GraphicsStream2d stream = spy(new GraphicsStream2dStub());
        final CoordinateSystemStack coosStack = spy(stream.getCoordinateSystemStack());
        when(stream.getCoordinateSystemStack()).thenAnswer(new Answer<Object>() {
            @Override
            public Object answer(InvocationOnMock invocationOnMock) throws Throwable {
                return coosStack;
            }
        });
        Matrix2d[] originalStack = coosStack.toArray();

        WidgetStub w = new WidgetStub();
        w.getEcs().setLocation(new Point2d(3, 5));
        w.getEcs().setRotationInRadians(RadianAngles.k30);

        w.draw(stream);

        verify(coosStack).pushCoordinateSystem(new CoordinateSystem(new Point2d(3, 5), RadianAngles.k30));
        assertThat(w.drawEcsCount, is(1));
        assertThat(coosStack, is(equalTo(originalStack)));
    }
}
