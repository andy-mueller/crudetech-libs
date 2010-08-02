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
    class GraphicsStream2dImp extends GraphicsStream2d {

        GraphicsStream2dImp(GraphicsContext pipe) {
            super(pipe);
        }
    }

    class GraphicsContextImp implements GraphicsContext {
        private Font font = new Font("Arial", FontStyle.Bold, 12);
        private Matrix2d mx = Matrix2d.preMultiply(Matrix2d.createScale(3, 5), Matrix2d.createTranslation(3, 2), Matrix2d.createRotationInRadians(0.022));
        private Pen pen = new Pen(1, Cap.Butt, Join.Round, 1, new float[]{2, 1}, 1);
        private Brush brush = new Brush() {
        };

        @Override
        public Matrix2d getTransform() {
            return mx;
        }

        @Override
        public void setTransform(Matrix2d xform) {
            mx = xform;
        }

        @Override
        public Pen getPen() {
            return pen;
        }

        @Override
        public void setPen(Pen pen) {
            this.pen = pen;
        }

        @Override
        public Brush getBrush() {
            return brush;
        }


        @Override
        public void setBrush(Brush brush) {
            this.brush = brush;
        }

        @Override
        public Font getFont() {
            return font;
        }

        @Override
        public void setFont(Font font) {
        }

        @Override
        public void drawString(String string, double x, double y) {
        }

        @Override
        public void drawString(String string, BoundingBox2d bound, double padding) {
        }

        @Override
        public void drawLine(Point2d start, Point2d end) {
        }

        @Override
        public void drawCurve(BoundingBox2d rect) {
        }

        @Override
        public void drawRectangle(BoundingBox2d rect) {
        }

        @Override
        public void fillRectangle(BoundingBox2d rect) {
        }

        @Override
        public void drawPolyLine(Iterable<Point2d> pts) {
        }

        public void drawEllipse(BoundingBox2d boundingBox2d) {
        }

        @Override
        public void fillEllipse(BoundingBox2d bounds) {
        }

        @Override
        public void drawBezier(BezierCurve2d bezier) {
        }
    }

    @Test
    public void xformIsAppliedOnPush() {
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.getCoordinateSystemStack().pushTranslation(3, 5);

        assertThat(pipe.getCoordinateSystemStack().peek(), is(context.getTransform()));
        verify(context, times(1)).setTransform((Matrix2d) any());
    }

    @Test
    public void xformIsAppliedOnPop() {
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.getCoordinateSystemStack().pushTranslation(3, 5);
        pipe.getCoordinateSystemStack().pop();

        assertThat(pipe.getCoordinateSystemStack().peek(), is(context.getTransform()));
        verify(context, times(2)).setTransform((Matrix2d) any());
    }

    @Test
    public void penIsAppliedOnPushAndPop() {
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        assertThat(pipe.getPenStack().peek(), is(context.getPen()));

        pipe.getPenStack().push(new Pen(3, Cap.Butt, Join.Miter, 2, new float[]{1, 1}, 1));
        pipe.getPenStack().pop();

        assertThat(pipe.getPenStack().peek(), is(context.getPen()));
        verify(context, times(2)).setPen((Pen) any());
    }

    @Test
    public void brushIsAppliedOnPushAndPop() {
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        assertThat(pipe.getBrushStack().peek(), is(context.getBrush()));

        pipe.getBrushStack().push(mock(Brush.class));
        pipe.getBrushStack().pop();

        assertThat(pipe.getBrushStack().peek(), is(context.getBrush()));
        verify(context, times(2)).setBrush((Brush) any());
    }

    @Test
    public void fontIsAppliedOnPushAndPop() {
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.getFontStack().push(mock(Font.class));
        pipe.getFontStack().pop();

        assertThat(pipe.getFontStack().peek(), is(context.getFont()));
        verify(context, times(2)).setFont((Font) any());
    }

    @Test
    public void drawStringForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.drawString("Text", 3, 4);

        verify(context, times(1)).drawString("Text", 3, 4);
    }
    @Test
    public void drawString2ForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.drawString("Text", new BoundingBox2d(Point2d.Origin, new Point2d(4, 3)), 4);

        verify(context, times(1)).drawString("Text", new BoundingBox2d(Point2d.Origin, new Point2d(4, 3)), 4);
    }
    @Test
    public void drawLineForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.drawLine(new Point2d(3, 2), new Point2d(8, 2));

        verify(context, times(1)).drawLine(new Point2d(3, 2), new Point2d(8, 2));
    }
    @Test
    public void drawPolyLineForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        Iterable<Point2d> pts = asList(new Point2d(3, 2), new Point2d(8, 2), new Point2d(1, 5));
        pipe.drawPolyLine(pts);

        verify(context, times(1)).drawPolyLine(pts);
    }
    @Test
    public void drawRectangleForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.drawRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).drawRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void fillRectangleForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.fillRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).fillRectangle(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void drawElipseForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.drawEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).drawEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void fillElipseForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        pipe.fillEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));

        verify(context, times(1)).fillEllipse(new BoundingBox2d(new Point2d(2, 5), new Point2d(7, 9)));
    }
    @Test
    public void drawBezierForwardsToContext(){
        GraphicsContextImp context = spy(new GraphicsContextImp());
        GraphicsStream2dImp pipe = new GraphicsStream2dImp(context);

        BezierCurve2d b= new BezierCurve2d(new Point2d(2, 3), new Vector2d(1, 1), new Point2d(4, 5), new Vector2d(3, 5));

        pipe.drawBezier(b);

        verify(context, times(1)).drawBezier(b);
    }
}
