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

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class XmlControl extends ResourceBundle.Control {
    private static final String XML = "properties.xml";
    static final XmlControl Instance = new XmlControl();

    @Override
    public List<String> getFormats(String baseName) {
        return Collections.singletonList(XML);
    }

    @Override
    public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader, boolean reload)
            throws IllegalAccessException, InstantiationException, IOException {

        if ((baseName == null) || (locale == null) || (format == null)
                || (loader == null)) {
            throw new NullPointerException();
        }

        if (!format.equals(XML)) {
            return null;
        }

        String bundleName = toBundleName(baseName, locale);
        String resourceName = toResourceName(bundleName, format);
        URL url = loader.getResource(resourceName);
        if (url == null) {
            return null;
        }
        URLConnection connection = url.openConnection();
        if (connection == null) {
            return null;
        }
        if (reload) {
            connection.setUseCaches(false);
        }
        InputStream stream = connection.getInputStream();
        BufferedInputStream bis = new BufferedInputStream(stream);
        try {
            ResourceBundle bundle = new PropertyXMLResourceBundle(bis);
            return bundle;
        } catch (IOException ex) {
            bis.close();
            throw ex;
        }

    }

    /**
     * ResourceBundle that loads definitions from an XML properties file.
     */
    private static class PropertyXMLResourceBundle extends ResourceBundle {
        private Properties props;

        /**
         * Creates the ResourceBundle instance based on the InputStream
         *
         * @param stream The properties input stream
         * @throws IOException Any exception that can occur while reading the stream
         */
        PropertyXMLResourceBundle(InputStream stream) throws IOException {
            props = new Properties();
            props.loadFromXML(stream);
        }

        @Override
        protected Object handleGetObject(String key) {
            return props.getProperty(key);
        }

        @Override
        public Enumeration<String> getKeys() {
            Set<String> handleKeys = props.stringPropertyNames();
            return Collections.enumeration(handleKeys);
        }
    }
}
