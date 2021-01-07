package com.gl.springcloud.test.B_singleton;

/**
 * 验证单例模式在多线程环境下线程不安全
 * 1.采用DCL双端检索机制，能够保证99%的线程安全，需要在属性前加上volatile关键字禁止指令重排，才能保证线程安全
 * 2.可以使用synchronized关键字，但锁太重，并发多了影响效率
 */
public class Singleton1 {

    private static /*volatile*/ Singleton1 instance = null;

    private Singleton1(){
        System.out.println(Thread.currentThread().getName() + " 我是构造方法Singleton1()");
    }

    /**
     * 1.多线程环境下，单例模式不安全，加上 synchronized 可以解决并发问题，但这个锁太重，影响效率，不建议使用
     */
    public static /*synchronized*/ Singleton1 getInstance(){
        if (instance == null){
            instance = new Singleton1();
        }
        return instance;
    }

    /**
     * 2.利用DCL机制（Double Check Lock双端检索机制）
     * 注意：双端检索机制不一定线程安全，但在属性前增加上volatile会线程安全（禁止指令重排）
     */
    public static Singleton1 getInstance2(){
        if (instance == null){
            synchronized (Singleton1.class){
                if (instance == null){
                    instance = new Singleton1();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        /**
         * 验证单线程情况下只会获取到一个实例
         * 这里比较的是内存地址是否相等
         */
        /*System.out.println(Singleton1.getInstance() == Singleton1.getInstance());
        System.out.println(Singleton1.getInstance() == Singleton1.getInstance());
        System.out.println(Singleton1.getInstance() == Singleton1.getInstance());
        System.out.println(Singleton1.getInstance() == Singleton1.getInstance());*/

        /**
         * 验证多线程情况下，单例模式线程不安全
         * 这里会多次输出：“xxx 我是构造方法Singleton1()”
         */
        for (int i = 1; i <= 20 ; i++) {
            new Thread(() -> {
                Singleton1.getInstance();
            },String.valueOf(i)).start();
        }
    }
}
