package com.gl.springcloud.test.E_lock;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *Synchronized和Lock有什么区别？
 * 1.原始构成：
 *  Synchronized是Java的一个关键字，属于JVM层面，Lock是Java具体的类，属于API层面;
 * 2.使用方法
 *  Synchronized不需要用户去手动释放锁，当Synchronized代码块执行完后系统会自动让线程释放对锁的占用，
 *  即使代码块有异常也会释放；
 *  Lock则需要用户手动释放锁，若没有手动释放锁就会出现死锁现象。
 * 3.等待是否可中断
 *  Synchronized不可中断，除非抛出异常或者正常运行完成；
 *  Lock可中断，1)设置超时的方式，tryLock(long timeout,TimeUnit unit);2)lockInterruptibly()放代码块中，
 *  调用interrupt方法可中断；
 * 4.加锁是否公平
 *  Synchronized非公平锁
 *  Lock两者都可以，默认非公平锁，可以指定boolean值，true为公平锁，false为非公平锁
 * 5.锁绑定多个条件
 *  Synchronized没有
 *  Lock用来实现分组唤醒需要唤醒的线程们，可以精确唤醒，而不是像Synchronized要么随机唤醒一个线程，
 *  要么全部唤醒
 *
 *  下面演示第5点：
 *  多线程之间按照顺序调用，实现A->B->C三个线程启动，精确唤醒。要求如下：
 *  A打印5次，B打印10次，C打印15次；紧接着A打印5次，B打印10次，C打印15次
 *  循环5轮
 */
public class Lock4 {

    public static void main(String[] args) {
        ShareData shareData = new ShareData();

        for (int i = 1; i <= 5; i++) {
            new  Thread(() -> {
                shareData.print5();
            },"A").start();
        }

        for (int i = 1; i <= 5; i++) {
            new  Thread(() -> {
                shareData.print10();
            },"B").start();
        }

        for (int i = 1; i <= 5; i++) {
            new  Thread(() -> {
                shareData.print15();
            },"C").start();
        }
    }
}

class ShareData{

    private int number = 1;

    private Lock lock = new ReentrantLock();
    //相当于一把锁有3把备用钥匙
    private Condition condition1 = lock.newCondition();
    private Condition condition2 = lock.newCondition();
    private Condition condition3 = lock.newCondition();

    /**
     * 打印5次
     */
    public void print5(){

        lock.lock();

        try{
            //1.判断
            while (number != 1){
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //2.干活
            for (int i = 1; i <= 5; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }

            //3.唤醒
            number = 2;
            condition2.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 打印10次
     */
    public void print10(){

        lock.lock();

        try{
            //1.判断
            while (number != 2){
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //2.干活
            for (int i = 1; i <= 10; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }

            //3.唤醒
            number = 3;
            condition3.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }

    /**
     * 打印15次
     */
    public void print15(){

        lock.lock();

        try{
            //1.判断
            while (number != 3){
                try {
                    condition1.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            //2.干活
            for (int i = 1; i <= 15; i++) {
                System.out.println(Thread.currentThread().getName()+" "+i);
            }

            //3.唤醒
            number = 1;
            condition1.signal();

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            lock.unlock();
        }
    }
}
