package com.gl.springcloud.service;
import cn.hutool.core.util.IdUtil;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.concurrent.TimeUnit;

@Service
public class PaymentService {

    /******************************************以下为服务降级*********************************************/

    public String paymentInfo_OK(Integer id){
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_OK,id："+id+"\t"+"O(∩_∩)O哈哈~";
    }

    /**
     * @HystrixCommand 注解的fallbackMethod方法的意思是Timeout这个方法访问超时时会自动调用paymentInfo_TimeoutHandler
     * 这个方法。@HystrixProperty这个注解配置了指定线程处理时超过3秒就会访问paymentInfo_TimeoutHandler这个方法
     */
    @HystrixCommand(fallbackMethod = "paymentInfo_TimeoutHandler",commandProperties = {
            @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds",value = "3000")
    })
    public String paymentInfo_Timeout(Integer id){
        int timeNumber = 5;
        try{
            TimeUnit.SECONDS.sleep(timeNumber);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_Timeout,id："+id+"\t"+"O(∩_∩)O哈哈~"+"   耗时(秒)："+timeNumber;
    }

    public String paymentInfo_TimeoutHandler(Integer id){
        return "线程池："+Thread.currentThread().getName()+"   paymentInfo_TimeoutHandler,id："+id+"\t"+"系统繁忙，请稍后再试.....";
    }


    /******************************************以下为服务熔断*********************************************/

    /**
     * 在10秒内请求总数要大于等于10次且错误率超过60%断路器才会打开。
     * 一段时间之后（默认是5秒），这个时候断路器是半开状态，会让其中一个请求进行正常处理，如果成功，断路器会关闭，若失败，断路器会开启。依次循环。
     */
    @HystrixCommand(fallbackMethod = "paymentCircuitBreaker_fallBack",commandProperties = {
            @HystrixProperty(name = "circuitBreaker.enabled",value = "true"),//是否开启断路器
            @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold",value = "10"),//请求总数阈值。请求总次数超过阈值，熔断器将从关闭状态变为开启状态
            @HystrixProperty(name = "circuitBreaker.sleepWindowInMillseconds",value = "10000"),//快照时间窗
            @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage",value = "60"),//错误百分比阈值，失败率达到多少后跳闸，跟请求次数配合使用，表示10次请求里面有60%的失败率，断路器起作用
    })
    public String paymentCircuitBreaker(@PathVariable("id") Integer id){
        if (id < 0){
            throw new RuntimeException("ID 不能为负数！");
        }
        String uuid = IdUtil.simpleUUID();
        return Thread.currentThread().getName()+"\t"+"调用成功，随机ID："+uuid;
    }

    public String paymentCircuitBreaker_fallBack(@PathVariable("id") Integer id){
        return "ID不能为负数，请重新尝试！ID："+id;
    }
}
