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


import java.util.Locale;
import java.util.ResourceBundle;

public abstract class I18nBase {
    private final String baseName;
    private static volatile LocaleProvider localeProvider = LocaleProvider.Default;
    private final ResourceBundleControlProvider controlProvider;

    public static class LocaleProviderOverride {
        private LocaleProvider localeProvider = LocaleProvider.Default;
        public LocaleProviderOverride(LocaleProvider localeProvider) {
            this.localeProvider = I18nBase.localeProvider;
            I18nBase.localeProvider = localeProvider;
        }

        public void reset() {
            I18nBase.localeProvider = this.localeProvider;
        }
    }


    public I18nBase(String baseName) {
        this(baseName, ResourceBundleControlProvider.Xml);
    }
    public I18nBase(String baseName, ResourceBundleControlProvider controlProvider) {
        this.baseName = baseName;
        this.controlProvider = controlProvider;
    }


    public String getString(String key) {
        Locale locale = localeProvider.getCurrentLocale();
        ResourceBundle.Control control = controlProvider.getControl();
        ResourceBundle rb = ResourceBundle.getBundle(baseName, locale, control);
        return rb.getString(key);
    }
}
