package com.crazyorange.beauty.comm.network.call;

import com.crazyorange.beauty.comm.network.exception.HttpError;
import com.crazyorange.beauty.comm.network.lifecycle.LifeCall;

public interface Callback<T> {
    /**
     * @param call The {@code Call} that was started
     */
    void onStart(Call<T> call);

    /**
     * @param call The {@code Call} that has thrown exception
     * @param t    统一解析throwable对象转换为HttpError对象，如果throwable为{@link HttpError}
     *             <li>则为{@link retrofit2.Converter#convert(Object)}内抛出的异常</li>
     *             如果为{@link retrofit2.HttpException}
     *             <li>则为{@link Response)}为null的时候抛出的</li>
     */
    HttpError parseThrowable(Call<T> call, Throwable t);

    /**
     * 过滤一次数据,如剔除List中的null等,默认可以返回t
     */
    T transform(Call<T> call, T t);

    void onError(Call<T> call, HttpError error);

    void onSuccess(Call<T> call, T t);

    /**
     * @param t 请求失败的错误信息，如果是 {@link com.crazyorange.beauty.comm.network.exception.DisposedException}代表请求被生命周期事件取消了，
     *          {@link LifeCall#isDisposed()} 返回true
     */
    void onCompleted(Call<T> call,Throwable t);
}
