package com.crazyorange.beauty.comm.network.call;

import androidx.lifecycle.Lifecycle;

import com.crazyorange.beauty.comm.network.exception.DisposedException;
import com.crazyorange.beauty.comm.network.exception.HttpError;
import com.crazyorange.beauty.comm.network.lifecycle.LifeCall;
import com.crazyorange.beauty.comm.network.lifecycle.LifeCycleProvider;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 包装 RealLifeCall 进行包装，绑定 LifeCycle
 *
 * @param <T>
 * @author guojinlong
 */
public class RealLifeCall<T> implements LifeCall<T> {
    private final Call<T> delegate;
    private final Lifecycle.Event event;
    private final LifeCycleProvider provider;

    private final AtomicBoolean once = new AtomicBoolean();
    /**
     * 保存最后一次生命周期事件
     */
    private volatile Lifecycle.Event lastEvent;

    RealLifeCall(Call<T> delegate, Lifecycle.Event event, LifeCycleProvider provider) {
        this.delegate = delegate;
        this.event = event;
        this.provider = provider;
        provider.observe(this);
    }

    @Override
    public boolean isDisposed() {
        return once.get();
    }

    @Override
    public void onChanged(Lifecycle.Event event) {
        if (event != Lifecycle.Event.ON_ANY) {
            lastEvent = event;
        }
        if (this.event == event || event == Lifecycle.Event.ON_DESTROY
                || event == Lifecycle.Event.ON_ANY) {
            if (once.compareAndSet(false, true)) {
                delegate.cancel();
            }
        }
    }

    /**
     * 如果执行时发生中断，移除生命周期的监控
     *
     * @return
     * @throws Throwable
     */
    @Override
    public T execute() throws Throwable {
        try {
            boolean isDispose = isDisposed();
            if (isDispose) {
                throw new DisposedException(lastEvent);
            }
            return delegate.execute();
        } catch (Throwable throwable) {
            if (isDisposed() && !(throwable instanceof DisposedException)) {
                throw new DisposedException(lastEvent, throwable);
            }
            throw throwable;
        } finally {
            provider.removeObserver(this);
        }
    }


    @Override
    public void enqueue(final Callback<T> callback) {
        delegate.enqueue(new Callback<T>() {
            @Override
            public void onStart(Call<T> call) {
                boolean isNotDisposed = !isDisposed();
                if (isNotDisposed) {
                    callback.onStart(call);
                }
            }

            @Override
            public HttpError parseThrowable(Call<T> call, Throwable t) {
                boolean isNotDisposed = !isDisposed();
                if (isNotDisposed) {
                    return callback.parseThrowable(call, t);
                }
                return new HttpError("Already disposed", t);
            }

            @Override
            public T transform(Call<T> call, T t) {
                boolean isNotDisposed = !isDisposed();
                if (isNotDisposed) {
                    return callback.transform(call, t);
                }
                return t;
            }

            @Override
            public void onError(Call<T> call, HttpError error) {
                boolean isNotDisposed = !isDisposed();
                if (isNotDisposed) {
                    callback.onError(call, error);
                }
            }

            @Override
            public void onSuccess(Call<T> call, T t) {
                boolean isNotDisposed = !isDisposed();
                if (isNotDisposed) {
                    callback.onSuccess(call, t);
                }
            }

            @Override
            public void onCompleted(Call<T> call, Throwable t) {
                callback.onCompleted(call, isDisposed() ? new DisposedException(lastEvent, t) : t);
                provider.removeObserver(RealLifeCall.this);
            }
        });
    }
}
