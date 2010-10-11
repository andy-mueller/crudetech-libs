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
package com.crudetech.graphics2d.awt;

import com.crudetech.geometry.geom2d.*;
import com.crudetech.graphics2d.xwt.Brush;
import com.crudetech.graphics2d.xwt.Font;
import com.crudetech.graphics2d.xwt.GraphicsContext;
import com.crudetech.graphics2d.xwt.Pen;

import java.awt.*;
import java.awt.geom.Rectangle2D;

import static com.crudetech.graphics2d.awt.Convert.*;


public class AwtGraphicsContext implements GraphicsContext {
    private final Graphics2D pipe;

    public AwtGraphicsContext(Graphics2D pipe) {
        this.pipe = pipe;
    }

    @Override
    public Matrix2d getTransform() {
        return convertAffineTransform(pipe.getTransform());
    }

    @Override
    public void setTransform(Matrix2d xform) {
        pipe.setTransform(convertMatrix(xform));
    }

    @Override
    public Pen getPen() {
        // TODO: this sucks
        return Convert.convertStroke((BasicStroke) pipe.getStroke());
    }

    @Override
    public void setPen(Pen pen) {
        pipe.setStroke(convertPen(pen));
    }

    @Override
    public Brush getBrush() {
        return Convert.convertToBrush(pipe.getPaint());
    }

    @Override
    public void setBrush(Brush brush) {
        pipe.setPaint(Convert.convertToAwtPaint(brush));
    }

    @Override
    public Font getFont() {
        return Convert.convertAwtFontToFont(pipe.getFont());
    }

    @Override
    public void setFont(Font font) {
        pipe.setFont(Convert.convertFontToAwtFont(font));
    }

    @Override
    public void drawString(String string, double x, double y) {
        pipe.drawString(string, (float) x, (float) y);
    }

    @Override
    public void drawString(String string, BoundingBox2d bounds, double padding) {
        FontMetrics metrics = pipe.getFontMetrics(pipe.getFont());

        final double stringWidth = metrics.stringWidth(string);
        final double stringHeight = metrics.getHeight();

        // calculate scale, so that bb fits into bounds - padding
        final double sx = bounds.getWidth() / (stringWidth + padding);
        final double sy = bounds.getHeight() / (stringHeight + padding);

        final double scale = Math.min(sx, sy);

        java.awt.Font oldFont = pipe.getFont();
        java.awt.Font scaledFont = oldFont.deriveFont((float)(oldFont.getSize2D()*scale));

        //position b inside bounds
        final double x = bounds.getLocation().getX() + (bounds.getWidth() - stringWidth * scale)/2;
        final double y = bounds.getLocation().getY() + bounds.getHeight() - (bounds.getHeight() - scaledFont.getSize2D())/2;

        pipe.setFont(scaledFont);
        try {
            pipe.drawString(string, (float) x, (float) y);
        } finally {
            pipe.setFont(oldFont);
        }
    }

    @Override
    public void drawLine(Point2d start, Point2d end) {
        pipe.drawLine((int) start.getX(), (int) start.getY(), (int) end.getX(), (int) end.getY());
    }

    @Override
    public void drawCurve(BoundingBox2d rect) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawRectangle(BoundingBox2d rect) {
        drawShape(new Rectangle2D.Double(rect.getLowerLeft().getX(), rect.getLowerLeft().getY(), rect.getWidth(), rect.getHeight()));
    }

    private void drawShape(Shape s) {
        pipe.draw(s);
    }
    private void fillShape(Shape s) {
        pipe.fill(s);
    }

    @Override
    public void fillRectangle(BoundingBox2d rect) {
        fillShape(new Rectangle2D.Double(rect.getLowerLeft().getX(), rect.getLowerLeft().getY(), rect.getWidth(), rect.getHeight()));
    }

    @Override
    public void drawEllipse(BoundingBox2d bounds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void fillEllipse(BoundingBox2d bounds) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawBezier(BezierCurve2d bezier) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawPolyLine(Iterable<Point2d> pts) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void drawPolygon(Polygon2d polygon) {
        pipe.draw(convertPolygon(polygon));
    }
    private static Shape convertPolygon(Polygon2d polygon){
        int[] x = new int[polygon.getNumberOfCorners()];
        int[] y = new int[polygon.getNumberOfCorners()];

        int pos = 0;
        for(Point2d pt : polygon.getCornerPoints()){
            x[pos] = (int) pt.getX();
            y[pos] = (int) pt.getY();
            ++pos;
        }

        return new Polygon(x, y, x.length);
    }

    @Override
    public void fillPolygon(Polygon2d polygon) {
        pipe.fill(convertPolygon(polygon));
    }
}
