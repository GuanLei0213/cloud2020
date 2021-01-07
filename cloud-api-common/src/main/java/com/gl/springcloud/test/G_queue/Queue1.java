package com.gl.springcloud.test.G_queue;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * 队列：类似排队打饭，先进先出
 * 阻塞队列：
 * 当阻塞队列为空时，从队列中获取元素的操作将会被阻塞；当阻塞队列为满时，往队列里添加元素的操作将会被阻塞。
 * 所谓阻塞，就是在某些情况下线程被挂起，一旦条件满足，被挂起的线程又会自动被唤醒。MQ的底层原理就是阻塞队列。
 *
 * ArrayBlockingQueue:由数组结构组成的有界阻塞队列
 * LinkedBlockingQueue：由数组结构组成的有界（但大小默认值为Integer.MAX_VALUE=21亿，相当于无界了）阻塞队列
 * PrioriBlockingQueue: 支持优先级排序的无界阻塞队列
 * DelayQueue:使用优先级队列实现的延迟无界阻塞队列
 * SynchronousQueue:不存储元素的阻塞队列，也即单个元素的队列（只能存储一个元素）
 * LinkedTransferQueue:由链表结构组成的无界阻塞队列
 * LinkedBlockingDeque:由链表结构组成的双向阻塞队列
 */
public class Queue1 {

    public static void main(String[] args) {

        //List<String> list = new ArrayList<>();
        BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(3);

        //1. add和remove 当队列满或者空是抛出异常
        /*System.out.println(blockingQueue.add("a"));
        System.out.println(blockingQueue.add("b"));
        System.out.println(blockingQueue.add("c"));
//        System.out.println(blockingQueue.add("d"));

        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());
        System.out.println(blockingQueue.remove());*/


        //2.offer和poll 当队列满或者空时分别返回的是false和null
        //注意，offer和poll还有另一个参数就是配置超时时间
        /*System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));
        System.out.println(blockingQueue.offer("a"));

        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());
        System.out.println(blockingQueue.poll());*/

        try {
            //注意offer还可以配置超时时间
            blockingQueue.offer("a",2, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        //3.put和take 当队列满或者空时进行阻塞，直到队列有空地方或队列里有内容才会继续往队列里放或者取
        try {
            blockingQueue.put("a");
            blockingQueue.put("a");
            blockingQueue.put("a");
//            blockingQueue.put("a");

            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
            blockingQueue.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
