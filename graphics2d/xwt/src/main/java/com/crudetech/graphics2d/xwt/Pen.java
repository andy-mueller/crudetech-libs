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

public final class Pen {
    private final float[] dashPattern;
    private final float dashPhase;
    private final Cap endCap;
    private final Join lineJoin;
    private final float lineWidth;
    private final float miterLimit;

    public Pen(float lineWidth, Cap endCap, Join lineJoin, float miterLimit, float[] dashPattern, float dashPhase){

        this.lineWidth = lineWidth;
        this.endCap = endCap;
        this.lineJoin = lineJoin;
        this.miterLimit = miterLimit;
        this.dashPattern = dashPattern;
        this.dashPhase = dashPhase;
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
}
