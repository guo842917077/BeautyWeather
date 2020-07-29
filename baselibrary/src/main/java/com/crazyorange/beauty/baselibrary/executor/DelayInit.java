package com.crazyorange.beauty.baselibrary.executor;

import android.os.Looper;
import android.os.MessageQueue;

import java.util.LinkedList;
import java.util.Queue;

public class DelayInit {
    private Queue<Runnable> mDelayQueue = new LinkedList<>();

    public void add(Runnable task) {
        mDelayQueue.add(task);
    }

    public void start() {
        Looper.myQueue().addIdleHandler(new MessageQueue.IdleHandler() {
            @Override
            public boolean queueIdle() {
                Runnable task = mDelayQueue.poll();
                if (task != null) {
                    task.run();
                }
                return !mDelayQueue.isEmpty();
            }
        });
    }

}
