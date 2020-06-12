package com.gl.springcloud.service;

import com.gl.springcloud.entity.Payment;

public interface PaymentService {

    int create(Payment payment);

    Payment getPaymentById(long id);
}
