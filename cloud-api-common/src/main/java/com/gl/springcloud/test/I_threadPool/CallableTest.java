package com.gl.springcloud.test.I_threadPool;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

public class CallableTest implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        System.out.println("进入call()方法..........");
        TimeUnit.SECONDS.sleep(2);
        return 1024;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        FutureTask futureTask = new FutureTask(new CallableTest());

        Thread thread = new Thread(futureTask);
        thread.start();

        System.out.println(Thread.currentThread().getName()+"线程执行.....");
        int result0 = 100;

        //thread线程没有计算完时，会等待
        int result1 = Integer.valueOf(futureTask.get().toString());

        System.out.println("最终值为："+(result0+result1));
    }

}
