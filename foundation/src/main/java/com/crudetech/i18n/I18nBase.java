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

/**
 * A helper base class to simplify i18 related issues, such as loading the correct
 * resource bundle, loading resources from other file types than the default,
 * using the appropriate locale and so on.
 * <p>
 * To use it, derive your own local class. You can have multiple derivations and/or
 * instances in your source code. A typical setup would be to have one instance in
 * your project. Another typical setup is one derived instance per package.
 *<p>
 * Here is an example of a local derivation that will derive the base name of the property
 * file from its class name and extract strings from the file
 * /com/exampe/I18n.properties.xml. It will use the current default locale of the jvm.
 *
 *
 * <pre>
 *     package com.example;
 *     class I18n extends I18nBase{
 *          static final I18n Instance = new I18n();
 *     }
 * </pre>
 * <p>
 * Sometimes you want to switch the locale according to your context. A typical example for this is
 * when you have a web application or any other sort of server. Then you usually want to use the locale
 * of the calling client. Assuming that the requests Locale is stored inside an associated session
 * object, an implementation could look like this:
 * <pre>
 *     package com.example
 *     class SessionLocaleProvider implements LocaleProvider{
 *         &#064;Override
           public Locale getCurrentLocale() {
               return Session.get().getLocale();
           }
 *     }
 *     class I18n extends I18nBase{
 *          private static I18n Instance = new I18n();
 *     }
 * </pre>
 * Inside your application initialisation, e.g. your main method, spring context or  servlet init
 * method, you instantiate an override for the {@link LocaleProvider} implementation that
 * all I18nBase classes will use:
 * <pre>
 *     I18nBase.LocaleProviderOverride localeOverride =
 *           new LocaleProviderOverride(new SessionLocaleProvider());
 * </pre>
 * <p>
 * Last and least, you can control the type of the resource bundle by implementing the
 * {@link ResourceBundleControlProvider} interface and pass it to the ctor of this class. The default
 * is not Java's default, but xml property files. The reason for this is, that there ere a lot of
 * encoding issues. Here is an example of a derived class using the the standard java formats,
 * i.e. property files and/or classes.
 * <pre>
 *     package com.example
 *     class I18n extends I18nBase{
 *          private static I18n Instance = new I18n();
 *          I18n(){
 *              super(ResourceBundleControlProvider.Default);
 *          }
 *     }
 * </pre>
 *
 */
public abstract class I18nBase {
    private final String baseName;
    private static volatile LocaleProvider localeProvider = LocaleProvider.Default;
    private final ResourceBundleControlProvider controlProvider;

    public static class LocaleProviderOverride implements com.crudetech.lang.AutoCloseable{
        private LocaleProvider localeProvider = LocaleProvider.Default;
        public LocaleProviderOverride(LocaleProvider localeProvider) {
            this.localeProvider = I18nBase.localeProvider;
            I18nBase.localeProvider = localeProvider;
        }

        public void reset() {
            I18nBase.localeProvider = this.localeProvider;
        }

        @Override
        public void close(){
            reset();
        }
    }


    private static final String UseClassName = "I18nBase"+ '\u0000'  +"UseClassName";
    public I18nBase(String baseName) {
        this(baseName, ResourceBundleControlProvider.Xml);
    }
    public I18nBase() {
        this(ResourceBundleControlProvider.Xml);
    }
    public I18nBase(ResourceBundleControlProvider rbcp) {
        this(UseClassName, rbcp);
    }
    public I18nBase(String baseName, ResourceBundleControlProvider controlProvider) {
        this.baseName = UseClassName.equals(baseName) ? getClass().getName().replace('.', '/') :  baseName;
        this.controlProvider = controlProvider;
    }


    public String getString(String key) {
        Locale locale = localeProvider.getCurrentLocale();
        ResourceBundle.Control control = controlProvider.getControl();
        ResourceBundle rb = ResourceBundle.getBundle(baseName, locale, control);
        return rb.getString(key);
    }
}
