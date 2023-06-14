package com.rolex.microservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rolex.microservices.entity.User;
import com.rolex.microservices.mapper.UserMapper;
import com.rolex.microservices.service.IUserService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author rolex
 * @since 2021-06-23
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
