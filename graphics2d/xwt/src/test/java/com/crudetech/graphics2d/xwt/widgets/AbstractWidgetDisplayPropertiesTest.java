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

import com.crudetech.graphics2d.xwt.Font;
import com.crudetech.graphics2d.xwt.FontStyle;
import com.crudetech.graphics2d.xwt.Pen;
import com.crudetech.graphics2d.xwt.SolidBrush;
import org.junit.Test;

import static com.crudetech.matcher.ThrowsException.doesThrow;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class AbstractWidgetDisplayPropertiesTest {
    @Test
    public void addedPropIsAccessible() {
        AbstractWidgetDisplayProperties props = new AbstractWidgetDisplayProperties(){};

        WidgetDisplayProperty prop = new WidgetDisplayProperty(new Pen(3), SolidBrush.Red, new Font("Arial", FontStyle.Italic, 3));
        props.putProperty("Key", "Display Name", prop);

        assertThat(props.getProperty("Key"), is(prop));
    }

    @Test
    public void accessingUnknownPropertyThrows() {
        final AbstractWidgetDisplayProperties props = new AbstractWidgetDisplayProperties(){};

        Runnable doAccess = new Runnable() {
            @Override
            public void run() {
                props.getProperty("Unknown");
            }
        };

        assertThat(doAccess, doesThrow(IllegalArgumentException.class));
    }

    @Test
    public void iteratingProps() {
        AbstractWidgetDisplayProperties props = new AbstractWidgetDisplayProperties(){};

        WidgetDisplayProperty prop = new WidgetDisplayProperty(new Pen(3), SolidBrush.Red, new Font("Arial", FontStyle.Italic, 3));
        props.putProperty("Key", "Display Name", prop);

        WidgetDisplayProperty prop1 = new WidgetDisplayProperty(new Pen(3), SolidBrush.Red, new Font("Arial", FontStyle.Italic, 3));
        props.putProperty("Key1", "Display Name", prop1);

        WidgetDisplayProperty prop2 = new WidgetDisplayProperty(new Pen(3), SolidBrush.Red, new Font("Arial", FontStyle.Italic, 3));
        props.putProperty("Key2", "Display Name", prop);


        assertThat(props.getProperty("Key"), is(prop));
    }

}
