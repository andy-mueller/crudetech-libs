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

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ResourceBundle;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class I18nBaseTest {
    static class I18n extends I18nBase {
        I18n() {
            super("i18ntest/i18n");
        }

        I18n(ResourceBundleControlProvider rb) {
            super("i18ntest/i18n", rb);
        }
    }

    private I18nBase instance;

    @Before
    public void before() {
        instance = new I18n();
    }

    @After
    public void after() {
        ResourceBundle.clearCache();
    }

    @Test
    public void defaultSetupUsesMainResources() {
        try (I18nBase.LocaleProviderOverride germanXml = new I18nBase.LocaleProviderOverride(LocaleProvider.German)) {
            String xmlTestString = instance.getString("testString");

            assertThat(xmlTestString, is("xml:Test erfolgreich!"));
        }

    }

    @Test
    public void propertyResourceBundleControlUsesPropertyFiles() {
        try (I18nBase.LocaleProviderOverride germanProp = new I18nBase.LocaleProviderOverride(LocaleProvider.German)) {
            instance = new I18n(ResourceBundleControlProvider.Properties);
            String propTestString = instance.getString("testString");
            assertThat(propTestString, is("prop:Test erfolgreich!"));
        }
    }

    @Test
    public void getStringFetchesWithProvidedLocale() {
        try (I18nBase.LocaleProviderOverride englishXml = new I18nBase.LocaleProviderOverride(LocaleProvider.English)) {
            String result = instance.getString("testString");
            assertThat(result, is("xml:Test successful!"));
        }
    }

    @Test
    public void overrideIsResetable() {
        try (I18nBase.LocaleProviderOverride englishXml = new I18nBase.LocaleProviderOverride(LocaleProvider.English)) {
            try (I18nBase.LocaleProviderOverride germanXml = new I18nBase.LocaleProviderOverride(LocaleProvider.German)) {
                String result = instance.getString("testString");
                assertThat(result, is("xml:Test erfolgreich!"));
            }
            String result = instance.getString("testString");
            assertThat(result, is("xml:Test successful!"));
        }
    }

    static class ImplicitBaseNameI18n extends I18nBase {
    }

    @Test
    public void noBaseNameUsesClassName() {
        try (I18nBase.LocaleProviderOverride germanXml = new I18nBase.LocaleProviderOverride(LocaleProvider.German)) {
            I18nBase i18n = new ImplicitBaseNameI18n();
            String result = i18n.getString("testString");
            assertThat(result, is("xml-classname:Test erfolgreich!"));
        }
    }

}
