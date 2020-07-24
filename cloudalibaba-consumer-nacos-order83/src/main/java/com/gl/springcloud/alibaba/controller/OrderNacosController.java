package com.gl.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;

@RestController
@RequestMapping(value = "/consumer")
public class OrderNacosController {

    @Resource
    private RestTemplate restTemplate;

    @Value("${service-url.nacos-user-service}")
    private String paymentServiceUrl;

    @GetMapping(value = "/payment/nacos/serverPort/{id}")
    public String paymentInfo(@PathVariable Long id){
        return restTemplate.getForObject(paymentServiceUrl + "/payment/nacos/serverPort/"+id, String.class);
    }
}
