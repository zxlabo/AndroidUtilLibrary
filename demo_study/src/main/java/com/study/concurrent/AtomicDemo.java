package com.study.concurrent;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * desc:
 * 1）一个用原子类修饰，一个用 volatile 修饰，在多线程的情况做自增，然后输出最后得值。
 *  原子类的值是正确的，volatile修饰的值是不正确的。
 * 2）这个类的作用是用来测试：原子类和 volatile 关键字的作用。
 *
 * 原子类：通过不加锁的方式，实现线程安全。本质是：在写入数据的时候，通过while循环和CAS,对当前线程的值和主存的值进行比较。
 * 进行数据更新。能够有效避免线程因阻塞-唤醒带来的系统资源开销。但是在并发量大的情况下，加锁的反而比原子类性能跟高。
 *
 * volatile：保证了可见性和有序性，但不能保证原子性。
 *          也就是说：volatile不能保证非原子操作的线程安全。比如 volatileCount++;
 *
 *
 */
public class AtomicDemo {


    public static void main(String[] args) throws InterruptedException {
        final AtomicTask task = new AtomicTask();

        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000; i++) {
                    task.incrementVolatile();
                    task.incrementAtomic();
                }
            }
        };

        Thread t1 = new Thread(runnable);
        Thread t2 = new Thread(runnable);
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println("原子类的结果：" + task.atomicInteger.get());
        System.out.println("volatile修饰的结果：" + task.volatileCount);
    }

    static class AtomicTask {
        AtomicInteger atomicInteger = new AtomicInteger();
        volatile int volatileCount = 0;

        void incrementAtomic() {
            atomicInteger.getAndIncrement();
        }

        void incrementVolatile() {
            volatileCount++;
            //volatileCount = volatileCount + 1;
            //volatileCount = 10000;
        }
    }
}
