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

public interface GraphicsContext {
    Matrix2d getTransform();
    void setTransform(Matrix2d xform);

    Pen getPen();
    void setPen(Pen pen);

    Brush getBrush();
    void setBrush(Brush brush);

    Font getFont();
    void setFont(Font font);

    void drawString(String string, double x, double y);
    void drawString(String string, BoundingBox2d bound, double padding);
    void drawLine(Point2d start, Point2d end);

    void drawCurve(BoundingBox2d rect);

    void drawRectangle(BoundingBox2d rect);
    void fillRectangle(BoundingBox2d rect);

    void drawEllipse(BoundingBox2d bounds);
    void fillEllipse(BoundingBox2d bounds);

    void drawBezier(BezierCurve2d bezier);

    void drawPolyLine(Iterable<Point2d> pts);

    void drawPolygon(Polygon2d polygon);
    void fillPolygon(Polygon2d polygon);
}
