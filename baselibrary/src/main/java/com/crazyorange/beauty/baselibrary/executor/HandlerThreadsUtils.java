package com.crazyorange.beauty.baselibrary.executor;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.util.ArrayMap;

/**
 * 适合线程不断的从任务队列中获取任务去执行
 * 1. 适合图片采集 视频采集等任务
 * 2. 不再使用的 HandlerThread 一定要退出
 */
public class HandlerThreadsUtils {
    private static final ArrayMap<String, HandlerThread> mThreads = new ArrayMap<>();

    public static HandlerThread newHandlerThread(String name) {
        HandlerThread handlerThread;
        synchronized (mThreads) {
            handlerThread = mThreads.get(name);
            if (handlerThread != null && handlerThread.getLooper() == null) {
                mThreads.remove(name);
                handlerThread = null;
            }
            if (handlerThread == null) {
                handlerThread = new HandlerThread(name);
                handlerThread.start();
                mThreads.put(name, handlerThread);
            }
        }
        return handlerThread;
    }

    public final Handler asyncHandler(String name) {
        return new Handler(newHandlerThread(name).getLooper());
    }

    public final void quit(Handler handler) {
        if (handler.getLooper() == Looper.getMainLooper()) {
            return;
        }
        handler.getLooper().quit();
    }
}
