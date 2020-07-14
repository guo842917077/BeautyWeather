package com.crazyorange.beauty.comm.network.call;

import com.crazyorange.beauty.comm.network.exception.HttpError;

public class DefaultCallback<T> implements Callback<T> {
    @Override
    public void onStart(Call<T> call) {

    }

    @Override
    public HttpError parseThrowable(Call<T> call, Throwable t) {
        return new HttpError(t.getMessage(), t);
    }

    @Override
    public T transform(Call<T> call, T t) {
        return t;
    }

    @Override
    public void onError(Call<T> call, HttpError error) {

    }

    @Override
    public void onSuccess(Call<T> call, T t) {

    }

    @Override
    public void onCompleted(Call<T> call, Throwable t) {

    }
}
