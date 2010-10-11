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
package com.crudetech.graphics2d.xwt;

import com.crudetech.lang.ArgumentNullException;


public class SolidBrush implements Brush{
    public static Brush Red = new SolidBrush(Color.Red);
    public static Brush Green = new SolidBrush(Color.Green);
    public static Brush Blue = new SolidBrush(Color.Blue);
    public static Brush Black = new SolidBrush(Color.Black);
    public static Brush White= new SolidBrush(Color.White);

    private final Color color;

    public SolidBrush(Color color) {
        if(color == null) throw new ArgumentNullException("color");
        
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SolidBrush that = (SolidBrush) o;

        return color.equals(that.color);

    }

    @Override
    public int hashCode() {
        return color.hashCode();
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "SolidBrush{" +
                "color=" + color +
                '}';
    }

}
