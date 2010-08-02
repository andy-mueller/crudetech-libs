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
import com.crudetech.graphics2d.xwt.*;

import java.awt.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.geom.AffineTransform;

import static com.crudetech.geometry.geom.Matrix.column;
import static com.crudetech.geometry.geom.Matrix.row;


class Convert {
    private Convert() {
    }

    static AffineTransform convertMatrix(Matrix2d mx) {
        return new AffineTransform(
                mx.get(row(0), column(0)), mx.get(row(1), column(0)),
                mx.get(row(0), column(1)), mx.get(row(1), column(1)),
                mx.get(row(0), column(2)), mx.get(row(1), column(2))
        );
    }

    public static Matrix2d convertAffineTransform(AffineTransform xform) {
        double[] m = new double[6];
        xform.getMatrix(m); //{ m00 m10 m01 m11 m02 m12 }

        return new Matrix2d(
                m[0], m[2], m[4],
                m[1], m[3], m[5],
                0, 0, 1
        );
    }

    static Pen convertStroke(BasicStroke stroke) {
        return new Pen(stroke.getLineWidth(), convertToEndcap(stroke.getEndCap()),
                convertToJoin(stroke.getLineJoin()), stroke.getMiterLimit(),
                stroke.getDashArray(), stroke.getDashPhase());

    }

    static Join convertToJoin(int join) {
        switch (join) {
            case BasicStroke.JOIN_BEVEL:
                return Join.Bevel;
            case BasicStroke.JOIN_MITER:
                return Join.Miter;
            case BasicStroke.JOIN_ROUND:
                return Join.Round;
            default:
                throw new IllegalArgumentException();
        }
    }

    static Cap convertToEndcap(int endCap) {
        switch (endCap) {
            case BasicStroke.CAP_BUTT:
                return Cap.Butt;
            case BasicStroke.CAP_ROUND:
                return Cap.Round;
            case BasicStroke.CAP_SQUARE:
                return Cap.Square;
            default:
                throw new IllegalArgumentException();
        }
    }

    static int convertEndcap(Cap endCap) {
        switch (endCap) {
            case Butt:
                return 0;
            case Round:
                return 1;
            case Square:
                return 2;
            default:
                throw new IllegalArgumentException("Unknown cap type");
        }
    }

    static int convertLineJoin(Join join) {
        switch (join) {
            case Miter:
                return 0;
            case Round:
                return 1;
            case Bevel:
                return 2;
            default:
                throw new IllegalArgumentException("Unknown join type!");
        }

    }

    static BasicStroke convertPen(Pen p) {
        return new BasicStroke(p.getLineWidth(), convertEndcap(p.getEndCap()), convertLineJoin(p.getLineJoin()), p.getMiterLimit(), p.getDashArray(), p.getDashPhase());
    }
    public static com.crudetech.graphics2d.xwt.Color convertToXwtColor(Color awtColor){
        return new com.crudetech.graphics2d.xwt.Color(awtColor.getRed(), awtColor.getGreen(), awtColor.getBlue(), awtColor.getAlpha());
    }
    public static Color convertToAwtColor(com.crudetech.graphics2d.xwt.Color xwtColor){
        return new Color(xwtColor.getRed(), xwtColor.getGreen(), xwtColor.getBlue(), xwtColor.getAlpha());
    }

    public static SolidBrush convertToSolidBrush(Color awtColor) {
        return new SolidBrush(convertToXwtColor(awtColor));
    }
    public static Brush convertToBrush(Paint awtPaint) {
        return new AwtBrush(awtPaint);
    }

    public static Color convertToAwtColor(SolidBrush solidBrush) {
        return convertToAwtColor(solidBrush.getColor());
    }

    public static Paint convertToAwtPaint(Brush brush) {
        if(brush instanceof SolidBrush){
            return convertToAwtColor((SolidBrush) brush);
        }else if (brush instanceof AwtBrush){
            return ((AwtBrush)brush).getPaint();
        }

        throw new IllegalArgumentException();
    }

    public static com.crudetech.graphics2d.xwt.Font convertAwtFontToFont(Font font) {
        return new com.crudetech.graphics2d.xwt.Font(font.getFamily(), convertFontStyle(font.getStyle()), font.getSize());
    }

    private static FontStyle convertFontStyle(int style) {
        switch (style){
            case Font.BOLD: return FontStyle.Bold;
            case Font.ITALIC: return FontStyle.Italic;
            case Font.PLAIN: return FontStyle.Plain;
            default: throw new IllegalArgumentException();
        }
    }

    public static Font convertFontToAwtFont(com.crudetech.graphics2d.xwt.Font font) {
        return new Font(font.getFamily(), convertToAwtFontStyle(font.getStyle()), font.getSize());
    }

    private static int convertToAwtFontStyle(FontStyle style) {
        switch (style){
            case Plain: return Font.PLAIN;
            case Bold: return Font.BOLD;
            case Italic: return Font.ITALIC;
            default: throw new IllegalArgumentException();
        }
    }
}
