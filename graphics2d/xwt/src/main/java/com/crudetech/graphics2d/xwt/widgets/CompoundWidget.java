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

import com.crudetech.collections.Iterables;
import com.crudetech.functional.BinaryFunction;
import com.crudetech.functional.UnaryFunction;
import com.crudetech.geometry.geom2d.BoundingBox2d;
import com.crudetech.graphics2d.xwt.GraphicsStream2d;

import java.util.ArrayList;
import java.util.Collection;

import static com.crudetech.collections.Iterables.accumulate;


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
        for(Widget sub : getComponents()){
            GraphicsStream2d.RestorePoint rp = stream.createRestorePoint();
            try{
                stream.getCoordinateSystemStack().pushCoordinateSystem(sub.getEcs());
                sub.draw(stream);
            }
            finally{
                rp.restore();
            }
        }
    }

    @Override
    public BoundingBox2d getBoundingBox() {
        UnaryFunction<Widget, BoundingBox2d> getBoundingBox =   new UnaryFunction<Widget, BoundingBox2d>() {
            @Override
            public BoundingBox2d execute(Widget widget) {
                return widget.getEcs().fromCoordinateSystemToWorld(widget.getBoundingBox());
            }
        };
        Iterable<BoundingBox2d> boxes = Iterables.transform(getComponents(), getBoundingBox);

        BinaryFunction<BoundingBox2d, BoundingBox2d, BoundingBox2d> add = new BinaryFunction<BoundingBox2d, BoundingBox2d, BoundingBox2d>() {
            @Override
            public BoundingBox2d execute(BoundingBox2d lhs, BoundingBox2d rhs) {
                return lhs.add(rhs);
            }
        };
        return accumulate(boxes, add);
    }
}
