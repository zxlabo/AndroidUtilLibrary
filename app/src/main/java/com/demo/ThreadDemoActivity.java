package com.demo;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.labo.library.R;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/**
 * 多个线程异步执行任务，主线程需要等待它们都完成再继续，有哪些实现方式？
 */
public class ThreadDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thread_demo);
        findViewById(R.id.btn_count_down_latch).setOnClickListener(v -> {
            fun1();

        });
        findViewById(R.id.btn_future).setOnClickListener(v -> {
            fun2();
        });
        findViewById(R.id.btn_wait).setOnClickListener(v -> {
            fun4();
            fun3();
        });
    }

    /**
     * 方案一：CountDownLatch，它的实现类似计数器功能。计数器的初始值是要等待的任务数量。
     * 它提供了await方法和countDown方法。当某个任务执行完成，调用countDown，对count进行减一操作。
     * 当count等于0，就表示所有的任务都执行完成了。调用await()方法的线程会被挂起，它会等待直到count值为0才继续执行。
     * https://www.cnblogs.com/dolphin0520/p/3920397.html
     */
    private void fun1() {
        final CountDownLatch latch = new CountDownLatch(2);

        new Thread() {
            @Override
            public void run() {
                try {
                    printLog("子线程" + Thread.currentThread().getName() + "正在执行");
                    Thread.sleep(10000);
                    printLog("子线程" + Thread.currentThread().getName() + "执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();

        new Thread() {
            @Override
            public void run() {
                try {
                    printLog("子线程" + Thread.currentThread().getName() + "正在执行");
                    Thread.sleep(8000);
                    printLog("子线程" + Thread.currentThread().getName() + "执行完毕");
                    latch.countDown();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
        try {
            printLog("等待2个子线程执行完毕...");
            latch.await();
            printLog("2个子线程已经执行完毕");
            printLog("继续执行主线程");
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * CompletableFuture 可以实现的功能
     * 一、等待多个任务并行执行结束获取结果； thenCombine
     * 二、支持流式计算，多个任务依赖形成计算流。thenApply
     * 我们可以通过调用 CompletableFuture 的 supplyAsync 方法创建 future。
     * 在supplyAsync方法中，我们可以传入自定义线程池执行任务，或者使用默认的线程池。
     * 我们调用future.join()方法，会一直等待，直到有返回结果。
     * 注意：CompletableFuture 要求是SDK版本是24及以上。
     * 文章：https://blog.csdn.net/tongtest/article/details/107549749
     */

    private void fun2() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {

//            CompletableFuture<String> future =new CompletableFuture();
//            new Thread(() -> {
//                try {
//                    Thread.sleep(2000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//                future.complete(Thread.currentThread().getName() + "result");
//            }).start();
//            printLog("等待结果");
//            String result = future.join();
//            printLog(result);


            CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
                printLog("compute test");
                return "test";
            });

            String result = future.join();
            printLog("get result: " + result);
        }


    }

    private void fun3() {
        synchronized (obj) {
            try {
                printLog("等待输出结果");
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
                printLog(e.getMessage());
            }
            printLog("输出结果");
        }
    }

    private Object obj = new Object();

    private void fun4() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                synchronized (obj) {
                    try {
                        printLog("休眠2000");
                        Thread.sleep(2000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        printLog(e.getMessage());
                    }
                    obj.notify();
                }
            }
        }).start();
    }

    private void printLog(String msg) {
        Log.e("多线程demo:" + Thread.currentThread().getName(), msg);
    }
}
