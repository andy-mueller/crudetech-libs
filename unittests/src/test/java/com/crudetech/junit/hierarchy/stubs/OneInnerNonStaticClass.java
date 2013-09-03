package com.crudetech.junit.hierarchy.stubs;

import com.crudetech.junit.hierarchy.Hierarchical;
import org.junit.Test;
import org.junit.runner.RunWith;


@SuppressWarnings("unused")
@RunWith(Hierarchical.class)
public class OneInnerNonStaticClass {
    public  class Inner{
        @Test
        public void atest() throws Exception {
            Tracker.executed();
        }
    }
}
