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
package com.crudetech.junit.categories;

import org.junit.runner.Description;
import org.junit.runner.manipulation.Filter;
import org.junit.runner.manipulation.NoTestsRemainException;
import org.junit.runners.Suite;
import org.junit.runners.model.InitializationError;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.CachingMetadataReaderFactory;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.lang.reflect.Modifier;
import java.util.*;

import static com.crudetech.junit.categories.If.isNull;
import static com.crudetech.junit.categories.If.isNullOrEmpty;
import static java.util.Arrays.asList;

/**
 * A suite runner that collects all tests from the class path that match the
 * pattern specified in the {@link TestNamePattern} annotation;
 */
public class Categories extends Suite {

    public Categories(Class<?> testClass) throws InitializationError {
        super(testClass, allTestClassesInClassPathMatchingPattern(testClass));

        try {
            filter(new CategoryFilter(getInclusions(testClass), getExclusions(testClass)));
        } catch (NoTestsRemainException e) {
            throw new InitializationError(e);
        }
    }

    private static Collection<Class<?>> getExclusions(Class<?> testClass) {
        ExcludeCategory exc = testClass.getAnnotation(ExcludeCategory.class);
        if (isNull(exc) || isNullOrEmpty(exc.value())) {
            return asList();
        }
        return asList(exc.value());
    }

    private static final Class<?> All = Void.class;

    private static Collection<Class<?>> getInclusions(Class<?> testClass) throws InitializationError {
        IncludeCategory inc = testClass.getAnnotation(IncludeCategory.class);
        if (isNull(inc) || isNullOrEmpty(inc.value())) {
            return Arrays.<Class<?>>asList(All);
        }
        return asList(inc.value());
    }

    static class CategoryFilter extends Filter {

        private final Collection<Class<?>> inclusions;
        private final Collection<Class<?>> exclusions;

        public CategoryFilter(Collection<Class<?>> inclusions, Collection<Class<?>> exclusions) {
            this.inclusions = inclusions;
            this.exclusions = exclusions;
        }

        @Override
        public boolean shouldRun(Description description) {
//            for(Description child : description.getChildren()){
//                if(shouldRun(child)){
//                    return true;
//                }
//            }

            Collection<Class<?>> categories = getCategories(description);

            if (allCategoriesAreIncluded()) {
                return categoriesAreNotExcluded(categories);
            }

            if (categoriesAreExcluded(categories)) {
                return false;
            }

            boolean shouldRun = categoriesAreIncluded(categories);
            return shouldRun;
        }

        private boolean categoriesAreIncluded(Collection<Class<?>> categories) {
            return atLeastOneIsIncluded(categories, inclusions);
        }


        private boolean categoriesAreExcluded(Collection<Class<?>> categories) {
            return atLeastOneIsIncluded(categories, exclusions);
        }

        private static boolean atLeastOneIsIncluded(Collection<?> in, Collection<?> of) {
            for (Object exclusion : of) {
                if (in.contains(exclusion)) {
                    return true;
                }
            }
            return false;
        }

        private boolean categoriesAreNotExcluded(Collection<Class<?>> categories) {
            return !categoriesAreExcluded(categories);
        }

        private boolean allCategoriesAreIncluded() {
            return inclusions.contains(All);
        }

        private Set<Class<?>> getCategories(Description description) {
            Set<Class<?>> categories = getCategories(description.getAnnotation(Category.class));
            if (description.getTestClass() != null) {
                categories.addAll(getCategories(description.getTestClass()));
            }
            return categories;
        }

        private Set<Class<?>> getCategories(Class<?> clazz){
            return getCategories(clazz != null ? clazz.getAnnotation(Category.class) : null);
        }
        private Set<Class<?>> getCategories(Category cat) {
            if (isNull(cat)) {
                return new HashSet<Class<?>>(asList(StandardCategory.class));
            }
            if (isNull(cat.value())) {
                return new HashSet<Class<?>>();
            }
            return new HashSet<Class<?>>(asList(cat.value()));
        }

        @Override
        public String describe() {
            return "category";
        }
    }

    static Class<?>[] allTestClassesInClassPathMatchingPattern(String pattern) throws InitializationError {
        List<Class<?>> classes = new ArrayList<Class<?>>();

        PathMatchingResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        MetadataReaderFactory metaDataReaderFactory = new CachingMetadataReaderFactory();
        try {
            String classPattern = "classpath:" + pattern.replace('.', '/') + ".class";
            Resource[] res = resolver.getResources(classPattern);
            for (Resource r : res) {
                if (!r.isReadable()) {
                    continue;
                }
                MetadataReader reader = metaDataReaderFactory.getMetadataReader(r);
                Class<?> c = Class.forName(reader.getClassMetadata().getClassName());

                if (Modifier.isAbstract(c.getModifiers())) {
                    continue;
                }
                classes.add(c);
            }
            return classes.toArray(new Class<?>[classes.size()]);
        } catch (Exception e) {
            throw new InitializationError(e);
        }
    }

    private static Class<?>[] allTestClassesInClassPathMatchingPattern(Class<?> testClass) throws InitializationError {
        return allTestClassesInClassPathMatchingPattern(getPatternFrom(testClass));
    }

    private static String getPatternFrom(Class<?> clazz) throws InitializationError {
        TestNamePattern pattern = clazz.getAnnotation(TestNamePattern.class);
        if (isNull(pattern) || isNullOrEmpty(pattern.value())) {
            throw new InitializationError("No proper test name pattern specified!");
        }
        return pattern.value();
    }
}
