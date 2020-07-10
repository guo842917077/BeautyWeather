package com.crazyorange.beauty.comm.network;

import com.crazyorange.beauty.comm.network.interceptor.ChangeHostInterceptor;
import com.google.gson.Gson;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author guojinlong
 * <p>
 * Retrofit 的封装以及请求的管理
 */
public class RetrofitManager {

    private static final String TAG = "RetrofitManager";
    /**
     * 负责管理 Retrofit
     * String:baseUrl
     * Retrofit: Retrofit 对象
     */
    public static Map<String, Retrofit> mRetrofits = new ConcurrentHashMap<>();
    private ChangeHostInterceptor mHostInterceptor;

    private RetrofitManager() {
        mHostInterceptor = new ChangeHostInterceptor();
    }

    private static volatile RetrofitManager instance;

    public static RetrofitManager instance() {
        if (instance == null) {
            synchronized (RetrofitManager.class) {
                if (instance == null) {
                    instance = new RetrofitManager();
                }
            }
        }
        return instance;
    }

    public <T> Retrofit syncRetrofit(String url) {
        if (mRetrofits.containsKey(url)) {
            return mRetrofits.get(url);
        }
        OkHttpClient mClient = new OkHttpClient.Builder().addInterceptor(mHostInterceptor)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(new CallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .callFactory(mClient)
                .build();
        mRetrofits.put(url, mRetrofit);
        return mRetrofit;
    }

    /**
     * 异步回调接口,指定 callback 回调的接口
     *
     * @param url          地址
     * @param callExecutor 线程池
     * @param <T>
     * @return
     */
    public <T> Retrofit asyncRetrofit(String url, Executor callExecutor) {
        if (mRetrofits.containsKey(url)) {
            return mRetrofits.get(url);
        }
        OkHttpClient mClient = new OkHttpClient.Builder().addInterceptor(mHostInterceptor)
                .build();
        Retrofit mRetrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addCallAdapterFactory(new CallAdapterFactory())
                .callbackExecutor(callExecutor)
                .callFactory(mClient)
                .build();
        mRetrofits.put(url, mRetrofit);
        return mRetrofit;
    }


    public void setBaseUrl(String baseUrl) {
        this.mHostInterceptor.setBaseUrl(baseUrl);
    }

    public void clear() {
        mRetrofits.clear();
    }
}
