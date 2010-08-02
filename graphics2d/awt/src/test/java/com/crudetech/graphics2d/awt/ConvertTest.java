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

import com.crudetech.geometry.geom2d.Matrix2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.*;
import com.crudetech.graphics2d.xwt.Font;
import org.junit.Test;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ConvertTest {
    @Test
    public void matrixConvertsToAffineTransform() {
        Matrix2d mx = Matrix2d.postMultiply(Matrix2d.createScale(3, 5), Matrix2d.createRotationInRadians(0.22222), Matrix2d.createTranslation(4, 6));

        AffineTransform xform = Convert.convertMatrix(mx);

        Point2d expected = mx.multiply(new Point2d(3, 77));
        Point2D actual = xform.transform(new Point2D.Double(3, 77), new Point2D.Double());

        assertThat(actual.getX(), is(expected.getX()));
        assertThat(actual.getY(), is(expected.getY()));
    }

    @Test
    public void affineTransformConvertsToMatrix() {

        AffineTransform xform = new AffineTransform();
        xform.setTransform(3, 7, 2, 9, 5, 4);

        Matrix2d mx = Convert.convertAffineTransform(xform);

        Point2d expected = mx.multiply(new Point2d(3, 77));
        Point2D actual = xform.transform(new Point2D.Double(3, 77), new Point2D.Double());

        assertThat(actual.getX(), is(expected.getX()));
        assertThat(actual.getY(), is(expected.getY()));
    }

    @Test
    public void convertPenToStroke() {
        BasicStroke expected = new BasicStroke(1.2f, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 2, new float[]{3f, 1f}, 2f);
        Pen p = Convert.convertStroke(expected);

        assertThat(expected.getDashArray(), is(p.getDashArray()));
        assertThat(expected.getDashPhase(), is(p.getDashPhase()));
        assertThat(expected.getEndCap(), is(Convert.convertEndcap(p.getEndCap())));
        assertThat(Convert.convertToEndcap(expected.getEndCap()), is(p.getEndCap()));
        assertThat(expected.getLineJoin(), is(Convert.convertLineJoin(p.getLineJoin())));
        assertThat(expected.getLineWidth(), is(p.getLineWidth()));
        assertThat(expected.getMiterLimit(), is(p.getMiterLimit()));
    }

    @Test
    public void convertStrokeToPen() {
        Pen expected = new Pen(1.2f, Cap.Butt, Join.Round, 2, new float[]{3f, 1f}, 2f);
        BasicStroke stroke = Convert.convertPen(expected);

        assertThat(stroke.getDashArray(), is(expected.getDashArray()));
        assertThat(stroke.getDashPhase(), is(expected.getDashPhase()));
        assertThat(stroke.getEndCap(), is(Convert.convertEndcap(expected.getEndCap())));
        assertThat(Convert.convertToEndcap(stroke.getEndCap()), is(expected.getEndCap()));
        assertThat(stroke.getLineJoin(), is(Convert.convertLineJoin(expected.getLineJoin())));
        assertThat(stroke.getLineWidth(), is(expected.getLineWidth()));
        assertThat(stroke.getMiterLimit(), is(expected.getMiterLimit()));
    }
    @Test
    public void convertColorToSolidBrush(){
        java.awt.Color awtColor = java.awt.Color.BLUE;

        SolidBrush b = Convert.convertToSolidBrush(awtColor);

        assertThat(b.getColor(), is(com.crudetech.graphics2d.xwt.Color.Blue));
    }
    @Test
    public void convertSolidBrushToAwtColor(){
        java.awt.Color awtColor = Convert.convertToAwtColor(new SolidBrush(com.crudetech.graphics2d.xwt.Color.Green));

        assertThat(awtColor, is(java.awt.Color.GREEN));
    }
    @Test
    public void convertAwtBrushToAwtPaint(){
        Brush brush = new AwtBrush(java.awt.Color.BLUE);
        java.awt.Paint awtPaint = Convert.convertToAwtPaint(brush);

        assertThat(awtPaint, is((Paint)java.awt.Color.BLUE));
    }
    @Test
    public void convertAwtPaintToAwtBrush(){
        Brush brush =  Convert.convertToBrush((Paint)java.awt.Color.BLUE);

        assertThat(brush, is((Brush)new AwtBrush(java.awt.Color.BLUE)));
    }
    @Test
    public void awtFontIsConverted(){
        Font font = Convert.convertAwtFontToFont(new java.awt.Font("Arial", java.awt.Font.BOLD, 12));

        assertThat(font, is(new Font("Arial", FontStyle.Bold, 12)));
    }
    @Test
    public void fontIsConvertedToAwtFont(){
        java.awt.Font font = Convert.convertFontToAwtFont(new Font("Arial", FontStyle.Plain, 12));

        assertThat(font, is(new java.awt.Font("Arial", java.awt.Font.PLAIN, 12)));
    }
}
