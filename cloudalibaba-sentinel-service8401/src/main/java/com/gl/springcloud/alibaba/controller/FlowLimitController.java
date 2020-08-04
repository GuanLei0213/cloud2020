package com.gl.springcloud.alibaba.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.gl.springcloud.alibaba.handler.BlockHandler;
import com.gl.springcloud.alibaba.service.PaymentService;
import com.gl.springcloud.entity.CommonResult;
import com.gl.springcloud.entity.Payment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * 被流量控制的Controller，根据yml的配置，该Controller的所有接口都会被监控到。
 */
@RestController
public class FlowLimitController {

    @GetMapping(value = "/testA")
    public String testA(){
        return "----------testA";
    }

    @GetMapping(value = "/testB")
    public String testB(){
        return "----------testB";
    }

    /**
     *  blockHandler指定了兜底的方法
     */
    @GetMapping("/testHotKey")
    @SentinelResource(value = "testHotKey", blockHandler = "deal_testHotKey")
    public String testHotKey(@RequestParam(value = "p1", required = false) String p1,
                             @RequestParam(value = "p2", required = false) String p2) {
        return "*****testHotKey";
    }

    /**
     * 如果被限流就用这个方法来兜底
     */
    public String deal_testHotKey (String p1, String p2, BlockException exception) {
        return "****deal_testHotKey";
    }

    @GetMapping(value = "/block")
    @SentinelResource(value = "block",blockHandlerClass = BlockHandler.class,
                                        blockHandler = "handlerException2")
    public String blockHandler(){
        return "处理成功......";
    }

    @Resource
    private PaymentService paymentService;
    /**
     * OpenFeign测试
     */
    @GetMapping(value = "/openFeign/test/{id}")
    public CommonResult<Payment> openFeignTest(Long id){
        CommonResult<Payment> result = paymentService.paymentSQL(id);
        return result;
    }
}
