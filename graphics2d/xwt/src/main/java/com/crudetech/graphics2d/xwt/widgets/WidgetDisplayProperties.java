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

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Contains the display properties of a {@link Widget}. Derived widgets will
 * have specific types. At this base level you are able to iterate over all
 * contained properties.
 */
public class WidgetDisplayProperties implements Iterable<WidgetDisplayProperties.Info>{
    private final Map<String,Info> properties =
            new HashMap<String, Info>();
    public void putProperty(String key, String displayName, WidgetDisplayProperty displayProperty) {
        properties.put(key, new Info(displayName, displayProperty));
    }

    @Override
    public Iterator<Info> iterator() {
        return properties.values().iterator();
    }

    public static class Info{
        private final String displayName;
        private final WidgetDisplayProperty displayProperty;

        private Info(String displayName, WidgetDisplayProperty displayProperty) {
            this.displayName = displayName;
            this.displayProperty = displayProperty;
        }

        public WidgetDisplayProperty getProperty() {
            return displayProperty;
        }

        public String getDisplayName() {
            return displayName;
        }
    }
    public WidgetDisplayProperty getProperty(String key) {
        return getPropertyInfo(key).getProperty();
    }
    public Info getPropertyInfo(String key) {
        Info i = properties.get(key);
        if(i == null) throw new IllegalArgumentException();
        return i;
    }
}
