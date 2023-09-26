package com.rolex.model;

import org.junit.Assert;
import org.junit.Test;

/**
 * @author rolex
 * @since 2023/7/8
 */
public class UserTest {
    User user = new User();

    @Test
    public void testSetId() throws Exception {
        user.setId(Long.valueOf(1));
    }

    @Test
    public void testSetName() throws Exception {
        user.setName("name");
    }

}

//Generated with love by TestMe :) Please report issues and submit feature requests at: http://weirddev.com/forum#!/testme