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

import java.util.Arrays;

import static com.crudetech.matcher.OrderMatcher.greaterThan;
import static com.crudetech.matcher.Verify.verifyThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;

public final class Pen {
    private final float[] dashPattern;
    private final float dashPhase;
    private final Cap endCap;
    private final Join lineJoin;
    private final float lineWidth;
    private final float miterLimit;

    public Pen(float lineWidth, Cap endCap, Join lineJoin, float miterLimit, float[] dashPattern, float dashPhase) {
        verifyThat(endCap, is(notNullValue()));
        verifyThat(lineJoin, is(notNullValue()));
        verifyThat(miterLimit, is(greaterThan(0.0f)));

        this.lineWidth = lineWidth;
        this.endCap = endCap;
        this.lineJoin = lineJoin;
        this.miterLimit = miterLimit;
        this.dashPattern = dashPattern;
        this.dashPhase = dashPhase;
    }

    public Pen(float lineWidth) {
        this(lineWidth, Cap.Square, Join.Miter, 10.0f, null, 0.0f);
    }

    public float[] getDashArray() {
        return dashPattern;
    }


    public float getDashPhase() {
        return dashPhase;
    }

    public Cap getEndCap() {
        return endCap;
    }

    public Join getLineJoin() {
        return lineJoin;
    }

    public float getLineWidth() {
        return lineWidth;
    }


    public float getMiterLimit() {
        return miterLimit;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Pen pen = (Pen) o;

        return Float.compare(pen.dashPhase, dashPhase) == 0
                && Float.compare(pen.lineWidth, lineWidth) == 0
                && Float.compare(pen.miterLimit, miterLimit) == 0
                && Arrays.equals(dashPattern, pen.dashPattern)
                && endCap == pen.endCap && lineJoin == pen.lineJoin;

    }

    @Override
    public int hashCode() {
        int result = dashPattern != null ? Arrays.hashCode(dashPattern) : 0;
        result = 31 * result + (dashPhase != +0.0f ? Float.floatToIntBits(dashPhase) : 0);
        result = 31 * result + endCap.hashCode();
        result = 31 * result + lineJoin.hashCode();
        result = 31 * result + (lineWidth != +0.0f ? Float.floatToIntBits(lineWidth) : 0);
        result = 31 * result + (miterLimit != +0.0f ? Float.floatToIntBits(miterLimit) : 0);
        return result;
    }

    @SuppressWarnings({"StringConcatenation", "HardCodedStringLiteral", "MagicCharacter"})
    @Override
    public String toString() {
        return "Pen{" +
                "dashPattern=" + Arrays.toString(dashPattern) +
                ", dashPhase=" + dashPhase +
                ", endCap=" + endCap +
                ", lineJoin=" + lineJoin +
                ", lineWidth=" + lineWidth +
                ", miterLimit=" + miterLimit +
                '}';
    }
}
