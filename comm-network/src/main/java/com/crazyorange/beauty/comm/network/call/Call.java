package com.crazyorange.beauty.comm.network.call;

import androidx.lifecycle.Lifecycle;

import com.crazyorange.beauty.comm.network.lifecycle.LifeCall;
import com.crazyorange.beauty.comm.network.lifecycle.LifeCycleProvider;

import okhttp3.Request;

public interface Call<T> extends Callable<T>, Cloneable {

    boolean isExecuted();

    void cancel();

    boolean isCanceled();

    Call<T> clone();

    Request request();

    /**
     * 绑定生命周期
     *
     * @param provider LifecycleProvider
     * @param event    {@link Lifecycle.Event}, {@link Lifecycle.Event#ON_ANY} is not allowed
     * @return LifeCall
     */
    LifeCall<T> bindToLifecycle(LifeCycleProvider provider, Lifecycle.Event event);

    /**
     * default event is {@link Lifecycle.Event#ON_DESTROY}
     *
     * @param provider LifecycleProvider
     * @return LifeCall
     * @see #bindToLifecycle(LifeCycleProvider, Lifecycle.Event)
     */
    LifeCall<T> bindUntilDestroy(LifeCycleProvider provider);
}
