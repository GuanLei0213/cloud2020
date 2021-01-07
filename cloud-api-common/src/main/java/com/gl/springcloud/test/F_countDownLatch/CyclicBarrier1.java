package com.gl.springcloud.test.F_countDownLatch;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 * 和CountDownLatch的作用正好相反，CyclicBarrier是做加法，
 * 各个子线程没有运行完成之前，主线程被阻塞。
 */
public class CyclicBarrier1 {

    public static void main(String[] args) {
        CyclicBarrier cyclicBarrier = new CyclicBarrier(6,
                () -> {
                    System.out.println("******都运行完了该我运行了.......");
                });

        for (int i = 1; i <= 6 ; i++) {
            new Thread(() -> {
                System.out.println(Thread.currentThread().getName()+" 运行完成....");
                try {
                    cyclicBarrier.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (BrokenBarrierException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }
    }
}
