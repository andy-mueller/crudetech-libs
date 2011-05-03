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

public interface LocaleProvider {
    public static final LocaleProvider Default = new LocaleProvider() {
        @Override
        public Locale getCurrentLocale() {
            return Locale.getDefault();
        }
    };
     public static LocaleProvider English = new LocaleProvider() {
         @Override
         public Locale getCurrentLocale() {
             return Locale.ENGLISH;
         }
     };
    public static LocaleProvider German = new LocaleProvider() {
         @Override
         public Locale getCurrentLocale() {
             return Locale.GERMAN;
         }
     };

    Locale getCurrentLocale();
}
