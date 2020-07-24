package com.gl.springcloud.alibaba.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    /**
     * 因为Nacos集成了Ribbon， 所以自带负载均衡和RestTemplate
     */
    @Bean
    @LoadBalanced //一定要加这个注解
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
