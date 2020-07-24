package com.gl.springcloud.alibaba.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/payment/nacos")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @GetMapping(value = "/serverPort/{id}")
    public String getServerPort(@PathVariable Long id){
        return "Nacos register ...... serverPort:" + serverPort + ",id:" + id;
    }
}
