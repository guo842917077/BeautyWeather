package com.crazyorange.beauty.comm.network.call;


import com.crazyorange.beauty.comm.network.call.Callback;

public interface Callable<T> {
    /**
     * synchronously send the request and return its response body
     *
     * @return
     */
    T execute() throws Throwable;

    /**
     * Asynchronously send the request
     *
     * @param callback
     */
    void enqueue(Callback<T> callback);
}
