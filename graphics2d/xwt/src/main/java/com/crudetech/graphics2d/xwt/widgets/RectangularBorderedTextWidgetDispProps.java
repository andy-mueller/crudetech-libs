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

public class RectangularBorderedTextWidgetDispProps extends AbstractCompoundWidgetDisplayProperties{
    public RectangularBorderedTextWidgetDispProps(RectangularBorderedWidgetDispProps borderProps, TextWidgetDispProps textProps) {
        super(borderProps, textProps);
    }

    public TextWidgetDispProps getTextProperties() {
        return (TextWidgetDispProps) getPropertiesAt(1);
    }

    public RectangularBorderedWidgetDispProps getBorderProperties() {
        return (RectangularBorderedWidgetDispProps) getPropertiesAt(0);
    }
}
