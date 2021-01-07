package com.gl.springcloud.test.H_productionConsumption;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 模拟新版生产者消费者模式练习（利用A-G学过的知识）
 */
public class ProductionConsumption2 {

    public static void main(String[] args) {

        MyResource myResource = new MyResource(new ArrayBlockingQueue<String>(10));

        new Thread(() -> {
            System.out.println("生产线程启动.............");
            try {
                myResource.myProducer();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"prod").start();

        new Thread(() -> {
            System.out.println("消费线程启动.............");
            try {
                myResource.myConsumer();
                System.out.println();
                System.out.println();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        },"consumer").start();

        try {
            TimeUnit.SECONDS.sleep(5);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println();
        System.out.println();
        System.out.println();
        System.out.println("5秒钟时间到，大老板Main线程叫停，活动结束！！！");
        myResource.stop();



       /*for (int i = 1; i <= 10; i++) {
            new Thread(() -> {
                try {
                    myResource.myProducer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }


        for (int i = 1; i <= 5; i++) {
            new Thread(() -> {
                try {
                    myResource.myConsumer();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            },String.valueOf(i)).start();
        }*/
    }
}

class MyResource{

    //volatile可见性，值发生变化时对其它线程立刻可见。默认开启，进行生产和消费
    private volatile boolean FLAG = true;

    //能够保证线程安全
    private AtomicInteger atomicInteger = new AtomicInteger();

    //阻塞队列，存储生产的消息
    public BlockingQueue<String> blockingQueue = null;

    //提供构造方法后别人可以new任何一个BlockingQueue
    public MyResource(BlockingQueue<String> blockingQueue) {
        this.blockingQueue = blockingQueue;
        System.out.println(blockingQueue.getClass().getName());
    }

    public void myProducer() throws InterruptedException {
        String data = null;
        while (FLAG){
            data = atomicInteger.incrementAndGet()+"";
            //队列超过2秒还是放不进去的话就会返回false了（满的情况）
            boolean success = blockingQueue.offer(data, 2, TimeUnit.SECONDS);
            if (success){
                System.out.println(Thread.currentThread().getName()+" 生产了蛋糕"+data);
            }else{
                System.out.println(Thread.currentThread().getName()+" 队列已满且超时，不再生产");
            }
            TimeUnit.SECONDS.sleep(1);//1秒钟生产一个蛋糕
        }
        System.out.println(Thread.currentThread().getName()+" 大老板叫停了，FLAG=false，不再生产......");
    }

    public void myConsumer() throws InterruptedException {
        String result = null;
        while (FLAG){
            result = blockingQueue.poll(2, TimeUnit.SECONDS);
            if (result == null || "".equalsIgnoreCase(result)){
                FLAG = false;
                System.out.println(Thread.currentThread().getName()+" 队列为空且超时，不再消费");
                System.out.println();
                System.out.println();
                System.out.println();
                return;
            }else {
                System.out.println(Thread.currentThread().getName()+" 消费了蛋糕"+result);
            }
        }
    }

    public void stop(){
        this.FLAG = false;
    }
}
