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

/**
 * Features are a way to reuse and compose generic test fixtures. An example for this
 * is the correct implementations of {@link Object#equals(Object)} and {@link Object#hashCode()}
 * or implementations of the java collections interfaces that you will test over and over again.
 * <p/>
 * The following example demonstrates the reuse of the generic {@link Equivalent}
 * and {@link Serializable} test fixture to test for the correct implementation of the equals contract for
 * a simple Pair class:
 * <p/>
 * <pre>
 * public class Pair implements java.io.Serializable {
 *    private final Object first;
 *    private final Object second;
 *
 *    public Pair(final Object first, final Object second) {
 *        this.second = second;
 *        this.first = first;
 *    }
 *    public Object getFirst() {
 *        return first;
 *    }
 *    public Object getSecond() {
 *        return second;
 *    }
 *
 *    &#064;Override
 *    public boolean equals(Object o) {
 *        if (this == o) return true;
 *        if (o == null || getClass() != o.getClass()) return false;
 *
 *        Pair pair = (Pair) o;
 *
 *       return equals(first, pair.first)
 *           && equals(second, pair.second);
 *    }
 *    private boolean equals(Object lhs, Object rhs){
 *        return lhs == null ? rhs == null : lhs.equals(rhs);
 *    }
 *    &#064;Override
 *    public int hashCode() {
 *        int result = hashCode(first);
 *        result = 31 * result + hashCode(second);
 *        return result;
 *    }
 *    private int hashCode(Object o) {
 *        return o == null ? 0 : o.hashCode();
 *    }
 * }
 *
 * &#064;RunWith(Features.class)
 * public class PairFixture {
 *    &#064;Test
 *    public void getFirstReturnsValuePassedInCtor() throws Exception {
 *        Pair p = new Pair(2, "sdf");
 *        assertThat(p.getFirst(), is(2));
 *    }
 *    &#064;Feature(Equivalent.class)
 *    public static Equivalent.Factory<Pair> equivalentFeature() {
 *        return new Equivalent.Factory<Pair>() {
 *            &#064;Override
 *            public Pair createItem() {
 *                return new Pair(2, "default");
 *            }
 *
 *            &#064;Override
 *            public List<Pair> createOtherItems() {
 *                return asList(
 *                        new Pair(2, "other"),
 *                        new Pair(5, "default"),
 *                        new Pair(5, "other")
 *                );
 *            }
 *        };
 *    }
 *    &#064;Feature(Serializable.class)
 *    public static Serializable.Factory<Pair> serializableFeature(){
 *        return new Serializable.Factory<Pair>(){
 *            &#064;Override
 *            public Pair createItem() {
 *                return new Pair(2, "default");
 *            }
 *        }
 *    }
 * }
 *
 * </pre>
 * <p/>
 * You can compose as many feature test fixtures using the {@link Feature} annotation as you like.
 * A feature implementation must implement the {@link FeatureFixture} interface and provide a public
 * one argument constructor taking the appropriate factory to create the objects for the tests.
 */
public class Features extends Suite {
    public Features(Class<?> klass) throws InitializationError {
        super(klass, extractAndCreateRunners(klass));
    }

    private static List<Runner> extractAndCreateRunners(Class<?> klass) throws InitializationError {
        List<Runner> runners = new ArrayList<Runner>();
        for (FeatureAccessor field : extractFieldsWithTest(klass)) {
            @SuppressWarnings("unchecked")
            Class<? extends FeatureFixture> test = field.getFeature();
            runners.add(new FeatureRunner(test, field.getFactory(), field.getName()));
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

        Class<? extends FeatureFixture> getFeature() {
            return field.getAnnotation(Feature.class).value();
        }

        String getName() {
            return this.field.getName();
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
