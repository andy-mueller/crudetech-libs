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
package com.crudetech.junit.feature;

import org.junit.runner.Runner;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.List;

public class FeaturesSuite extends Suite {
    public FeaturesSuite(Class<?> klass) throws InitializationError {
        super(klass, extracAndCreateRunners(klass));
    }

    private static List<Runner> extracAndCreateRunners(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (FeatureAccessor field : extractFieldsWithTest(klass)) {

            Class<?> test = field.getFeature();
            runners.add(new FeaturesSuiteRunner(test, field.getFactory()));
        }
        addSuiteIfItContainsTests(klass, runners);
        return runners;
    }

    private static void addSuiteIfItContainsTests(Class<?> klass, List<Runner> runners) {
        try {
            runners.add(new BlockJUnit4ClassRunner(klass));
        } catch (InitializationError e) {// do nothing, no tests
        }
    }


    private static abstract class FeatureAccessor<TField extends AnnotatedElement & Member> {
        static <TField extends AnnotatedElement & Member> boolean isValid(TField field) {
            return Modifier.isPublic(field.getModifiers())
                    && Modifier.isStatic(field.getModifiers())
                    && field.isAnnotationPresent(Feature.class);
        }

        static <TField extends AccessibleObject & AnnotatedElement> FeatureAccessor<?> createFrom(final TField field) {
            if (!field.isAccessible()) {
                field.setAccessible(true);
            }

            if (field instanceof Method) {
                return new FeatureAccessor<Method>((Method) field) {
                    @Override
                    Object extractFactory() throws Exception {
                        return this.field.invoke(null);
                    }
                };
            }
            if (field instanceof Field) {
                return new FeatureAccessor<Field>((Field) field) {
                    @Override
                    Object extractFactory() throws Exception {
                        return this.field.get(null);
                    }
                };
            }

            throw new IllegalArgumentException();
        }

        final TField field;

        public FeatureAccessor(TField field) {
            this.field = field;
        }

        Object getFactory() {
            Object factory;
            try {
                factory = extractFactory();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
            if (factory == null) {
                throw new NullPointerException();
            }
            return factory;
        }

        abstract Object extractFactory() throws Exception;

        Class<?> getFeature() {
            return field.getAnnotation(Feature.class).value();
        }
    }

    private static List<FeatureAccessor> extractFieldsWithTest(Class<?> klass) {
        List<FeatureAccessor> factoryFieldsWithProperty = new ArrayList<FeatureAccessor>();
        for (Field field : klass.getFields()) {
            if (!FeatureAccessor.isValid(field)) {
                continue;
            }
            factoryFieldsWithProperty.add(FeatureAccessor.createFrom(field));
        }
        for (Method method : klass.getMethods()) {
            if (!FeatureAccessor.isValid(method)) {
                continue;
            }
            factoryFieldsWithProperty.add(FeatureAccessor.createFrom(method));
        }
        return factoryFieldsWithProperty;
    }
}
