package com.rolex.docker;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author rolex
 * @since 2023/7/8
 */
public class DockerMainTest {
    DockerMain dockerMain = new DockerMain();

    @Test
    public void testTest() throws Exception {
        String result = dockerMain.test();
        Assert.assertEquals("docker test", result);
    }
}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme