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
package com.crudetech.graphics2d.xwt.test;

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.geometry.geom2d.Polygon2d;
import com.crudetech.graphics2d.xwt.*;
import com.crudetech.graphics2d.xwt.widgets.*;

import static java.util.Arrays.asList;

/**
 * A very simple test scene that can be used to visually expect the your xwt implementation.
 */
public class TestScene {
    private final double width, height;

    public TestScene(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public void render(GraphicsStream2d pipe) {
        drawBorders(pipe);

        drawRectangularWidget(pipe);
        drawTextWidget(pipe);
        drawBorderedTextWidget(pipe);

        drawPolygon(pipe);
    }

    private void drawPolygon(GraphicsStream2d pipe) {
        Iterable<Point2d> cornerPts = asList(
                new Point2d(0, 0), new Point2d(20, 0), new Point2d(25, 15), new Point2d(10, 30), new Point2d(-5, 15)
        );
        Polygon2d poly = new Polygon2d(cornerPts);

        CoordinateSystem ecs = new CoordinateSystem(new Point2d(300, 10), RadianAngles.k0);

        try (GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint()) {
            pipe.getCoordinateSystemStack().pushCoordinateSystem(ecs);

            pipe.getPenStack().push(new Pen(2f));
            pipe.getBrushStack().push(new SolidBrush(Color.Red));
            pipe.fillPolygon(poly);
            pipe.getBrushStack().pop();

            pipe.drawPolygon(poly);
        }
    }

    private void drawBorderedTextWidget(GraphicsStream2d pipe) {
        TextWidgetDispProps textProps = new TextWidgetDispProps(new SolidBrush(Color.Red), new Font("Arial", FontStyle.Italic, 54));
        RectangularBorderedWidgetDispProps borderProps = new RectangularBorderedWidgetDispProps(new Pen(2f), new SolidBrush(Color.Blue));

        RectangularBorderedTextWidgetDispProps props = new RectangularBorderedTextWidgetDispProps(borderProps, textProps);

        RectangularBorderedTextWidget txt = new RectangularBorderedTextWidget("Hallo Welt!", 60, 30, props);
        txt.getEcs().setLocation(new Point2d(200, 10));

        try (GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint()) {
            txt.draw(pipe);
        }
    }

    private void drawTextWidget(GraphicsStream2d pipe) {
        TextWidgetDispProps textProps = new TextWidgetDispProps(new SolidBrush(Color.Green), new Font("Arial", FontStyle.Italic, 24));
        TextWidget txt = new TextWidget("Hallo Welt!", 60, 30, textProps);
        txt.getEcs().setLocation(new Point2d(100, 10));

        try (GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint()) {
            txt.draw(pipe);
        }
    }

    private void drawRectangularWidget(GraphicsStream2d pipe) {
        RectangularBorderedWidgetDispProps rectProps = new RectangularBorderedWidgetDispProps(new Pen(1.0f), new SolidBrush(Color.Red));
        RectangularBorderedWidget tableBoundary = new RectangularBorderedWidget(60, 30, rectProps);
        tableBoundary.getEcs().setLocation(new Point2d(10, 10));

        try (GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint()) {
            tableBoundary.draw(pipe);
        }
    }

    private void drawBorders(GraphicsStream2d pipe) {
        RectangularBorderedWidgetDispProps tableProps = new RectangularBorderedWidgetDispProps(new Pen(2.0f), new SolidBrush(Color.Blue));
        RectangularBorderedWidget tableBoundary = new RectangularBorderedWidget(width, height, tableProps);

        try (GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint()) {
            tableBoundary.draw(pipe);
        }
    }
}
