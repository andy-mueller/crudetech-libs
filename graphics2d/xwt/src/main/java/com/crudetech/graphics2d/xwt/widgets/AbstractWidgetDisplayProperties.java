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

import com.crudetech.lang.ArgumentNullException;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Contains the display properties of a {@link Widget}. Derived widgets will
 * have specific types. At this base level you are able to iterate over all
 * contained properties.
 */
public abstract class AbstractWidgetDisplayProperties implements WidgetDisplayProperties {
    private final Map<String, Info> properties =
            new HashMap<String, Info>();

    protected void putProperty(String key, String displayName, WidgetDisplayProperty displayProperty) {
        properties.put(key, new InfoImp(displayName, displayProperty));
    }

    static class InfoImp implements Info {
        private final String displayName;
        private final WidgetDisplayProperty displayProperty;

        InfoImp(String displayName, WidgetDisplayProperty displayProperty) {
            if(displayName == null) throw new ArgumentNullException("displayName");
            if(displayProperty== null) throw new ArgumentNullException("displayProperty");
            this.displayName = displayName;
            this.displayProperty = displayProperty;
        }

        public WidgetDisplayProperty getProperty() {
            return displayProperty;
        }

        public String getDisplayName() {
            return displayName;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            InfoImp infoImp = (InfoImp) o;

            if (!displayName.equals(infoImp.displayName)) return false;
            if (!displayProperty.equals(infoImp.displayProperty)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = displayName.hashCode();
            result = 31 * result + displayProperty.hashCode();
            return result;
        }
    }

    @Override
    public Iterator<Info> iterator() {
        return properties.values().iterator();
    }

    public WidgetDisplayProperty getProperty(String key) {
        return getPropertyInfo(key).getProperty();
    }

    @Override
    public Info getPropertyInfo(String key) {
        Info i = properties.get(key);
        if (i == null) throw new IllegalArgumentException();
        return i;
    }
}
