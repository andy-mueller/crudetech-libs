package com.crudetech.junit.hierarchy;

import com.crudetech.junit.feature.TestTracker;
import com.crudetech.junit.hierarchy.stubs.*;
import org.hamcrest.collection.IsIterableContainingInOrder;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import sun.swing.BakedArrayList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class HierarchicalTest {
    @Before
    public void resetTracker(){
        Tracker.reset();
    }

    private void runTest(Class<?> testClass) {
        JUnitCore jUnitCore = new JUnitCore();
        Result result = jUnitCore.run(testClass);
        assertThat(result.getRunCount(), is(greaterThanOrEqualTo(1)));
        assertThat(Arrays.toString(result.getFailures().toArray()),result.getFailureCount(), is((0)));

    }

    @Test
    public void givenAStaticInnerClass_InnerClassIsExecuted() throws Exception {
        runTest(OneInnerStaticClass.class);
        assertTestWasRun("OneInnerStaticClass$Inner#atest");
    }

   @Test
    public void givenANonStaticInnerClass_InnerClassIsExecuted() throws Exception {
        runTest(OneInnerNonStaticClass.class);
        assertTestWasRun("OneInnerNonStaticClass$Inner#atest");
    }

    @Test
    public void givenMultipleNestedInnerClasses_allInnerClassesArePickedUp() throws Exception {
        runTest(MultipleNestedInnerClasses.class);
        assertTestWasRun("MultipleNestedInnerClasses$Inner#atest");
        assertTestWasRun("MultipleNestedInnerClasses$Inner$Inner2#btest");
    }

    private void assertTestWasRun(String testMethod) {
        Tracker.assertMethodWasRun(testMethod);
    }

    @Test
    public void givenNonStaticNestedClass_beforeIsExecutedOnOuterAndInnerClassBeforeTheTest() throws Exception {
        runTest(InnerNonStaticClassWithBefores.class);

        Tracker.assertExecutionOrder(
                "InnerNonStaticClassWithBefores#outerBefore",
                "InnerNonStaticClassWithBefores$Inner#innerBefore",
                "InnerNonStaticClassWithBefores$Inner#innerTest");

//        TestTracker.assertExecutionOrder(
//                "InnerNonStaticClassWithBefores#outerBefore",
//                "InnerNonStaticClassWithBefores#outerTest" );
    }

    //test are run top down??
    //executes after on inner class
    //executes rules on inner class
    //executes beforeClass on static outer class before all test/befores/after/rules ran
    //executes afterClass on static outer class  after all test/befores/after/rules ran
    // acceptance test

}
