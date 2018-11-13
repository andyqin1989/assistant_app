package com.assistant.uaua.framework;

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


    private ThreadPoolExecutor executor;

    private AppExecutor() {
        executor = new ThreadPoolExecutor(
                corePoolSize,
                maximumPoolSize,
                keepAliveTime,
                TimeUnit.SECONDS,
                queue,
                new ThreadPoolExecutor.AbortPolicy());
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
}
