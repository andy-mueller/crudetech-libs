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



public class Font {
    private final String family;
    private final FontStyle style;
    private final int size;

    public Font(String family, FontStyle style, int size) {
        if(family == null || family.equals("")) throw new IllegalArgumentException();
        if(style == null) throw new IllegalArgumentException();
        if(size <= 0) throw new IllegalArgumentException();

          
        this.family = family;
        this.style = style;
        this.size = size;
    }

    public String getFamily() {
        return family;
    }

    public FontStyle getStyle() {
        return style;
    }

    public int getSize() {
        return size;
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Font{" +
                "family='" + family + '\'' +
                ", style=" + style +
                ", size=" + size +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Font font = (Font) o;

        if (size != font.size) return false;
        if (!family.equals(font.family)) return false;
        if (style != font.style) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = family.hashCode();
        result = 31 * result + style.hashCode();
        result = 31 * result + size;
        return result;
    }
}
