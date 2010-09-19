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
import com.crudetech.graphics2d.xwt.GraphicsStream2d;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

public class RectangularWidgetTest {
    @Test
    public void ctorSetRectProps(){
        RectangularWidget w = new RectangularWidget(4.0, 3.1){
            @Override
            protected void drawEcs(GraphicsStream2d stream) {
                throw new UnsupportedOperationException("drawEcs is not supported yet!");
            }
        };
        w.getEcs().setLocation(new Point2d(3, 2));
        BoundingBox2d shape = w.getBoundingBox();

        assertThat(shape, is(new BoundingBox2d(new Point2d(0, 0), new Point2d(4.0, 3.1))));
        assertThat(w.getEcs().fromCoordinateSystemToWorld(shape), is(new BoundingBox2d(new Point2d(3, 2), new Point2d(7.0, 5.1))));
    }
}
