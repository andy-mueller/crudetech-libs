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

import com.crudetech.graphics2d.xwt.*;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class WidgetDisplayPropertyTest {
    @Test
    public void ctorSetsAllProperties(){
        WidgetDisplayProperty prop =
                new WidgetDisplayProperty(new Pen(3), new SolidBrush(Color.Red), new Font("Arial", FontStyle.Plain, 3));

        assertThat(prop.getPen(), is(new Pen(3)));
        assertThat(new SolidBrush(Color.Red), is(prop.getBrush()));
        assertThat(new Font("Arial", FontStyle.Plain, 3), is(prop.getFont()));
    }
}
