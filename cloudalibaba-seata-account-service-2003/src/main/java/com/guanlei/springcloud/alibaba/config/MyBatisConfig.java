package com.guanlei.springcloud.alibaba.config;


import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan({"com.guanlei.springcloud.alibaba.dao"})
public class MyBatisConfig {
}

