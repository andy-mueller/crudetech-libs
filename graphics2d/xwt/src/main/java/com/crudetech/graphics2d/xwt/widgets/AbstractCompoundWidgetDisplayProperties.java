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

import com.crudetech.collections.Iterables;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static com.crudetech.collections.Iterables.cast;
import static com.crudetech.collections.Iterables.concat;
import static java.util.Arrays.asList;

public class AbstractCompoundWidgetDisplayProperties implements WidgetDisplayProperties {
    private final List<WidgetDisplayProperties> props;

    public AbstractCompoundWidgetDisplayProperties(WidgetDisplayProperties... prop) {
        this(asList(prop));
    }

    public AbstractCompoundWidgetDisplayProperties(Iterable<WidgetDisplayProperties> props) {
        this.props = Iterables.copy(props, new ArrayList<WidgetDisplayProperties>());
    }

    @Override
    public Info getPropertyInfo(String key) {
        for (WidgetDisplayProperties prop : props) {
            if(prop.hasProperty(key)){
                return prop.getPropertyInfo(key);
            }
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean hasProperty(String key) {
        for (WidgetDisplayProperties prop : props) {
            if(prop.hasProperty(key)){
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<WidgetDisplayProperties.Info> iterator() {
        Iterable<Iterable<Info>> infos = cast(props);
        return concat(infos).iterator();
    }
    protected WidgetDisplayProperties getPropertiesAt(int pos){
        return props.get(pos);
    }
}
