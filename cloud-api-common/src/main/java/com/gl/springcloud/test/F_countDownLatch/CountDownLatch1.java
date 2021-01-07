package com.gl.springcloud.test.F_countDownLatch;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 倒计时计数器
 * 让一些线程阻塞直到另一些线程完成一些列操作后才被唤醒。
 * CountDownLatch主要有两个方法，当一个或多个线程调用await方法时，调用线程会被阻塞。
 * 其它线程调用countDown方法会将计数器减1（调用countDown方法的线程不会阻塞），当计数器的值
 * 变为零时，因调用await方法被阻塞的线程会被唤醒，继续执行。
 */
public class CountDownLatch1 {

    public static void main(String[] args) {

        CountDownLatch countDownLatch = new CountDownLatch(6);

        for(int i=1;i<=6;i++){
            new Thread(()->{
                System.out.println(Thread.currentThread().getName() + "线程在运行........");
                countDownLatch.countDown();
            },String.valueOf(i)).start();
        }

        try {
            countDownLatch.await();
            System.out.println(Thread.currentThread().getName() + "线程被阻塞了........");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(Thread.currentThread().getName() + "线程继续运行........");

    }
}
