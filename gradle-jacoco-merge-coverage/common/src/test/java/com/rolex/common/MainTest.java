package com.rolex.common;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author rolex
 * @since 2023/7/8
 */
public class MainTest {
    Main main = new Main();

    @Test
    public void testTest() throws Exception {
        String result = main.test();
        Assert.assertEquals("common test", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme