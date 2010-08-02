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



public enum Cap {
    /**
     * Ends unclosed sub paths and dash segments with no added decoration.
     */
    Butt,
    /**
     * Ends unclosed sub paths and dash segments with a round decoration that has
     * a radius equal to half of the width of the pen.
     */
    Round,
    /**
     * Ends unclosed sub paths and dash segments with a square projection that
     * extends beyond the end of the segment to a distance equal to half of the line width.
      */
    Square,
}
