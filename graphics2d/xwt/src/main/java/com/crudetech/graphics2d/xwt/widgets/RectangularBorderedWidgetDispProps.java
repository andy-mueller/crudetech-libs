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
package com.crudetech.graphics2d.xwt.widgets;

import com.crudetech.graphics2d.xwt.Brush;
import com.crudetech.graphics2d.xwt.Color;
import com.crudetech.graphics2d.xwt.Pen;
import com.crudetech.graphics2d.xwt.SolidBrush;


public class RectangularBorderedWidgetDispProps extends AbstractWidgetDisplayProperties {
    private static final String BorderProp = "RectangularBorderedWidgetDispProps.BorderProp";
    public static RectangularBorderedWidgetDispProps Default =
            new RectangularBorderedWidgetDispProps(new Pen(1.0f), new SolidBrush(Color.Black));

    public RectangularBorderedWidgetDispProps(Pen borderPen, Brush borderBrush) {
        putProperty(BorderProp, I18n.Border(), new WidgetDisplayProperty(borderPen, borderBrush, null));
    }

    Pen getBorderPen() {
        return getProperty(BorderProp).getPen();
    }

    Brush getBorderBrush() {
        return getProperty(BorderProp).getBrush();
    }
}
