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
import com.crudetech.graphics2d.xwt.CoordinateSystem;
import com.crudetech.graphics2d.xwt.GraphicsStream2d;

/**
 * When implemented, it represents a simple graphical 2d entity. Widgets
 * are typically use as sub items of a more complex custom control or widget. For example
 * one might implement a widget to represent a cell in a table or a node
 * in a tree.
 * <p>
 * Widgets have an entity coordinate system (ECS). All properties of a widget are returned
 * inside this ECS. To obtain their world position, they must be transformed by the ecs coordinate system
 */
public interface Widget {
    CoordinateSystem getEcs();

    WidgetDisplayProperties getDisplayProperties();

    void setDisplayProperties(WidgetDisplayProperties props);

    void draw(GraphicsStream2d stream);

    BoundingBox2d getBoundingBox();
}
