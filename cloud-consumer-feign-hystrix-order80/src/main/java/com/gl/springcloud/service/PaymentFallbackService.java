package com.gl.springcloud.service;

import org.springframework.stereotype.Component;

/**
 * 统一为接口里面得方法做异常处理
 */
@Component //让springboot容器能够扫描到该类
public class PaymentFallbackService implements PaymentHystrixService {
    @Override
    public String paymentInfo_OK(Integer id) {
        return "PaymentFallbackService ---------> paymentInfo_OK";
    }

    @Override
    public String paymentInfo_TimeOut(Integer id) {
        return "PaymentFallbackService ---------> paymentInfo_TimeOut";
    }
}
