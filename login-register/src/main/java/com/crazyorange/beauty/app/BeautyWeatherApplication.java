package com.crazyorange.beauty.app;


import android.app.Application;

import com.alibaba.android.arouter.launcher.ARouter;
import com.crazyorange.beauty.BuildConfig;
import com.crazyorange.beauty.baselibrary.sp.SpUtil;

public class BeautyWeatherApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        SpUtil.init(this);
        if (BuildConfig.IsDebug) {
            ARouter.openDebug();
            ARouter.openDebug();
        }
        ARouter.init(this);
    }
}
