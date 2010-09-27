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

import java.util.Iterator;

import static com.crudetech.collections.Iterables.cast;
import static com.crudetech.collections.Iterables.concat;
import static java.util.Arrays.asList;

public class AbstractCompoundWidgetDisplayProperties implements WidgetDisplayProperties {
    private final Iterable<WidgetDisplayProperties> props;

    public AbstractCompoundWidgetDisplayProperties(WidgetDisplayProperties... prop) {
        this(asList(prop));
    }

    public AbstractCompoundWidgetDisplayProperties(Iterable<WidgetDisplayProperties> props) {
        this.props = props;
    }

    @Override
    public Info getPropertyInfo(String key) {
        for (WidgetDisplayProperties prop : props) {
            Info i = prop.getPropertyInfo(key);
            if(i != null){
                return i;
            }
        }
        return null;
    }

    @Override
    public Iterator<WidgetDisplayProperties.Info> iterator() {
        Iterable<Iterable<Info>> infos = cast(props);
        return concat(infos).iterator();
    }
}
