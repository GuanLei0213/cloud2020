package com.gl.springcloud.test.E_lock;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 独占锁（写锁）、共享锁（读锁）、互斥锁
 * 独占锁：指该锁一次只能被一个线程所持有。对ReentrantLock和Synchronized而言都是独占锁。
 * 共享锁：指该锁可被多个线程所持有。
 * 对ReentrantReadWriteLock其读锁是共享锁，其写锁是独占锁。
 * 读锁的共享锁可保证并发读是非常高效的，读写、写读，写写的过程是互斥的。
 *
 * 多个线程同时读一个资源类没有任何问题，所以为了满足并发量，读取共享资源应该可以同时进行。
 * 但是，如果有一个线程想去写共享资源，就不应该再有其它线程可以对资源进行读或写。也就是：
 * 读-读能共存、读-写不能共存、写-写不能共存。
 *
 */
public class Lock3 {

    public static void main(String[] args) {

        MyCache myCache = new MyCache();

        //并发写
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.write(temp+"",temp+"");
            },String.valueOf(i)).start();
        }

        //并发读
        for (int i = 1; i <= 10; i++) {
            final int temp = i;
            new Thread(()->{
                myCache.read(temp+"");
            },String.valueOf(i)).start();
        }
    }
}

/**
 * 资源类：模拟分布式缓存，底层其实就是key-value键值对
 * 写操作：要求原子性+独占。也就是整个过程必须是完整的统一体，中间不许被分割，被打断。
 */
class MyCache{

    /**
     * 模拟内存的Map,注意应该保证可见性，只要内存有变化就应该让所有人看见
     */
    private volatile Map<String,Object> cache = new HashMap<>();

    /**
     * 用ReentrantReadWriteLock锁实现读共享，写独占
     */
    private ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    /**
     * 读共享
     */
    public void read(String key){
        lock.readLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " 开始读操作....key:"+cache.get(key));
            cache.get(key);
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println(Thread.currentThread().getName() + " 读操作完成....key:"+cache.get(key));
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.readLock().unlock();
        }
    }

    /**
     * 写独占 所以正常的话打印内容应该成组出现，保证了写独占，也就是保证了原子性，在写的过程中不会有其它线程进来加塞
     */
    public void write(String key,Object obj){
        lock.writeLock().lock();
        try{
            System.out.println(Thread.currentThread().getName() + " 开始写操作....value:"+obj);
            cache.put(key,obj);
            TimeUnit.MILLISECONDS.sleep(300);
            System.out.println(Thread.currentThread().getName() + " 写操作完成....value:"+obj);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.writeLock().unlock();
        }
    }
}
