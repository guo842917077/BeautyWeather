package com.crazyorange.beauty.comm.network.call;

import androidx.lifecycle.Lifecycle;

import java.util.concurrent.Executor;

import okhttp3.Request;

import com.crazyorange.beauty.comm.network.exception.HttpError;
import com.crazyorange.beauty.comm.network.lifecycle.LifeCall;
import com.crazyorange.beauty.comm.network.lifecycle.LifeCycleProvider;

import retrofit2.HttpException;
import retrofit2.Response;

/**
 * https://github.com/xchengDroid/retrofit-helper
 *
 * @param <T>
 */
public final class RealCall<T> implements Call<T> {
    private final Executor callbackExecutor;
    private final retrofit2.Call<T> delegate;

    public RealCall(Executor callbackExecutor, retrofit2.Call<T> delegate) {
        this.callbackExecutor = callbackExecutor;
        this.delegate = delegate;
    }

    @Override
    public T execute() throws Throwable {
        Response<T> response = delegate.execute();
        T body = response.body();
        if (body != null) {
            return body;
        }
        throw new HttpException(response);
    }

    @Override
    public void enqueue(final Callback<T> callback) {
        callbackExecutor.execute(new Runnable() {
            @Override
            public void run() {
                callback.onStart(RealCall.this);
            }
        });
        delegate.enqueue(new retrofit2.Callback<T>() {
            @Override
            public void onResponse(retrofit2.Call<T> call, Response<T> response) {
                //response.isSuccessful() 不能保证 response.body() != null,反之可以
                if (response.body() != null) {
                    callSuccess(response.body());
                } else {
                    callFailure(new HttpException(response));
                }
            }

            @Override
            public void onFailure(retrofit2.Call<T> call, Throwable t) {
                callFailure(t);
            }

            private void callSuccess(final T body) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        T transformer = callback.transform(RealCall.this, body);
                        //noinspection ConstantConditions

                        callback.onSuccess(RealCall.this, transformer);
                        callback.onCompleted(RealCall.this, null);
                    }
                });
            }

            private void callFailure(final Throwable t) {
                callbackExecutor.execute(new Runnable() {
                    @Override
                    public void run() {
                        HttpError error = callback.parseThrowable(RealCall.this, t);
                        //noinspection ConstantConditions
                        callback.onError(RealCall.this, error);
                        callback.onCompleted(RealCall.this, t);
                    }
                });
            }
        });
    }

    @Override
    public boolean isExecuted() {
        return delegate.isExecuted();
    }

    @Override
    public void cancel() {
        delegate.cancel();
    }

    @Override
    public boolean isCanceled() {
        return delegate.isCanceled();
    }

    @SuppressWarnings("CloneDoesntCallSuperClone") // Performing deep clone.
    @Override
    public Call<T> clone() {
        return new RealCall<>(callbackExecutor, delegate.clone());
    }

    @Override
    public Request request() {
        return delegate.request();
    }

    @Override
    public LifeCall<T> bindToLifecycle(LifeCycleProvider provider, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_ANY) {
            throw new IllegalArgumentException("ON_ANY event is not allowed.");
        }
        return new RealLifeCall<T>(clone(), event, provider);
    }

    @Override
    public LifeCall<T> bindUntilDestroy(LifeCycleProvider provider) {
        return bindToLifecycle(provider, Lifecycle.Event.ON_DESTROY);
    }
}