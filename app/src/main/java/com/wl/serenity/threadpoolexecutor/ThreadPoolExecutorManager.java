package com.wl.serenity.threadpoolexecutor;

import android.support.annotation.NonNull;
import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.Delayed;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by serenitynanian on 2018/5/10.
 * 线程池管理类
 */

public class ThreadPoolExecutorManager {
    private static final ThreadPoolExecutorManager ourInstance = new ThreadPoolExecutorManager();

    public static ThreadPoolExecutorManager getInstance() {
        return ourInstance;
    }

    private ThreadPoolExecutorManager() {
    }

    public static void main(String[] args){
//        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(3, 3, 5, TimeUnit.SECONDS, new SynchronousQueue<Runnable>());
//        //设置corethread如果是闲置状态的话，超过一定时间会不会销毁
//        threadPoolExecutor.allowCoreThreadTimeOut(false);
//
//        new ThreadFactory() {
//            private final AtomicInteger mCount = new AtomicInteger();
//            @Override
//            public Thread newThread(@NonNull Runnable r) {
//
//                return new Thread(r,"AsyncTask #"+mCount.getAndIncrement());
//            }
//        };
//
//
//        ExecutorService executorService = Executors.newCachedThreadPool();
//
//        ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);
//
//
//        ExecutorService executorService1 = Executors.newSingleThreadExecutor();
//        executorService1.execute(new Runnable() {
//            @Override
//            public void run() {
//
//            }
//        });
//
//        try {
//            executorService1.awaitTermination(5, TimeUnit.SECONDS);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//
//        executorService1.shutdown();
        System.out.println( "------start: ------------");

        ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        //只会执行一次
//        scheduledExecutorService.execute(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println( "------run: ------------");
//            }
//        });

        final AtomicInteger atomicInteger = new AtomicInteger();

        /**
         * 按照固定的频率来执行任务
         */
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                System.out.println("'------Thread---->'"+atomicInteger.getAndIncrement());
            }
        }, 2, 2, TimeUnit.SECONDS);

        /**
         * 相对固定的延迟后，执行任务；
         * 也就是说，它会等待前一个任务执行，前一个任务执行完毕后，会等待固定的延迟后，再执行下次任务
         */
//        scheduledExecutorService.scheduleWithFixedDelay(new Runnable() {
//            @Override
//            public void run() {
//                System.out.println("'------Thread---->'"+atomicInteger.getAndIncrement());
//            }
//        }, 2, 2, TimeUnit.SECONDS);

        scheduledExecutorService.shutdown();
    }



}
