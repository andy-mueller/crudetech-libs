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

import java.util.ResourceBundle;

public interface ResourceBundleControlProvider {
    ResourceBundle.Control getControl();

    static final ResourceBundleControlProvider Default = new ResourceBundleControlProvider() {
        @Override
        public ResourceBundle.Control getControl() {
            return ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_DEFAULT);
        }
    };
    static final ResourceBundleControlProvider Properties = new ResourceBundleControlProvider() {
        @Override
        public ResourceBundle.Control getControl() {
            return ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_PROPERTIES);
        }
    };
    static final ResourceBundleControlProvider Class= new ResourceBundleControlProvider() {
        @Override
        public ResourceBundle.Control getControl() {
            return ResourceBundle.Control.getControl(ResourceBundle.Control.FORMAT_CLASS);
        }
    };
    static final ResourceBundleControlProvider Xml = new ResourceBundleControlProvider() {
        @Override
        public ResourceBundle.Control getControl() {
            return XmlControl.Instance;
        }
    };
}
