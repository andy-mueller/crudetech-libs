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
import com.crudetech.graphics2d.xwt.Pen;

/**
 * The display properties for parts of a {@link Widget}
 */
public class WidgetDisplayProperty {
    private final Pen pen;
    private final Brush brush;
    private final Font font;

    public WidgetDisplayProperty(Pen pen, Brush brush, Font font) {
        this.pen = pen;
        this.brush = brush;
        this.font = font;
    }

    public Pen getPen() {
        return pen;
    }

    public Brush getBrush() {
        return brush;
    }

    public Font getFont() {
        return font;
    }
}
