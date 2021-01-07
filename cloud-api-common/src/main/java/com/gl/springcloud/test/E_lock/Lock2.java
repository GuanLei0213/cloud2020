package com.gl.springcloud.test.E_lock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 自旋锁
 * 是指尝试获取锁的线程不会立即阻塞，而是采用循环的方式去尝试获取锁，这样的好处是减少线程上下文切换的消耗，
 * 缺点是一直循环会消耗CPU
 *
 * 例题：
 * 通过CAS操作完成自旋锁，A线程先进来调用myLock方法自己持有锁5秒钟，B随后进来后发现当前有线程持有锁，不是null，
 * 所以只能通过自旋锁等待，直到A释放锁后B随后抢到。
 *
 */
public class Lock2 {

    AtomicReference<Thread> atomicReference = new AtomicReference<>();

    /**
     * 上锁
     */
    public void myLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+" come in myLock().....");

        //自旋锁配置,循环等待锁被释放
        while (!atomicReference.compareAndSet(null,thread)){

        }

        System.out.println(thread.getName()+" come out myLock().....");
    }

    /**
     * 解锁
     */
    public void  myUnLock(){
        Thread thread = Thread.currentThread();
        System.out.println(thread.getName()+" invoked myUnLock().....");
        atomicReference.compareAndSet(thread,null);
    }


    public static void main(String[] args) {

        Lock2 lock2 = new Lock2();

        new Thread(() -> {
            lock2.myLock();
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock2.myUnLock();
        },"AA").start();

        //主线程修庙1秒，确保AA线程先启动执行
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(() -> {
            lock2.myLock();
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock2.myUnLock();
        },"BB").start();
    }
}
