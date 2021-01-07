package com.gl.springcloud.test.D_collection;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * 多线程不安全的集合类测试
 * List、Set、Map
 * 如果想要线程安全，可用如下方式。
 */
public class Collection1 {

    /**
     *  多线程操作线程不安全的集合时会抛出java.util.ConcurrentModificationException异常
     */
    public static void main(String[] args) {
        List<String> list = new ArrayList<>();

        //线程安全的LIST
        List<String> list1 = Collections.synchronizedList(new ArrayList<>());
        List<String> list2 = new CopyOnWriteArrayList();

        //线程安全的SET，注意SET的底层原理是HashMap
        Set<String> set = Collections.synchronizedSet(new HashSet<>());
        Set<String> set1 = new CopyOnWriteArraySet<>();

        //线程安全的MAP，注意学习Map和ConcurrentHashMap底层实现的不同
        Map<String,String> map = Collections.synchronizedMap(new HashMap<>());
        Map<String,String> map1 = new ConcurrentHashMap<>();

        for (int i = 0; i < 30; i++) {
            new Thread(()->{
                list.add(UUID.randomUUID().toString().substring(0,8));
                System.out.println(list);
            },String.valueOf(i)).start();
        }
    }
}
