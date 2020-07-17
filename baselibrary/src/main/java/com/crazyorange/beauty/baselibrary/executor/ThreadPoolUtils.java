package com.crazyorange.beauty.baselibrary.executor;

import android.os.Process;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 1.设置线程的优先级 优先值越大优先级越低
 * 2.给线程命名 方便任务的排查
 * 3.控制整个应用线程的总数量
 * 4.各个应用复用线程池
 * @author guojinlong
 */
public class ThreadPoolUtils {
    private final int CORE_POOL_SIZE = Runtime.getRuntime().availableProcessors();

    private final int MAXIMUM_POOL_SIZE = 16;

    private static final int KEEP_ALIVE_SECONDS = 3;

    private static final ThreadFactory IO_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "IO # " + mCount.getAndIncrement());
            Process.setThreadPriority(Process.THREAD_PRIORITY_BACKGROUND);
            return thread;
        }
    };
    private static final ThreadFactory CPU_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "CPU # " + mCount.getAndIncrement());
            Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
            return thread;
        }
    };
    private static final ThreadFactory DEFAULT_THREAD_FACTORY = new ThreadFactory() {
        private final AtomicInteger mCount = new AtomicInteger(1);

        @Override
        public Thread newThread(Runnable r) {
            Thread thread = new Thread(r, "DEFAULT # " + mCount.getAndIncrement());
            Process.setThreadPriority(Process.THREAD_PRIORITY_DEFAULT);
            return thread;
        }
    };
    /**
     * IO 密集型可以提供多一些线程
     */
    public Executor mIoExecutor = new ThreadPoolExecutor(MAXIMUM_POOL_SIZE, MAXIMUM_POOL_SIZE,
            KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), IO_THREAD_FACTORY);
    /**
     * CPU 密集型
     */
    public Executor mCpuExecutor = new ThreadPoolExecutor(CORE_POOL_SIZE, CORE_POOL_SIZE,
            KEEP_ALIVE_SECONDS, TimeUnit.SECONDS, new SynchronousQueue<Runnable>(), CPU_THREAD_FACTORY);
    /**
     * 当不知道是什么任务时
     */
    public ExecutorService mDefaultExecutor = Executors.newFixedThreadPool(3, DEFAULT_THREAD_FACTORY);

    public Executor getIoExecutor() {
        return mIoExecutor;
    }

    public Executor getDefExecutor() {
        return mDefaultExecutor;
    }

    public Executor getCpuExecutor() {
        return mCpuExecutor;
    }

    public void ioExecute(Runnable task) {
        mIoExecutor.execute(task);
    }

    public void cpuExecute(Runnable task) {
        mCpuExecutor.execute(task);
    }

    public void defExecutor(Runnable task) {
        mDefaultExecutor.execute(task);
    }
}
