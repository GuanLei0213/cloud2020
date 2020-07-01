package com.gl.springcloud;

import com.gl.myrule.MySelfRule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;

@SpringBootApplication
@EnableEurekaClient
@RibbonClient(name = "CLOUD-ORDER-SERVICE",configuration = MySelfRule.class) //表示不要用EurekaClient默认出厂的轮询负载均衡规则，用我自定义的负载均衡规则
public class OrderMain80 {

    public static void main(String[] args) {
        SpringApplication.run(OrderMain80.class,args);
    }

    /**
     * 说明：
     * 作为EurekaClient和RibbonClient，实现了Ribbon的客户端负载均衡功能。
     */
}
