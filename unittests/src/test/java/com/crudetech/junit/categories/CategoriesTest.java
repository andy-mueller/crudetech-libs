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


import com.crudetech.junit.categories.stubs.*;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

public class CategoriesTest {
    public static final String TestClassPattern = "com.crudetech.junit.categories.stubs.*Stub";

    @Before
    public void before() {
        CategoryTestStub.reset();
    }

    @Test
    public void classPathSearchFindsAllTestForPattern() throws Exception {
        Class<?>[] allTest = Categories.allTestClassesInClassPathMatchingPattern(TestClassPattern);
        assertThat(allTest.length, is(8));
    }

    @Test
    public void singleCategoryIsSelected() {
        runTest(SlowCategorySuite.class);

        assertThat(CategoryTestStub.getExecutionCount(SlowStub.class), is(1));
        assertThat(CategoryTestStub.getExecutionCount(RemoteStub.class), is(0));
    }

    private void runTest(Class<?> testClass) {
        JUnitCore jUnitCore = new JUnitCore();
        assertThat(jUnitCore.run(testClass).getRunCount(), is(greaterThanOrEqualTo(1)));
    }


    @Test
    public void noIncludeCategoryExecutesAllTest() throws Exception {
        runTest(NoIncludeCategorySuite.class);

        for (Class<?> clazz : Categories.allTestClassesInClassPathMatchingPattern(TestClassPattern)) {
            assertThat(clazz.toString(), CategoryTestStub.getExecutionCount(cast(clazz)), is(greaterThanOrEqualTo(1)));
        }
    }

    @Test
    public void noIncludeCategoryExecutesAllExceptExcludedTest() throws Exception {
        runTest(NoIncludeWithExcludeCategorySuite.class);

        List<Class<?>> testClasses = new ArrayList<Class<?>>(asList(Categories.allTestClassesInClassPathMatchingPattern(TestClassPattern)));
        assertThat(testClasses.remove(ParameterizedStub.class), is(true));
        for (Class<?> clazz : testClasses) {
            assertThat(clazz.toString(), CategoryTestStub.getExecutionCount(cast(clazz)), is(greaterThanOrEqualTo(1)));
        }

        assertThat("ParameterizedStub shall be excluded", CategoryTestStub.getExecutionCount(ParameterizedStub.class), is(0));
    }

    private static Class<? extends CategoryTestStub> cast(Class<?> clazz) {
        @SuppressWarnings("unchecked")
        Class<? extends CategoryTestStub> tmp = (Class<? extends CategoryTestStub>) clazz;
        return tmp;
    }

    @Test
    public void multipleCategoriesCanBeExecuted() {
        runTest(SlowAndParameterizedCategorySuite.class);

        assertThat(CategoryTestStub.getExecutionCount(ParameterizedStub.class), is(2));
        assertThat(CategoryTestStub.getExecutionCount(SlowStub.class), is(1));

        assertThat(CategoryTestStub.getExecutionCount(RemoteStub.class), is(0));
    }

    @Test
    public void exclusionFiltersTestsOut() {
        runTest(StandardExcludingSlowCategorySuite.class);

        assertThat(CategoryTestStub.getExecutionCount(StandardExcludingSlowStub.class), is(0));
        assertThat(CategoryTestStub.getExecutionCount(SlowStub.class), is(0));
        assertThat(CategoryTestStub.getExecutionCount(StandardWithoutAnnotationStub.class), is(1));
    }

    @Test
    public void parameterizedTestCanBeIncluded() {
        runTest(ParameterizedCategorySuite.class);

        assertThat(CategoryTestStub.getExecutionCount(ParameterizedStub.class), is(2));
    }

    @Test
    public void categoriesAreInherited() {
        runTest(SomeCategory1InheritedSuite.class);
        assertThat(CategoryTestStub.getExecutionCount(SomeCategory1InheritedStub.class), is(1));
    }
    @Test
    public void inheritedCategoriesMerge() {
        runTest(SomeCategory2Suite.class);
        assertThat(CategoryTestStub.getExecutionCount(SomeCategory1InheritedStub.class), is(1));
        assertThat(CategoryTestStub.getExecutionCount(SomeCategory1Stub.class), is(0));
    }

    @Test
    public void  categoriesWorkOnTestMethods(){
        runTest(CategoryOnMethodsSuite.class);

        assertThat(CategoryTestStub.getExecutionCount(CategoryOnMethodStub.class), is(1));
    }
}
