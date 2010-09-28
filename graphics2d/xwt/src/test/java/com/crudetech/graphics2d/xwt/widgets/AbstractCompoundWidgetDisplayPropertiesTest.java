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
import org.junit.Before;
import org.junit.Test;

import static com.crudetech.matcher.RangeIsEquivalent.equivalentTo;
import static com.crudetech.matcher.ThrowsException.doesThrow;
import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsNull.notNullValue;


public class AbstractCompoundWidgetDisplayPropertiesTest {
    private AbstractCompoundWidgetDisplayProperties compoundProps;
    private Iterable<WidgetDisplayProperties.Info> props;

    @Before
    public void before() {
        final WidgetDisplayProperties.Info[] rawProps = new WidgetDisplayProperties.Info[]{
                new AbstractWidgetDisplayProperties.InfoImp("Name1", new WidgetDisplayProperty(new Pen(3f), new SolidBrush(Color.Red), new Font("Arial", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name2", new WidgetDisplayProperty(new Pen(2f), new SolidBrush(Color.Blue), new Font("Arial", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name3", new WidgetDisplayProperty(new Pen(1f), new SolidBrush(Color.Black), new Font("Courier", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name4", new WidgetDisplayProperty(new Pen(7f), new SolidBrush(Color.Green), new Font("Arial", FontStyle.Italic, 12))),
                new AbstractWidgetDisplayProperties.InfoImp("Name5", new WidgetDisplayProperty(new Pen(2f), new SolidBrush(Color.Blue), new Font("Arial", FontStyle.Italic, 12))),
        };
        WidgetDisplayProperties prop1 = new AbstractWidgetDisplayProperties() {
            {
                putProperty("#1", rawProps[0].getDisplayName(), rawProps[0].getProperty());
                putProperty("#2", rawProps[1].getDisplayName(), rawProps[1].getProperty());
                putProperty("#3", rawProps[2].getDisplayName(), rawProps[2].getProperty());
            }
        };
        WidgetDisplayProperties prop2 = new AbstractWidgetDisplayProperties() {
            {
                putProperty("#4", rawProps[3].getDisplayName(), rawProps[3].getProperty());
                putProperty("#5", rawProps[4].getDisplayName(), rawProps[4].getProperty());
            }
        };
        compoundProps = new AbstractCompoundWidgetDisplayProperties(prop1, prop2);
        props = asList(rawProps);
    }

    @Test
    public void iteratingCompoundPropsConcatSubProps() {
        assertThat(props, is(equivalentTo(compoundProps)));
    }

    @Test
    public void findProps() {
        assertThat(compoundProps.getPropertyInfo("#1"), is(notNullValue()));
        assertThat(compoundProps.getPropertyInfo("#5"), is(notNullValue()));
    }

    @Test
    public void findPropsThrowsOnIllegalKey() {
        Runnable accessIllegalKey = new Runnable() {
            @Override
            public void run() {
                compoundProps.getPropertyInfo("#42");
            }
        };
        assertThat(accessIllegalKey, doesThrow(IllegalArgumentException.class));
    }
}
