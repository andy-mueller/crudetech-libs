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
package com.crudetech.graphics2d.xwt.test;

import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.*;
import com.crudetech.graphics2d.xwt.widgets.*;

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
    }

    private void drawBorderedTextWidget(GraphicsStream2d pipe) {
        TextWidgetDispProps textProps = new TextWidgetDispProps(new SolidBrush(Color.Green), new Font("Arial", FontStyle.Italic, 24));
        RectangularBorderedWidgetDispProps borderProps = new RectangularBorderedWidgetDispProps(new Pen(2f), new SolidBrush(Color.Blue));

        RectangularBorderedTextWidgetDispProps props = new RectangularBorderedTextWidgetDispProps(borderProps, textProps);

        RectangularBorderedTextWidget txt = new RectangularBorderedTextWidget("Hallo Welt!", 60, 30, props);
        txt.getEcs().setLocation(new Point2d(200, 10));

        GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint();
        try {
            txt.draw(pipe);
        } finally {
            rp.restore();
        }
    }

    private void drawTextWidget(GraphicsStream2d pipe) {
        TextWidgetDispProps textProps = new TextWidgetDispProps(new SolidBrush(Color.Green), new Font("Arial", FontStyle.Italic, 24));
        TextWidget txt = new TextWidget("Hallo Welt!", 60, 30, textProps);
        txt.getEcs().setLocation(new Point2d(100, 10));

        GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint();
        try {
            txt.draw(pipe);
        } finally {
            rp.restore();
        }
    }

    private void drawRectangularWidget(GraphicsStream2d pipe) {
        RectangularBorderedWidgetDispProps rectProps = new RectangularBorderedWidgetDispProps(new Pen(1.0f), new SolidBrush(Color.Red));
        RectangularBorderedWidget tableBoundary = new RectangularBorderedWidget(60, 30, rectProps);
        tableBoundary.getEcs().setLocation(new Point2d(10, 10));

        GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint();
        try {
            tableBoundary.draw(pipe);
        } finally {
            rp.restore();
        }
    }

    private void drawBorders(GraphicsStream2d pipe) {
        RectangularBorderedWidgetDispProps tableProps = new RectangularBorderedWidgetDispProps(new Pen(2.0f), new SolidBrush(Color.Blue));
        RectangularBorderedWidget tableBoundary = new RectangularBorderedWidget(width, height, tableProps);

        GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint();
        try {
            tableBoundary.draw(pipe);
        } finally {
            rp.restore();
        }
    }
}
