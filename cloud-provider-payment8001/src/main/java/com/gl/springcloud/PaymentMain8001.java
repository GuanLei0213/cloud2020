package com.gl.springcloud;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;

@SpringBootApplication
@EnableEurekaClient //都是将服务注册进服务注册中心。范围单一，只能注册进Eureka
@EnableDiscoveryClient //都是将服务注册进服务注册中心。范围广，可以注册进Eureka、zookeeper、consul
public class PaymentMain8001 {

    public static void main(String[] args) {
        SpringApplication.run(PaymentMain8001.class, args);
    }
}
