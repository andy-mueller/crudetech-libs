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

import com.crudetech.geometry.geom.RadianAngles;
import com.crudetech.geometry.geom2d.BoundingBox2d;
import com.crudetech.geometry.geom2d.Point2d;
import com.crudetech.graphics2d.xwt.CoordinateSystem;
import com.crudetech.graphics2d.xwt.GraphicsStream2d;


public abstract class AbstractWidget implements Widget{
    private final CoordinateSystem ecs = new CoordinateSystem(Point2d.Origin, RadianAngles.k0);
    private WidgetDisplayProperties dispProps = new WidgetDisplayProperties();
    @Override
    public CoordinateSystem getEcs() {
        return ecs;
    }

    @Override
    public WidgetDisplayProperties getDisplayProperties() {
        return dispProps;
    }

    @Override
    public void setDisplayProperties(WidgetDisplayProperties props) {
        dispProps = props;
    }

    @Override
    public void draw(GraphicsStream2d stream) {
        GraphicsStream2d.RestorePoint rp = stream.createRestorePoint();
        try{
            stream.getCoordinateSystemStack().pushCoordinateSystem(getEcs());
            drawEcs(stream);
        }finally{
            rp.restore();
        }
    }

    protected abstract void drawEcs(GraphicsStream2d stream);

    @Override
    public abstract BoundingBox2d getBoundingBox();
}
