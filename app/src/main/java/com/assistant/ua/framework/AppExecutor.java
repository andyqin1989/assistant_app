package com.assistant.ua.framework;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created by qinbaoyuan on 2018/11/13
 * 自定义线程池
 */
public class AppExecutor {
    private static class SingletonHolder {
        private static AppExecutor holder = new AppExecutor();
    }

    public static AppExecutor getInstance() {
        return SingletonHolder.holder;
    }

    private int corePoolSize = 5;
    private int maximumPoolSize = 5;
    private int keepAliveTime = 300;
    private LinkedBlockingQueue<Runnable> queue = new LinkedBlockingQueue<>(128);
    private LinkedBlockingQueue<Runnable> singleQueue = new LinkedBlockingQueue<>(128);

    private ThreadPoolExecutor singleExecutor;


    private ThreadPoolExecutor executor;

    private AppExecutor() {
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                queue,
                new ThreadPoolExecutor.AbortPolicy());

        singleExecutor = new ThreadPoolExecutor(
                1,
                1,
                keepAliveTime,
                TimeUnit.SECONDS,
                singleQueue,
                new ThreadPoolExecutor.AbortPolicy());
        singleExecutor.allowCoreThreadTimeOut(true);
    }

    /**
     * 执行任务
     */
    public void execute(Runnable runnable) {
        executor.execute(runnable);
    }

    /**
     * 从线程池中删除任务
     */
    public void remove(Runnable runnable) {
        executor.remove(runnable);
    }

    /**
     * 使用单线程执模式行io操作的任务
     */
    public void executeIoTask(Runnable runnable) {
        singleExecutor.execute(runnable);
    }
}
