package com.gl.springcloud.test.A_volatile;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 2.验证volatile不具备原子性
 */
public class Volatile2 {

    /**
     * 1.启动20个线程
     * 2.每个线程执行从1加1000
     * 3.若num属性具备原子性，则num最终的结果应为20000，否则结果不等于20000
     */
    public static void main(String[] args) {
        TestData2 data = new TestData2();
        for (int i = 1; i <= 20; i++) {
            new Thread(() -> {
                for (int j = 1; j <= 1000; j++) {
                    data.add();
                }
            },String.valueOf(i)).start();
        }

        //等待上面的20个线程都计算完后，主线程再执行，看看最终的num的数值
        while (Thread.activeCount() > 2){
            Thread.yield();
        }

        System.out.println("线程名："+Thread.currentThread().getName()+",最终的num值为："+data.num);
    }
}

class TestData2{
    public volatile int num = 0;

    /**
     * 该方法在多线程访问的情况下，肯定不具备原子性
     * 加上synchronized关键字后具备原子性，但加上synchronized关键字后大材小用了，这个锁比较重量级，不建议这么用
     */
    public /*synchronized*/ void add(){
        this.num++;
    }

    /**
     * 使用原子定义来保证原子性
     */
    AtomicInteger atomicInteger = new AtomicInteger();

    public void addAtomic(){
        atomicInteger.getAndIncrement();
    }
}
