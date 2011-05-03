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

import com.crudetech.i18n.I18nBase;


class I18n extends I18nBase{
    private static final I18n Instance = new I18n();

    public I18n() {
        super(I18n.class.getCanonicalName().replace('.', '/'));
    }
    static String Border(){
      return Instance.getString("Border");
    }

    public static String Text() {
        return Instance.getString("Text");
    }
}
