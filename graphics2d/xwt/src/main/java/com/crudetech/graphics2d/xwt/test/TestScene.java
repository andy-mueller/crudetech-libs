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

import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.Color;
import com.crudetech.graphics2d.xwt.GraphicsStream2d;
import com.crudetech.graphics2d.xwt.Pen;
import com.crudetech.graphics2d.xwt.SolidBrush;
import com.crudetech.graphics2d.xwt.widgets.RectangularBorderedWidget;
import com.crudetech.graphics2d.xwt.widgets.RectangularBorderedWidgetDispProps;

/**
 * A very simple test scene that can be used to visually expect the your xwt implementation.
 */
public class TestScene {
    public void render(GraphicsStream2d pipe) {
        RectangularBorderedWidgetDispProps rectProps = new RectangularBorderedWidgetDispProps(new Pen(2.0f), new SolidBrush(Color.Blue));
        RectangularBorderedWidget rect = new RectangularBorderedWidget(200, 100, rectProps);
        rect.getEcs().setLocation(new Point2d(100, 100));

        GraphicsStream2d.RestorePoint rp = pipe.createRestorePoint();
        try {
//            pipe.getCoordinateSystemStack().pushTranslation(100, 100);
//            pipe.getCoordinateSystemStack().pushRotationInRadians(RadianAngles.k30);

            rect.draw(pipe);
        } finally {
            rp.restore();
        }
    }
}
