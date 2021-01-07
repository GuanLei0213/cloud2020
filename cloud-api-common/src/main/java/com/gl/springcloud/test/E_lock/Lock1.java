package com.gl.springcloud.test.E_lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 1.公平锁
 *  是指多个线程按照申请锁的顺序来获取锁，类似排队打饭，有先来后到的顺序
 * 2.非公平锁
 *  是指多个线程获取锁的顺序并不是按照申请锁的顺序，有可能后申请的线程比先申请的线程优先获取锁，在高并发的情况下，
 *  有可能会造成优先级反转或者饥饿现象。
 *
 *  并发包中的ReentrantLock的创建可以指定构造函数的boolean类型来得到公平锁或非公平锁，默认是非公平锁。
 *
 *  非公平锁的优点在于吞吐量比公平锁大。Synchronized也是非公平锁。
 *
 * 3.可重入锁（也叫递归锁）
 *  指的是同一线程外层函数获得锁之后，内层递归函数仍然能获取该锁的代码，在同一个线程最外层方法获取锁的时候，
 *  在进入内层方法会自动获取锁。也就是说，线程可以进入任何一个它已经拥有的锁所同步着的代码块。
 *  可重入锁的作用：防止死锁
 *
 *  以下为可重入锁的测试，输出如下：
 *  t1 invoked sendSMS()
 *  t1 #########invoked sendEmail()
 *  t2 invoked sendSMS()
 *  t2 #########invoked sendEmail()
 */
public class Lock1 {

    public static void main(String[] args) {
        Phone phone = new Phone();
        new Thread(()->{
            phone.sendSMS();
        },"t1").start();

        new Thread(()->{
            phone.sendSMS();
        },"t2").start();
    }
}


/**
 * 验证synchronized是可重入锁
 */
class Phone{

    /**
     * 以下为测试 synchronized 是可重入锁
     */
    public synchronized void sendSMS(){
        System.out.println(Thread.currentThread().getName()+" invoked sendSMS()");
        sendEmail();
    }

    public synchronized void sendEmail(){
        System.out.println(Thread.currentThread().getName()+" #########invoked sendEmail()");
    }

    /**
     * 以下为测试 Lock 是可重入锁
     */
    Lock lock = new ReentrantLock();

    public void sendSMS_lock(){
        try{
            lock.lock();
            System.out.println(Thread.currentThread().getName()+" invoked sendSMS_lock()");
            sendEmail_lock();
        }finally {
            lock.unlock();
        }
    }

    public void sendEmail_lock(){
        System.out.println(Thread.currentThread().getName()+" #########invoked sendEmail_lock()");
    }
}