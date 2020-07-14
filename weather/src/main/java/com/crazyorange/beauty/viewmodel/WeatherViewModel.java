package com.crazyorange.beauty.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;

import com.crazyorange.beauty.api.WeatherAPI;
import com.crazyorange.beauty.comm.config.API;
import com.crazyorange.beauty.comm.config.SignConfig;
import com.crazyorange.beauty.comm.network.RetrofitManager;
import com.crazyorange.beauty.comm.network.call.Call;
import com.crazyorange.beauty.comm.network.call.DefaultCallback;
import com.crazyorange.beauty.comm.network.exception.HttpError;
import com.crazyorange.beauty.comm.network.lifecycle.AndroidLifecycle;
import com.crazyorange.beauty.entity.WeatherEntity;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class WeatherViewModel extends AndroidViewModel {
    private MutableLiveData<List<WeatherEntity.WeatherBean.FutureBean>> mWeatherData;
    private MutableLiveData<String> mErrorMsg;
    public static final String CURRENT_DATE = "CURRENT_DATE";

    public WeatherViewModel(@NonNull Application application) {
        super(application);
        mWeatherData = new MutableLiveData<List<WeatherEntity.WeatherBean.FutureBean>>();
        List<WeatherEntity.WeatherBean.FutureBean> data = new ArrayList<>();
        mWeatherData.setValue(data);
        mErrorMsg = new MutableLiveData<String>();
    }


    public void requestWeatherData(LifecycleOwner owner) {
        WeatherAPI weatherAPI = RetrofitManager.instance()
                .syncRetrofit(API.WEATHER_BASE_URL)
                .create(WeatherAPI.class);
        Call<WeatherEntity> weatherCall = weatherAPI.requestWeather("天津", SignConfig.getApiKey() );
        weatherCall.bindToLifecycle(AndroidLifecycle.createLifecycleProvider(owner), Lifecycle.Event.ON_DESTROY);
        weatherCall.enqueue(new DefaultCallback<WeatherEntity>() {
            @Override
            public void onStart(Call<WeatherEntity> call) {
                super.onStart(call);
            }

            @Override
            public void onError(Call<WeatherEntity> call, HttpError error) {
                super.onError(call, error);
                mErrorMsg.setValue(error.getMessage());
            }

            @Override
            public void onSuccess(Call<WeatherEntity> call, WeatherEntity weatherEntity) {
                super.onSuccess(call, weatherEntity);
                WeatherEntity.WeatherBean.FutureBean bean = adaptCurrentDate(weatherEntity);
                mWeatherData.getValue().add(bean);
                mWeatherData.getValue().addAll(weatherEntity.getResult().getFuture());
                mWeatherData.setValue(mWeatherData.getValue());
            }

            @Override
            public void onCompleted(Call<WeatherEntity> call, Throwable t) {
                super.onCompleted(call, t);

            }
        });
    }

    /**
     * 适配今天的天气信息
     *
     * @param weatherEntity
     * @return
     */
    @NotNull
    private WeatherEntity.WeatherBean.FutureBean adaptCurrentDate(WeatherEntity weatherEntity) {
        WeatherEntity.WeatherBean.RealtimeBean currentWeather = weatherEntity.getResult().getRealtime();
        WeatherEntity.WeatherBean.FutureBean bean = new WeatherEntity.WeatherBean.FutureBean();
        bean.setDate(CURRENT_DATE);
        bean.setTemperature(currentWeather.getTemperature());
        bean.setDirect(currentWeather.getDirect());
        bean.setWeather(currentWeather.getInfo());
        return bean;
    }

    public MutableLiveData<List<WeatherEntity.WeatherBean.FutureBean>> getWeatherData() {
        return mWeatherData;
    }

    public MutableLiveData<String> getErrorMsg() {
        return mErrorMsg;
    }

}
