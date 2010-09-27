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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class RectangularBorderedWidgetDispPropsTest {
    @Test
    public void borderPropDisplayName(){
        RectangularBorderedWidgetDispProps p = new RectangularBorderedWidgetDispProps(null, null);

        WidgetDisplayProperties.Info i = p.iterator().next();

        assertThat(i.getDisplayName(), is("Border"));
    }
}
