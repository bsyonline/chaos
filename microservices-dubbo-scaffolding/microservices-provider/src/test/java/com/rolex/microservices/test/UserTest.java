package com.rolex.microservices.test;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.rolex.microservices.entity.User;
import com.rolex.microservices.service.IUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class UserTest {

    @Autowired
    IUserService userService;

    @Test
    public void add() {
        User user = new User();
        user.setName("Jolie");
        userService.save(user);
    }

    @Test
    public void update() {
        User user = new User();
        LambdaUpdateWrapper<User> updateWrapper = Wrappers.lambdaUpdate();
        updateWrapper.eq(User::getId, 1);
        userService.update(user, updateWrapper);
    }

    @Test
    public void page(){
        Page<User> page = userService.page(new Page<>(0, 3));
        List<User> records = page.getRecords();
        System.out.println(records);
    }
}
