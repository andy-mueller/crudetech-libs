package com.crudetech.junit.hierarchy;

import org.junit.*;

@SuppressWarnings("unused")
//@RunWith(Hierarchical.class)
public class HierarchyAcceptanceTest {

    @Before
    public void before_0() {
    }


    @Test
    public void level_0() throws Exception {

    }

    @After
    public void after_0() {
    }

    @BeforeClass
    public static void beforeClass_0() {
    }

    @AfterClass
    public static void afterClass_0() {
    }

    //    @Context
    public class Level_1_0 {
        @Before
        public void before_1_0() {

        }

        @Test
        public void level_1_0() throws Exception {

        }

        @After
        public void after_1_0() {
        }


        //@Context
        public class Level_1_0_1 {
            @Before
            public void before_1_0_1() {

            }

            @Test
            public void level_1_0_1() throws Exception {

            }

            @After
            public void after_1_0_1() {
            }

        }
    }


    //@Context
    public class Level_1_1 {
        @Before
        public void before_1_1_setup() {

        }

        @Test
        public void level_1_1() throws Exception {
        }

        @After
        public void after_1_1() {
        }

    }

    //@Context
    public static class Level_1_2 {
        @Before
        public void before_1_2_setup() {

        }

        @Test
        public void level_1_2() throws Exception {
        }

        @After
        public void after_1_2() {
        }

        @BeforeClass
        public static void beforeClass_1_2() {
        }

        @AfterClass
        public static void afterClass_1_2() {
        }


        //@Context
        public class Level_1_2_1 {
            @Before
            public void before_1_2_1() {

            }

            @Test
            public void level_1_2_1() throws Exception {

            }

            @After
            public void after_1_2_1() {
            }

        }
    }
}
