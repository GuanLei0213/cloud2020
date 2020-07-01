package com.gl.springcloud.controller;

import com.gl.springcloud.entity.CommonResult;
import com.gl.springcloud.entity.Payment;
import com.gl.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;


@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;
    @Autowired
    private PaymentService paymentService;

    @PostMapping("/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入payment结果:{}",result);
        if(result > 0){
            return new CommonResult(true,"插入数据成功",result);
        }else{
            return new CommonResult(false,"插入数据失败",result);
        }
    }

    @GetMapping("/get/{id}")
    public CommonResult getPaymentById(@PathVariable("id") Long id){
        Payment payment = paymentService.getPaymentById(id);
        log.info("查询payment结果:{},serverPort:{}",payment,serverPort);
        if(payment != null){
            return new CommonResult(true,"查询数据成功,serverPort:"+serverPort,payment);
        }else{
            return new CommonResult(false,"没有查询到对应记录",null);
        }
    }

    @GetMapping(value = "/lb")
    public String getPaymentByLB(){
        return serverPort;
    }

}
