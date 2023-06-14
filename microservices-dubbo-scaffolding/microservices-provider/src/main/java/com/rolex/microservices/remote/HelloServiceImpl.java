package com.rolex.microservices.remote;

import com.rolex.microservices.api.HelloService;
import com.rolex.microservices.entity.User;
import com.rolex.microservices.mapper.UserMapper;
import org.apache.dubbo.config.annotation.DubboService;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <P>
 *
 * </p>
 *
 * @author rolex
 * @since 2021
 */
@DubboService(version = "1.0.0")
public class HelloServiceImpl implements HelloService {
    @Autowired
    UserMapper userMapper;

    @Override
    public String sayHello(Long id) {
        User user = userMapper.selectById(id);
        return "welcome, " + user.toString();
    }
}
