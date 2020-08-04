package com.guanlei.springcloud.alibaba.dao;


import com.guanlei.springcloud.alibaba.entry.Order;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface OrderDao {
    //新建订单
    void create(Order order);

    //修改订单状态，从0改为1
    void update (@Param("userId") Long userId,@Param("status") Integer status);
}