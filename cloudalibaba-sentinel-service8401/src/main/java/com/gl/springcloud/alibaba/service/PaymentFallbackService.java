package com.gl.springcloud.alibaba.service;

import com.gl.springcloud.entity.CommonResult;
import com.gl.springcloud.entity.Payment;
import org.springframework.stereotype.Component;

@Component
public class PaymentFallbackService implements PaymentService {

    @Override
    public CommonResult<Payment> paymentSQL(Long id) {
        return new CommonResult<>(false,"服务被降级，进入到FallBack了.....id为："+id);
    }
}
