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


/**
 * Specifies the joining of two edges in a path.
 */
public enum Join {
    /**
     * Joins path segments by extending their outside edges until they meet.
     */
    Miter,
    /**
     * Joins path segments by rounding off the corner at a radius of half the line width.
     */
    Round,
    /**
     * Joins path segments by connecting the outer corners of their wide outlines with a straight segment. 
     */
    Bevel,
}
