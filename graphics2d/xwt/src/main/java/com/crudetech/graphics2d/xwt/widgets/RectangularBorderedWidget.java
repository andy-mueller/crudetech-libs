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


public class RectangularBorderedWidget extends RectangularWidget {
    public RectangularBorderedWidget(double width, double height, RectangularBorderedWidgetDispProps dispProps) {
        super(width, height);
        setDisplayProperties(dispProps);
    }

    @Override
    public RectangularBorderedWidgetDispProps getDisplayProperties() {
        return (RectangularBorderedWidgetDispProps) super.getDisplayProperties();
    }

    @Override
    protected void drawEcs(GraphicsStream2d stream) {
        try (GraphicsStream2d.RestorePoint rp = stream.createRestorePoint()) {
            stream.getPenStack().push(getDisplayProperties().getBorderPen());
            stream.getBrushStack().push(getDisplayProperties().getBorderBrush());
            stream.drawRectangle(getBoundingBox());
        }
    }
}
