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
package com.crudetech.graphics2d.xwt.test;

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.*;

/**
 * A very simple test scene that can be used to visually expect the your xwt implementation.
 */
public class TestScene {
    public void render(GraphicsStream2d pipe){
        pipe.getCoordinateSystemStack().pushTranslation(100, 100);
        pipe.getCoordinateSystemStack().pushRotationInRadians(RadianAngles.k30);

        pipe.getPenStack().push(new Pen(3, Cap.Square, Join.Miter, 1, null, 0));

        pipe.getBrushStack().push(new SolidBrush(Color.Blue));
        pipe.drawLine(new Point2d(10, 100), new Point2d(200, 100));        
    }
}
