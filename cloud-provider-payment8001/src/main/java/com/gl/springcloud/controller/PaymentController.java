package com.gl.springcloud.controller;

import com.gl.springcloud.entity.CommonResult;
import com.gl.springcloud.entity.Payment;
import com.gl.springcloud.service.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@Slf4j
@RequestMapping("/payment")
public class PaymentController {

    @Value("${server.port}")
    private String serverPort;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private DiscoveryClient discoveryClient;

    @GetMapping(value = "/discovery")
    public Object discovery(){
        //获取Eureka服务注册中心上全部微服务
        List<String> services = discoveryClient.getServices();
        for (String service : services) {
            log.info("service:{}",service);
        }
        //获取某一个微服务名称下面的全部实例
        List<ServiceInstance> instances = discoveryClient.getInstances("CLOUD-PAYMENT-SERVICE");
        for (ServiceInstance instance : instances){
            log.info("serviceId:{},serviceIP:{},servicePort:{},serviceUri:{}",instance.getServiceId(),
                    instance.getHost(),instance.getPort(),instance.getUri());
        }
        return this.discoveryClient;
    }

    @PostMapping(value = "/create")
    public CommonResult create(@RequestBody Payment payment){
        int result = paymentService.create(payment);
        log.info("插入payment结果:{}",result);
        if(result > 0){
            return new CommonResult(true,"插入数据成功",result);
        }else{
            return new CommonResult(false,"插入数据失败",result);
        }
    }

    @GetMapping(value = "/get/{id}")
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
