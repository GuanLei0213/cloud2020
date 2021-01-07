package com.gl.springcloud.test.F_countDownLatch;

import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

/**
 * 信号量
 * 信号量主要用于两个目的，一个是用于多个共享资源的互斥使用，另一个用于并发线程数的控制
 * 以下模拟：
 * 停车场有3个车位，只能有3个线程能够抢到车位，其它线程只能在门口等待，
 * 当突然释放一个车位，其它线程才能立刻抢到。
 */
public class Semaphore1 {

    public static void main(String[] args) {

        /**
         * 信号量，这里permits设置成1时就可以当synchronized或lock非公平锁来使用，
         * 也就是同一时刻只能有1个线程能够执行，当该线程把锁释放了其它线程才能抢到。
         * 默认是非公平，也可以指定为公平锁。
         */
        Semaphore semaphore = new Semaphore(3);

        //指定为公平锁
        Semaphore semaphore1 = new Semaphore(3,true);

        for (int i = 1; i <=6 ; i++) {
            new Thread(() -> {
                try {
                    semaphore.acquire();//抢占车位
                    System.out.println(Thread.currentThread().getName()+" 线程抢到了车位...");
                    TimeUnit.SECONDS.sleep(3);
                    System.out.println(Thread.currentThread().getName()+" 线程停车3秒离开了车位...");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    semaphore.release();//离开后最终释放车位
                }
            },String.valueOf(i)).start();
        }
    }
}
