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

import static com.crudetech.matcher.RangeIsEquivalent.equivalentTo;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;


public class AbstractCompoundWidgetDisplayPropertiesTest {
    @Test
    public void iteratingCompoundPropsConcatSubProps() {
        final WidgetDisplayProperties.Info[] props = new WidgetDisplayProperties.Info[]{
                new AbstractWidgetDisplayProperties.InfoImp("Name1", new WidgetDisplayProperty(new Pen(3f), new SolidBrush(Color.Red), new Font("Arial", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name2", new WidgetDisplayProperty(new Pen(2f), new SolidBrush(Color.Blue), new Font("Arial", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name3", new WidgetDisplayProperty(new Pen(1f), new SolidBrush(Color.Black), new Font("Courier", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name4", new WidgetDisplayProperty(new Pen(7f), new SolidBrush(Color.Green), new Font("Arial", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name2", new WidgetDisplayProperty(new Pen(2f), new SolidBrush(Color.Blue), new Font("Arial", FontStyle.Italic, 12))),
        };
        WidgetDisplayProperties prop1 = new AbstractWidgetDisplayProperties() {
            {
                putProperty("#1", props[0].getDisplayName(), props[0].getProperty());
                putProperty("#2", props[1].getDisplayName(), props[1].getProperty());
                putProperty("#3", props[2].getDisplayName(), props[2].getProperty());
            }
        };
        WidgetDisplayProperties prop2 = new AbstractWidgetDisplayProperties() {
            {
                putProperty("#4", props[3].getDisplayName(), props[3].getProperty());
                putProperty("#5", props[4].getDisplayName(), props[4].getProperty());
            }
        };
        AbstractCompoundWidgetDisplayProperties compoundProps = new AbstractCompoundWidgetDisplayProperties(prop1, prop2);

        assertThat(asList(props), is(equivalentTo(compoundProps)));
    }
}
