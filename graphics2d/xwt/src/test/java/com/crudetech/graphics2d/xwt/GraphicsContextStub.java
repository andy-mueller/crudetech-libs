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

import com.crudetech.geometry.geom2d.BezierCurve2d;
import com.crudetech.geometry.geom2d.BoundingBox2d;
import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;

class GraphicsContextStub implements GraphicsContext {
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
