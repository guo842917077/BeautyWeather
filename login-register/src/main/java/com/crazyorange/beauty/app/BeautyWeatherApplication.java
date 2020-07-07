package com.crazyorange.beauty.app;


import android.app.Application;

import com.crazyorange.beauty.baselibrary.sp.SpUtil;

public class BeautyWeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtil.init(this);
    }
}
