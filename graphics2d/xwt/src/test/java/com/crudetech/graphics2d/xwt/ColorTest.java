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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class ColorTest {
    @Test
    public void rgbaCtor(){
        Color c = new Color(100, 125, 150, 160);

        assertThat(c.getRed(), is(100));
        assertThat(c.getGreen(), is(125));
        assertThat(c.getBlue(), is(150));
        assertThat(c.getAlpha(), is(160));
    }
}
