package com.guanlei.springcloud.alibaba.service.impl;

import com.guanlei.springcloud.alibaba.dao.OrderDao;
import com.guanlei.springcloud.alibaba.entry.Order;
import com.guanlei.springcloud.alibaba.service.AccountService;
import com.guanlei.springcloud.alibaba.service.OrderService;
import com.guanlei.springcloud.alibaba.service.StorageService;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
@Slf4j
public class OrderServiceImpl implements OrderService {

    @Resource
    private OrderDao orderDao;
    @Resource
    private StorageService storageService;
    @Resource
    private AccountService accountService;

    /**
     * 下订单->减库存->减余额->改订单状态为已完成
     * 回滚所有异常
     * 标有@GlobalTransactional注解的方法就是TM，事务的发起方
     */
    @Override
    @GlobalTransactional(name = "create-order-gt",rollbackFor = Exception.class)
    public void create(Order order) {
        log.info("开始创建订单..........");
        orderDao.create(order);

        log.info("订单微服务开始调用库存微服务，做库减.............");
        storageService.decrease(order.getProductId(),order.getCount());
        log.info("订单微服务开始调用库存微服务，做库减完成.............");

        log.info("订单微服务开始调用支付微服务，做扣减.............");
        accountService.decrease(order.getUserId(),order.getMoney());
        log.info("订单微服务开始调用支付微服务，做扣减完成.............");

        log.info("修改订单状态开始................");
        orderDao.update(order.getUserId(),0);
        log.info("修改订单状态完成................");
    }
}
