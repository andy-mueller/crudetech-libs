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
package com.crudetech.junit.collections;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.List;

public class CollectionsSuite extends Suite {
    public CollectionsSuite(Class<?> klass) throws InitializationError {
        super(klass, extracAndCreateRunners(klass));
    }

    private static List<Runner> extracAndCreateRunners(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (Field field : extractFieldsWithTest(klass)) {
            Object factory = extractFieldValue(field);
            if (factory == null) {
                throw new InitializationError("Field declarations must not be null!!");
            }
            CollectionProperty prop = field.getAnnotation(CollectionProperty.class);
            Class<?> test = prop.value();
            runners.add(new CollectionsSuiteRunner(test, factory));
        }
        addSuiteIfItContainsTests(klass, runners);
        return runners;
    }
    private static Object extractFieldValue(Field f) throws InitializationError{
        Object o;
        try {
            o = f.get(null);
        } catch (IllegalAccessException e) {
            throw new InitializationError(e);
        }
        if(o == null){
            throw new NullPointerException("Field declaration must not be null!");
        }
        return o;
    }

    private static void addSuiteIfItContainsTests(Class<?> klass, List<Runner> runners) {
        try {
            runners.add(new BlockJUnit4ClassRunner(klass));
        } catch (InitializationError e) {// do nothing, no tests
        }
    }


    private static List<Field> extractFieldsWithTest(Class<?> klass) {
        List<Field> factoryFieldsWithProperty = new ArrayList<Field>();
        for (Field field : klass.getFields()) {
            if (!Modifier.isPublic(field.getModifiers())) {
                continue;
            }
            if (!Modifier.isStatic(field.getModifiers())) {
                continue;
            }
            if (!field.isAnnotationPresent(CollectionProperty.class)) {
                continue;
            }

            if (!field.isAccessible()) {
                field.setAccessible(true);
            }
            factoryFieldsWithProperty.add(field);
        }
        return factoryFieldsWithProperty;
    }
}
