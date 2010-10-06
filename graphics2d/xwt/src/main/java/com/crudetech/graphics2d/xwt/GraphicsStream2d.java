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

import com.crudetech.collections.LightweightStack;
import com.crudetech.collections.ListStack;
import com.crudetech.event.EventListener;
import com.crudetech.event.EventObject;
import com.crudetech.geometry.geom2d.*;

public class GraphicsStream2d {
    private final Matrix2dStack coosStack = new Matrix2dStack();
    private final NotifyingStackImp<Pen> penStack = new NotifyingStackImp<Pen>(new ListStack<Pen>());
    private final NotifyingStackImp<Brush> brushStack = new NotifyingStackImp<Brush>(new ListStack<Brush>());
    private final NotifyingStackImp<Font> fontStack = new NotifyingStackImp<Font>(new ListStack<Font>());

    private final GraphicsContext pipe;


    public GraphicsStream2d(GraphicsContext graphicsContext) {
        this.pipe = graphicsContext;

        coosStack.pushXForm(graphicsContext.getTransform());
        EventListener<EventObject<NotifyingStack<Matrix2d>>> xFormChangedListener =
                new EventListener<EventObject<NotifyingStack<Matrix2d>>>() {
                    @Override
                    public void onEvent(EventObject<NotifyingStack<Matrix2d>> e) {
                        pipe.setTransform(coosStack.peek());
                    }
                };
        coosStack.getPushEvent().addListener(xFormChangedListener);
        coosStack.getPopEvent().addListener(xFormChangedListener);

        penStack.push(pipe.getPen());
        final EventListener<EventObject<NotifyingStack<Pen>>> penChangedListener = new EventListener<EventObject<NotifyingStack<Pen>>>() {
            @Override
            public void onEvent(EventObject<NotifyingStack<Pen>> e) {
                pipe.setPen(penStack.peek());
            }
        };
        penStack.getPushEvent().addListener(penChangedListener);
        penStack.getPopEvent().addListener(penChangedListener);

        brushStack.push(pipe.getBrush());
        final EventListener<EventObject<NotifyingStack<Brush>>> brushChangedListener = new EventListener<EventObject<NotifyingStack<Brush>>>() {
            @Override
            public void onEvent(EventObject<NotifyingStack<Brush>> e) {
                pipe.setBrush(brushStack.peek());
            }
        };
        brushStack.getPushEvent().addListener(brushChangedListener);
        brushStack.getPopEvent().addListener(brushChangedListener);

        fontStack.push(pipe.getFont());
        final EventListener<EventObject<NotifyingStack<Font>>> fontChangedListener = new EventListener<EventObject<NotifyingStack<Font>>>() {
            @Override
            public void onEvent(EventObject<NotifyingStack<Font>> e) {
                pipe.setFont(fontStack.peek());
            }
        };
        fontStack.getPushEvent().addListener(fontChangedListener);
        fontStack.getPopEvent().addListener(fontChangedListener);
    }

    public CoordinateSystemStack getCoordinateSystemStack() {
        return coosStack;
    }

    public LightweightStack<Pen> getPenStack() {
        return penStack;
    }

    public LightweightStack<Brush> getBrushStack() {
        return brushStack;
    }

    public LightweightStack<Font> getFontStack() {
        return fontStack;
    }

    public void drawString(String string, double x, double y) {
        pipe.drawString(string, x, y);
    }

    public void drawString(String string, BoundingBox2d bound, double padding) {
        pipe.drawString(string, bound, padding);
    }

    public void drawLine(Point2d start, Point2d end) {
        pipe.drawLine(start, end);
    }


    public void drawRectangle(BoundingBox2d rect) {
        pipe.drawRectangle(rect);
    }

    public void fillRectangle(BoundingBox2d rect) {
        pipe.fillRectangle(rect);
    }

    public void drawEllipse(BoundingBox2d bounds) {
        pipe.drawEllipse(bounds);
    }

    public void fillEllipse(BoundingBox2d bounds) {
        pipe.fillEllipse(bounds);
    }

    public void drawBezier(BezierCurve2d bezier) {
        pipe.drawBezier(bezier);
    }

    public void drawPolyLine(Iterable<Point2d> pts) {
        pipe.drawPolyLine(pts);
    }

    public void drawCurve(Curve2d crv) {
        throw new UnsupportedOperationException("This operation is not supported yet!");
    }

    public RestorePoint createRestorePoint() {
        return new RestorePoint();
    }

    public void drawPolygon(Polygon2d polygon2d) {
        pipe.drawPolygon(polygon2d);
    }

    public void fillPolygon(Polygon2d polygon2d) {
        pipe.fillPolygon(polygon2d);
    }

    public class RestorePoint {
        private final Matrix2d[] xforms = coosStack.toArray();
        private final Pen[] pens = penStack.toArray(Pen.class);
        private final Brush[] brushes= brushStack.toArray(Brush.class);
        private final Font[] fonts = fontStack.toArray(Font.class);

        public void restore() {
            coosStack.clearWithoutEvents();
            coosStack.addWithoutEvents(xforms);
            pipe.setTransform(coosStack.peek());

            penStack.clearWithoutEvents();
            penStack.addWithoutEvents(pens);
            pipe.setPen(penStack.peek());

            brushStack.clearWithoutEvents();
            brushStack.addWithoutEvents(brushes);
            pipe.setBrush(brushStack.peek());

            fontStack.clearWithoutEvents();
            fontStack.addWithoutEvents(fonts);
            pipe.setFont(fontStack.peek());
        }
    }

}

