package com.gl.springcloud.test.I_threadPool;

import java.util.concurrent.*;

/**
 * 线程池底层的核心就是new ThreadPoolExecutor()这个构造方法，这个构造方法的底层有7分参数，具体意思如下：
 * 1.int corePoolSize ：线程池中的常驻核心线程数，当线程池中工作的线程数达到核心线程数时，就会把到达的任务
 *                      放到任务队列里等待执行；
 * 2.int maximumPoolSize ：线程池能够容纳同时执行的最大线程数，此值必须大于等于1；
 * 3.long keepAliveTime ：多余的空闲线程存活的时间。当前线程池数量超过corePoolSize时，当空闲时间达到keepAliveTime
 *                        值时，多余空闲线程会被销毁直到只剩下corePoolSize个线程为止；
 * 4.TimeUnit unit ：keepAliveTime的单位；
 * 5.BlockingQueue<Runnable> workQueue ：任务队列，被提交但尚未被执行的任务；
 * 6.ThreadFactory threadFactory ：生成线程池中工作线程的线程工厂，用于创建线程，一般用默认的即可；
 * 7.RejectedExecutionHandler handler ：拒绝策略，表示当队列满了并且工作线程大于线程池的最大线程数(maximumPoolSize)
 *                                      时如何来拒绝。
 * 所以线程池能处理的最大线程数其实就是：maximumPoolSize+BlockingQueue能存放任务的个数
 *
 * 综上，线程池的工作原理如下：
 * 1.在创建了线程池后，等待提交过来的任务请求；
 * 2.当调用execute()方法添加一个请求任务时，线程池会做如下判断：
 *  2.1 如果正在运行的线程数量小于corePoolSize，那么马上创建线程运行这个任务；
 *  2.2 如果正在运行的线程数量大于或等于corePoolSize，那么将这个任务放入队列；
 *  2.3 如果这时候队列满了且正在运行的线程数量还小于maximumPoolSize，那么还是要创建非核心线程立刻运行这个任务；
 *  2.4 如果这时候队列满了且正在运行的线程数量大于或等于maximumPoolSize，那么线程池会启动饱和拒绝策略来执行；
 * 3.当一个线程完成任务时，它会从队列中取下一个任务来执行；
 * 4.当一个线程无事可做超过一定的时间（keepAliveTime）时，线程池会判断：如果当前任务的线程数大于corePoolSize
 * 那么这个线程就被停掉。所以线程池的所有任务完成后它最终会收缩到corePoolSize的大小。
 *
 * 那么拒绝策略有哪些？默认的拒绝策略是什么？
 */
public class ThreadPool2 {

    public static void main(String[] args) {

        /**
         * corePoolSize和maxmumPoolSize具体用多大值合适？
         * 具体要看服务器的硬件，看属于CPU密集型还是IO密集型
         * CPU密集型通常 corePoolSize = Runtime.getRuntime().availableProcessors();
         * maxmumPoolSize = Runtime.getRuntime().availableProcessors()+1
         */
        int processors = Runtime.getRuntime().availableProcessors();
        System.out.println("CPU核数为："+processors);

        ExecutorService executorService = new ThreadPoolExecutor(
                2,
                5,
                2L,
                TimeUnit.SECONDS,
                new LinkedBlockingDeque<>(3),
                Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());

        try{
            //模拟10个用户来银行办理业务，每个用户就是来自外部的请求线程
            for (int i = 1; i <= 10; i++) {
                executorService.execute(() -> {
                    System.out.println(Thread.currentThread().getName()+" 办理业务。");
                });
            }
        }catch (Exception e){
            e.printStackTrace();
        }

        executorService.shutdown();//销毁线程
    }
}
