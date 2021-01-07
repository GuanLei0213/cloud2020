package com.gl.springcloud.test.A_volatile;

import java.util.concurrent.TimeUnit;

/**
 * 1.验证volatile关键字的可见性
 * 假设 int number = 0;number变量之前没有添加volatile关键字，则没有可见性;
 * number变量之前添加volatile关键字，则具有可见性。
 */
public class Volatile1 {

    /**
     * 1.TestData对象的num属性没有添加volatile关键字时，AAA线程将其改为60后，主线程不可见。即主线程认为num仍然等于0，
     * 故主线程在死循环着。
     * 2.TestData对象的num属性添加volatile关键字时，AAA线程将其改为60后，主线程可见，故主线程也得到num等于60，
     * 所以主线程进行了输出。
     */
    public static void main(String[] args) {
        TestData data = new TestData();
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + " come in........num"+data.num);
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            data.addTo60();
            System.out.println(Thread.currentThread().getName() + " updated finish........num:"+data.num);
        },"AAA").start();

        while (data.num == 0){

        }

        System.out.println(Thread.currentThread().getName() + " thread........num:"+data.num);
    }
}

class TestData{

    /**
     * 1.未添加volatile关键字
     */
//    public int num = 0;
    /**
     * 2.添加volatile关键字
     */
    public volatile int num = 0;

    public void addTo60(){
        this.num = 60;
    }
}
