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

import com.crudetech.graphics2d.xwt.Brush;
import com.crudetech.lang.ArgumentNullException;

import java.awt.*;



public class AwtBrush implements Brush {
    private final Paint paint;

    public AwtBrush(Paint paint) {
        if(paint == null) throw new ArgumentNullException("paint");
        this.paint = paint;
    }
    public Paint getPaint() {
        return paint;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AwtBrush awtBrush = (AwtBrush) o;

        if (!paint.equals(awtBrush.paint)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return paint.hashCode();
    }
}
