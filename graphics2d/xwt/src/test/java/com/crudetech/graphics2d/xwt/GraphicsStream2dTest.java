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
package com.crudetech.graphics2d.xwt;

import com.crudetech.geometry.geom2d.*;
import org.junit.Test;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.*;


public class GraphicsStream2dTest {

    @Test
    public void xformIsAppliedOnPush() {
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.getCoordinateSystemStack().pushTranslation(3, 5);

        assertThat(pipe.getCoordinateSystemStack().peek(), is(context.getTransform()));
        verify(context, times(1)).setTransform((Matrix2d) any());
    }

    @Test
    public void xformIsAppliedOnPop() {
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.getCoordinateSystemStack().pushTranslation(3, 5);
        pipe.getCoordinateSystemStack().pop();

        assertThat(pipe.getCoordinateSystemStack().peek(), is(context.getTransform()));
        verify(context, times(2)).setTransform((Matrix2d) any());
    }

    @Test
    public void penIsAppliedOnPushAndPop() {
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        assertThat(pipe.getPenStack().peek(), is(context.getPen()));

        pipe.getPenStack().push(new Pen(3, Cap.Butt, Join.Miter, 2, new float[]{1, 1}, 1));
        pipe.getPenStack().pop();

        assertThat(pipe.getPenStack().peek(), is(context.getPen()));
        verify(context, times(2)).setPen((Pen) any());
    }

    @Test
    public void brushIsAppliedOnPushAndPop() {
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        assertThat(pipe.getBrushStack().peek(), is(context.getBrush()));

        pipe.getBrushStack().push(mock(Brush.class));
        pipe.getBrushStack().pop();

        assertThat(pipe.getBrushStack().peek(), is(context.getBrush()));
        verify(context, times(2)).setBrush((Brush) any());
    }

    @Test
    public void fontIsAppliedOnPushAndPop() {
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.getFontStack().push(mock(Font.class));
        pipe.getFontStack().pop();

        assertThat(pipe.getFontStack().peek(), is(context.getFont()));
        verify(context, times(2)).setFont((Font) any());
    }

    @Test
    public void drawStringForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.drawString("Text", 3, 4);

        verify(context, times(1)).drawString("Text", 3, 4);
    }
    @Test
    public void drawString2ForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.drawString("Text", new BoundingBox2d(Point2d.Origin, new Point2d(4, 3)), 4);

        verify(context, times(1)).drawString("Text", new BoundingBox2d(Point2d.Origin, new Point2d(4, 3)), 4);
    }
    @Test
    public void drawLineForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.drawLine(new Point2d(3, 2), new Point2d(8, 2));

        verify(context, times(1)).drawLine(new Point2d(3, 2), new Point2d(8, 2));
    }
    @Test
    public void drawPolyLineForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        Iterable<Point2d> pts = asList(new Point2d(3, 2), new Point2d(8, 2), new Point2d(1, 5));
        pipe.drawPolyLine(pts);

        verify(context, times(1)).drawPolyLine(pts);
    }
    @Test
    public void drawRectangleForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.drawRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).drawRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void fillRectangleForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.fillRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).fillRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void drawElipseForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.drawEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).drawEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void fillElipseForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        pipe.fillEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).fillEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void drawBezierForwardsToContext(){
        GraphicsContextStub context = spy(new GraphicsContextStub());
        GraphicsStream2dStub pipe = new GraphicsStream2dStub(context);

        BezierCurve2d b= new BezierCurve2d(new Point2d(2, 3), new Vector2d(1, 1), new Point2d(4, 5), new Vector2d(3, 5));

        pipe.drawBezier(b);

        verify(context, times(1)).drawBezier(b);
    }
}

