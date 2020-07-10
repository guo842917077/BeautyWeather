package com.crazyorange.beauty.comm.network.exception;

import androidx.lifecycle.Lifecycle;

import java.io.IOException;

public class DisposedException extends IOException {
    private static final long serialVersionUID = 7699927425836702496L;
    /**
     * 保存抛出异常前的最后一次生命周期事件
     */
    public final Lifecycle.Event lastEvent;

    public DisposedException(Lifecycle.Event lastEvent) {
        super(getMessage(lastEvent));
        this.lastEvent = lastEvent;
    }

    public DisposedException(Lifecycle.Event lastEvent, Throwable cause) {
        super(getMessage(lastEvent), cause);
        this.lastEvent = lastEvent;
    }

    private static String getMessage(Lifecycle.Event lastEvent) {
        return "Already disposed, lastEvent is " + lastEvent;
    }

    public static boolean isDestroyed(Throwable t) {
        if (t instanceof DisposedException) {
            return ((DisposedException) t).lastEvent == Lifecycle.Event.ON_DESTROY;
        }
        return false;
    }
}
