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
import com.crudetech.graphics2d.xwt.GraphicsStream2d;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;


public class CompoundWidget extends AbstractWidget{
    private Collection<Widget> components = new ArrayList<Widget>();

    public CompoundWidget(Iterable<Widget> widgets) {
        for(Widget w : widgets){
            components.add(w);
        }
    }

    public Iterable<Widget> getComponents() {
        return components;
    }

    @Override
    protected void drawEcs(GraphicsStream2d stream) {
        throw new UnsupportedOperationException("drawEcs is not supported yet!");
    }

    @Override
    public BoundingBox2d getBoundingBox() {
//        Iterator<BoundingBox2d> i = components.iterator();
        return null;
    }
}
