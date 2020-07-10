package com.crazyorange.beauty.comm.network.interceptor;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ChangeHostInterceptor implements Interceptor {
    private String mNewBaseUrl = "";

    public void setBaseUrl(String baseUrl) {
        this.mNewBaseUrl = baseUrl;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        boolean isNeedChange = !mNewBaseUrl.equals("") && !request.url().host().equals(mNewBaseUrl);
        if (isNeedChange) {
            HttpUrl newUrl = request.url().newBuilder().host(mNewBaseUrl).build();
            request = request.newBuilder().url(newUrl).build();
        }

        return chain.proceed(request);
    }
}
