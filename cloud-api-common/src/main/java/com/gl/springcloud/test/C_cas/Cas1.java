package com.gl.springcloud.test.C_cas;

import java.util.Collections;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 验证：
 * 1.什么是CAS(compare and swap)?
 *   比较并交换的意思,它是一条CPU并发原语，它的功能是判断内存某个位置的值是否为预期值，如果是则更改为新的值，
 *   这个过程是原子的，能够保证原子性。
 *   原语的执行必须是连续的，在执行过程中不允许被中断，也就是说CAS是
 * 2.CAS底层原理
 *   首先CAS能够保证原子性，是因为底层的Unsafe类的作用。Unsafe类是CAS的核心类，由于Java方法无法直接访问底层系统，
 *   需要通过本地（native）方法来访问。Unsafe类相当于一个后门，基于该类可以直接操作特定内存的数据。Unsafe类存在于
 *   sun.misc包中，其内部方法操作可以像C的指针一样直接操作内存，因为JAVA中CAS操作的执行依赖于Unsafe类的方法。
 *   注意：Unsafe类中所有的方法都是native修饰的，也就是说Unsafe类中的方法都直接调用操纵系统底层资源执行相应任务。
 *   Unsafe中有两个重要的属性：valueOffset、value
 *   1）valueOffset表示该变量值在内存中的偏移地址，因为Unsafe就是根据内存偏移地址获取数据的；
 *   2）value用volatile修饰，保证了多线程之间的内存可见性。
 *
 * 3.CAS的缺点
 *  1）我们可以看到getAndAddInt方法执行时，有个do while，也就是如果CAS失败，会一直进行尝试，直到成功。
 *  如果CAS长时间一直不成功，可能会给CPU带来很大的开销。
 *  2）只能保证一个共享变量的原子操作
 *  3）ABA问题
 */
public class Cas1 {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        //期待如果atomicInteger的值等于5，我就更新成2020
        System.out.println(atomicInteger.compareAndSet(5,2020)+" current data:"+atomicInteger.get());
        //期待如果atomicInteger的值等于5，我就更新成2030
        System.out.println(atomicInteger.compareAndSet(5,2030)+" current data:"+atomicInteger.get());
    }

    /**
     * unsafe.getAndAddInt为例，解释比较并交换为何能保证原子性：
     * 假设线程A和线程B两个线程同时执行getAndAddInt操作（分别跑在不同的CPU上）：
     * 1.AtomicInteger里面的value原始值为3，即主内存中AtomicInteger的value值为3，根据JMM模型，线程A和线程B各自
     * 持有一份值为3的value副本分别到各自的工作内存；
     * 2.线程A通过getIntVolatile(var1,var2)拿到value值为3，这时候线程A被挂起；
     * 3.线程B也通过getIntVolatile(var1,var2)拿到value值为3，此时刚好线程B没有被挂起并执行compareAndSwapInt方法
     * 比较内存值也为3，成功修改内存值为4，线程B工作完手工，一切OK；
     * 4.这时候线程A恢复，执行compareAndSwapInt方法比较，发现自己手里的数值3和主内存中数值4不一致，说明该值已经被
     * 其它线程抢先一步修改过了，那A线程本次修改失败，只能重新读取重新来一遍了；
     * 5.线程A重新获取value值，因为变量value被volatile修饰，所以其它线程对它的修改，线程A总是能够看到，线程A继续执行
     * compareAndSwapInt方法进行比较替换，直到成功。
     */
}
