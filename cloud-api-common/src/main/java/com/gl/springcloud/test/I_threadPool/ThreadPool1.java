package com.gl.springcloud.test.I_threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 *线程池做的工作主要是控制运行的线程的数量，处理过程中将任务放入队列，然后在线程创建后启动这些任务，如果线程数量
 * 超过了最大数量，则超出数量的线程排队等待，等其它线程执行完毕，再从队列中取出任务来执行。
 * 线程池的主要特点为：线程复用、控制最大并发数、管理线程。
 * 第一：降低资源消耗。通过重复利用已创建的线程降低线程创建和销毁造成的消耗；
 * 第二：提高响应速度。当任务到达时，任务可以不需要的等到线程创建就能立即执行；
 * 第三：提高线程的可管理性。线程是稀缺资源，如果无限制的创建，不仅会消耗系统资源，还会降低系统的稳定性，使用线程池
 * 可以进行统一的分配、调优和监控。
 *
 * Java中的线程池是通过Executor框架实现的，该框架中用到了 Executor、Executors、ExecutorService和
 * ThreadPoolExecutor 这几个类。最终线程池的底层就是 ThreadPoolExecutor 这个类。
 *
 * 常用的线程池：
 * Executors.newFixedThreadPool(int)    固定数线程
 * Executors.newSingleThreadExecutor()  一池一线程
 * Executors.newCachedThreadPool()      一池多线程
 * 实际在工作中这三个线程池我们都不会去用它，原因是它们的底层用的LinkedBlockingQueue队列的长度取的是默认值
 * Integer.MAX_VALUE约等于12亿，这样会导致内存溢出。所以我们都是用它们的底层ThreadPoolExecutor(.....)这个方法。
 * 其实这三个线程池底层实现都是new ThreadPoolExecutor(....)，可以点进去看源码：
 * new ThreadPoolExecutor(1, 1,0L, TimeUnit.MILLISECONDS,new LinkedBlockingQueue<Runnable>())
 */
public class ThreadPool1 {

    public static void main(String[] args) {
        //一池5个线程。适合执行长期的任务，性能好很多
        ExecutorService threadPool = Executors.newFixedThreadPool(5);

        //一池1个线程。适合一个任务一个任务执行的场景
        ExecutorService threadPool2 = Executors.newSingleThreadExecutor();

        //一池N个线程(就看线程能不能忙的过来，如果忙的过来可能也就一个，如果忙不过来可能会有很多个)
        //适合很多短期异步的小程序或者负载较轻的服务器
        ExecutorService threadPool3 = Executors.newCachedThreadPool();

        try{
            //模拟只有5个窗口但是有10个人办理业务，发现线程被重复使用了。
            for (int i = 1; i <=10 ; i++) {
                final int person = i;
                threadPool.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+" 办理业务..客户为："+person);
                });
            }
        }finally {
            threadPool.shutdown();
        }
    }
}
