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
package com.crudetech.geometry.geom2d;

public class BezierCurve2d implements Curve2d{
    public BezierCurve2d(Point2d start, Vector2d startDir, Point2d end, Vector2d endDir) {
    }

    @Override
    public BezierCurve2d transformBy(Matrix2d xform) {
        throw new UnsupportedOperationException("transformBy is not supported yet!");
    }
}
