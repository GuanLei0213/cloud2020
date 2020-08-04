package com.guanlei.springcloud.alibaba.service;

import com.guanlei.springcloud.alibaba.entry.Order;

public interface OrderService {
    /**
     * 创建订单
     */
    void create(Order order);
}
