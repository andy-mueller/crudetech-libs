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

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;

import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class FeaturesSuiteFixture {
    private Result runTest(Class<?> testClass) {
        JUnitCore core = new JUnitCore();
        return core.run(testClass);
    }

    @Before
    public void before() {
        TestTracker.reset();
    }

    @Test
    public void allPropertiesArePickedUpAndRunAsTest() {
        Result result = runTest(SuiteWithTwoCollectionProperties.class);

        assertThat(result.wasSuccessful(), is(true));
        assertThat(result.getRunCount(), is(2));
        assertThat(TestTracker.getExecutionCount(SuiteWithTwoCollectionProperties.Prop1.class), is(1));
        assertThat(TestTracker.getExecutionCount(SuiteWithTwoCollectionProperties.Prop2.class), is(1));
    }

    @Test
    public void nullFactoryInstanceFails() {
        Result result = runTest(SuiteWithNullFactoryInstance.class);

        assertThat(result.wasSuccessful(), is(false));
        assertThat(result.getRunCount(), is(1));
        assertThat(result.getFailureCount(), is(1));
        assertThat(result.getFailures().get(0).getException(), is(instanceOf(NullPointerException.class)));

        assertThat(TestTracker.getExecutionCount(SuiteWithNullFactoryInstance.Prop1.class), is(0));
        assertThat(TestTracker.getExecutionCount(SuiteWithNullFactoryInstance.Prop2.class), is(0));
    }
    @Test
    public void testsInsideSuiteAreAlsoExecuted() {
        Result result = runTest(SuiteWithTwoCollectionPropertiesAndTests.class);

        assertThat(result.wasSuccessful(), is(true));
        assertThat(result.getRunCount(), is(3));
        assertThat(TestTracker.getExecutionCount(SuiteWithTwoCollectionPropertiesAndTests.Prop1.class), is(1));
        assertThat(TestTracker.getExecutionCount(SuiteWithTwoCollectionPropertiesAndTests.Prop2.class), is(1));
        assertThat(TestTracker.getExecutionCount(SuiteWithTwoCollectionPropertiesAndTests.class), is(1));
    }
    @Test
    public void suiteWithIncompatibleFactoryTypeFails()throws Exception{
        Result result = runTest(SuiteWithIncompatibleFactoryInstance.class);

        assertThat(result.wasSuccessful(), is(false));
        assertThat(result.getRunCount(), is(2));
        assertThat(result.getFailureCount(), is(1));
        assertThat(result.getFailures().get(0).getException(), is(instanceOf(IllegalArgumentException.class)));

        assertThat(TestTracker.getExecutionCount(SuiteWithIncompatibleFactoryInstance.Prop1.class), is(1));
        assertThat(TestTracker.getExecutionCount(SuiteWithIncompatibleFactoryInstance.Prop2.class), is(0));
    }
    @Test
    public void featuresCanBeOnMethodsToo() {
        Result result = runTest(SuiteWithMethodAsFactory.class);

        assertThat(result.wasSuccessful(), is(true));
        assertThat(result.getRunCount(), is(1));
        assertThat(TestTracker.getExecutionCount(SuiteWithMethodAsFactory.Prop1.class), is(1));
    }
}
