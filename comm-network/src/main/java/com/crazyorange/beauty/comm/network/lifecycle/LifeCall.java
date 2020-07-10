package com.crazyorange.beauty.comm.network.lifecycle;

import androidx.lifecycle.Lifecycle;

import com.crazyorange.beauty.comm.network.call.Callable;

public interface LifeCall<T> extends Callable<T>, LifeCycleProvider.Observer {
    /**
     * return true if this call has disposed
     *
     * @return
     */
    boolean isDisposed();

    /**
     * this method maybe called from multiple threads。The method must be thread safe。Calling this
     * method multiple times has no effect
     *
     * @param event
     */
    void onChanged(Lifecycle.Event event);
}
