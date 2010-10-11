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

import java.util.Arrays;

public class RectangularBorderedTextWidget extends CompoundWidget{
    public RectangularBorderedTextWidget(String text, double width, double height, RectangularBorderedTextWidgetDispProps props) {
        super(createSubWidgets(text, width, height, props));
        setDisplayProperties(props);
    }

    private static Iterable<Widget> createSubWidgets(String text, double width, double height, RectangularBorderedTextWidgetDispProps props) {
        return Arrays.<Widget>asList(
                new TextWidget(text,width,height,props.getTextProperties()), // TODO: This is not good
                new RectangularBorderedWidget(width,height,props.getBorderProperties())
        );
    }

    public String getText(){
        return ((TextWidget)getComponents().iterator().next()).getText();        
    }
}
