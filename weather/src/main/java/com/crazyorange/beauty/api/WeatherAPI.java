package com.crazyorange.beauty.api;

import com.crazyorange.beauty.comm.network.call.Call;
import com.crazyorange.beauty.entity.WeatherEntity;

import retrofit2.http.GET;
import retrofit2.http.Query;


public interface WeatherAPI {
    @GET("simpleWeather/query")
    Call<WeatherEntity> requestWeather(@Query("city") String city, @Query("key") String appKey);
}