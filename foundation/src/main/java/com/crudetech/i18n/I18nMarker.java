////////////////////////////////////////////////////////////////////////////////
// Copyright (c) 2011, Andreas Mueller.
// All rights reserved. This program and the accompanying materials
// are made available under the terms of the Eclipse Public License v1.0
// which accompanies this distribution, and is available at
// http://www.eclipse.org/legal/epl-v10.html
//
// Contributors:
//      Andreas Mueller - initial API and implementation
////////////////////////////////////////////////////////////////////////////////
package com.crudetech.i18n;

/**
 * Helper methods to mark strings do be included or excluded in I18n.
 */
public class I18nMarker {
    private I18nMarker() {
    }

    /**
     * This string should not be translated. This is typically used for exception
     * or log messages:
     * {@code throw new IllegalArgumentException(_DNT("The argument foo is not valid!"));}
     * <p>
     * DNT stands for <b>D</b>o <b>N</b>ot <b>T</b>ranslate
     *
     * @param s The marked string.
     * @return the parameter s unmodified.
     */
    public static String _DNT(String s) {
        return s;
    }

    /**
     * This string shall be translated.
     *
     * {@code label.setText(_T("Caption to be translated"));}
     *
     * @param s The marked string.
     * @return the parameter s unmodified.
     */
    public static String _T(String s) {
        return s;
    }
}
