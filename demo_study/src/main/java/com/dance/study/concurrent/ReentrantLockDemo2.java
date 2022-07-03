package com.dance.study.concurrent;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示 ReentrantLock 可重入锁
 * 注意：获得几次锁，就需要释放几次锁。
 */
public class ReentrantLockDemo2 {

    static class ReentrantLockTask {
        ReentrantLock reentrantLock = new ReentrantLock();

        void buyTicket() {
            String name = Thread.currentThread().getName();
            try {
                reentrantLock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(100);
                System.out.println(name + ":买好了");

                reentrantLock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(100);
                System.out.println(name + ":买好了");

                reentrantLock.lock();
                System.out.println(name + ":准备好了");
                Thread.sleep(100);
                System.out.println(name + ":买好了");
            } catch (InterruptedException e) {
                e.printStackTrace();
                System.out.println(e.getMessage());
            } finally {
                reentrantLock.unlock();
                reentrantLock.unlock();
                reentrantLock.unlock();
            }
        }
    }

    public static void main(String[] args) {
        final ReentrantLockTask task = new ReentrantLockTask();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                task.buyTicket();
            }
        };

        for (int i = 0; i < 10; i++) {
            new Thread(runnable).start();
        }
    }
}
