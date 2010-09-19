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

import com.crudetech.geometry.geom2d.BoundingBox2d;
import com.crudetech.geometry.geom2d.Point2d;

/**
 * A widget that has rectangular properties
 */
public abstract class RectangularWidget extends AbstractWidget{
    private final double width;
    private final double height;

    public RectangularWidget(double width, double height) {
        this.width = width;
        this.height = height;
    }

    public BoundingBox2d getBoundingBox() {
        return new BoundingBox2d(Point2d.Origin, new Point2d(width, height));
    }
}
