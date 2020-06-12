package com.gl.springcloud.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class ApplicationContextConfig {

    /**
     * 将 RestTemplate Bean交于容器去管理，这样后面才能从容器中获取到RestTemplate这个Bean
     * @LoadBalanced 赋予RestTemplate负载均衡的能力，才能通过微服务名称调用微服务
     */
    @Bean
    @LoadBalanced
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}
