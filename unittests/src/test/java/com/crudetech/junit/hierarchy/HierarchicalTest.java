package com.crudetech.junit.hierarchy;

import com.crudetech.junit.hierarchy.stubs.OneInnerNonStaticClass;
import com.crudetech.junit.hierarchy.stubs.OneInnerStaticClass;
import com.crudetech.junit.hierarchy.stubs.Tracker;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.Description;
import org.junit.runner.JUnitCore;
import org.junit.runner.notification.RunListener;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.Matchers.is;

public class HierarchicalTest {
    @Before
    public void resetTracker(){
        Tracker.reset();
    }


    private void runTest(Class<?> testClass) {
        JUnitCore jUnitCore = new JUnitCore();
        assertThat(jUnitCore.run(testClass).getRunCount(), is(greaterThanOrEqualTo(1)));
    }

    @Test
    public void givenAStaticInnerClass_InnerClassIsExecuted() throws Exception {
        runTest(OneInnerStaticClass.class);
        assertThat(Tracker.lastMethodRun(), is("OneInnerStaticClass$Inner#atest"));
    }

    @Test
    public void givenANonStaticInnerClass_InnerClassIsExecuted() throws Exception {
        runTest(OneInnerNonStaticClass.class);
        assertThat(Tracker.lastMethodRun(), is("OneInnerNonStaticClass$Inner#atest"));
    }

    //multiple Levels
    //executes befores on inner class
    //executes after on inner class
    //executes rules on inner class
    //executes beforeClass on static outer class before all test/befores/after/rules ran
    //executes afterClass on static outer class  after all test/befores/after/rules ran
    // acceptance test

}
