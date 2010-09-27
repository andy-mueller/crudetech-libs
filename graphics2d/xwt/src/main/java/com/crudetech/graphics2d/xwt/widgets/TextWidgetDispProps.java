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
import com.crudetech.graphics2d.xwt.Font;

public class TextWidgetDispProps extends AbstractWidgetDisplayProperties {
    private static final String TextProp = "TextWidgetDispProps.TextProp";

    public TextWidgetDispProps(Brush textBrush, Font textFont) {
        putProperty(TextProp, I18n.Text(), new WidgetDisplayProperty(null, textBrush, textFont));
    }

    Brush getTextBrush() {
        return getProperty(TextProp).getBrush();
    }

    Font getFont() {
        return getProperty(TextProp).getFont();
    }
}
