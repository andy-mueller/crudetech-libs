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

import java.util.ResourceBundle;


class I18n {
//    static String Border = getString("Border");
    static String Border(){
      return getString("Border");  
    }

    private static ResourceBundle resourceBundle =
            ResourceBundle.getBundle(I18n.class.getCanonicalName().replace('.', '/'));

    static String getString(String key) {
        return resourceBundle.getString(key);
    }

    public static String Text() {
        return resourceBundle.getString("Text");        
    }
}
