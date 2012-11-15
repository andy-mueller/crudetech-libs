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
package com.crudetech.graphics2d.xwt.widgets;

import com.crudetech.graphics2d.xwt.GraphicsStream2d;


public class TextWidget extends RectangularWidget {
    private final String text;

    public TextWidget(String text, double width, double height, TextWidgetDispProps dispProps) {
        super(width, height);
        this.text = text;
        setDisplayProperties(dispProps);
    }

    @Override
    public TextWidgetDispProps getDisplayProperties() {
        return (TextWidgetDispProps) super.getDisplayProperties();
    }

    @Override
    protected void drawEcs(GraphicsStream2d stream) {
        GraphicsStream2d.RestorePoint rp = stream.createRestorePoint();
        try {
            stream.getBrushStack().push(getDisplayProperties().getTextBrush());
            stream.getFontStack().push(getDisplayProperties().getFont());

            stream.drawString(text, getBoundingBox(), 1);
        } finally {
            rp.restore();
        }
    }

    public String getText() {
        return text;
    }
}
