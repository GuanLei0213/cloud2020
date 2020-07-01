package com.gl.springcloud.service;

import com.gl.springcloud.entity.CommonResult;
import com.gl.springcloud.entity.Payment;
import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

@Component
@FeignClient("CLOUD-PAYMENT-SERVICE")  //表示去注册中心里访问"CLOUD-PAYMENT-SERVICE"这个微服务（的下面这些接口）
public interface PaymentFeignService {


    @GetMapping(value = "/payment/get/{id}")
    public CommonResult<Payment> getPaymentById(@PathVariable("id") Long id);
}
