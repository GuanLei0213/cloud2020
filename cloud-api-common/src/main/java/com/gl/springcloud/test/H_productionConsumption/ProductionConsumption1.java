package com.gl.springcloud.test.H_productionConsumption;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 模拟传统版生产者消费者模式练习（利用A-G学过的知识）
 * 题目：一个初始值为0的变量，两个线程对其交替操作，一个加1一个减1，循环5轮
 *
 * 1.线程     操作      资源类
 * 2.判断     干活      通知
 * 3.防止虚假换醒机制（使用while循环而不是if循环）
 */
public class ProductionConsumption1 {

    public static void main(String[] args) {

        ShareData shareData = new ShareData();

        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                shareData.increment();
            }
        },"AAA").start();

        new Thread(() -> {
            for (int i = 0; i < 6; i++) {
                shareData.decrement();
            }
        },"BBB").start();
    }
}

/**
 * 资源类
 */
class ShareData{

    private int num = 0;

    private Lock lock = new ReentrantLock();

    private Condition condition = lock.newCondition();

    public void increment(){
        try{
            lock.lock();
            //1.判断是否需要生产
            while (num != 0){
                //等待，不能生产
                condition.await();
            }

            //2.生产
            num++;
            System.out.println(Thread.currentThread().getName()+" 生产后的值为："+num);

            //3.通知换醒
            condition.signalAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }

    public void decrement(){
        try{
            lock.lock();
            //1.判断是否能够消费
            while (num == 0){
                //等待，不能消费
                condition.await();
            }

            //2.消费
            num--;
            System.out.println(Thread.currentThread().getName()+" 消费后的值为："+num);

            //3.通知换醒
            condition.signalAll();
        }catch (Exception e){

        }finally {
            lock.unlock();
        }
    }
}
