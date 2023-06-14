package com.rolex.microservices.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.rolex.microservices.entity.Order;
import com.rolex.microservices.mapper.OrderMapper;
import com.rolex.microservices.service.IOrderService;
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
public class OrderServiceImpl extends ServiceImpl<OrderMapper, Order> implements IOrderService {

}
