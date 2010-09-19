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

public class GraphicsStream2dStub extends GraphicsStream2d {
    GraphicsStream2dStub(GraphicsContext pipe) {
        super(pipe);
    }
    public GraphicsStream2dStub(){
        this(new GraphicsContextStub());     
    }
}