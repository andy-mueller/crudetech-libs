package com.crudetech.junit.hierarchy.stubs;

import com.crudetech.junit.hierarchy.Hierarchical;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;


@SuppressWarnings("unused")
@RunWith(Hierarchical.class)
public class InnerNonStaticClassWithBefores {
    @Before
    public void outerBefore() throws Exception {
        Tracker.executed();
    }
    @Test
    public void outerTest() throws Exception {
        Tracker.executed();
    }
    public  class Inner{
        @Before
        public void innerBefore() throws Exception {
            Tracker.executed();
        }
        @Test
        public void innerTest() throws Exception {
            Tracker.executed();
        }
    }

}
