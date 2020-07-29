package com.crazyorange.beauty.weather;

import android.content.Context;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;
import androidx.core.view.LayoutInflaterCompat;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;


import com.alibaba.android.arouter.facade.annotation.Route;
import com.crazyorange.beauty.adapter.CardAdapter;
import com.crazyorange.beauty.baselibrary.log.ALog;
import com.crazyorange.beauty.baselibrary.view.cardswipe.SwpieFlingView;
import com.crazyorange.beauty.comm.config.RoutePage;
import com.crazyorange.beauty.entity.WeatherEntity;
import com.crazyorange.beauty.viewmodel.WeatherViewModel;
import com.crazyorange.beauty.weather.databinding.WeatherMainActivityBinding;


import java.util.List;


/**
 * 主页天气界面
 * <p>
 * 使用 AdMob 植入广告
 *
 * @author guojinlong
 */
@Route(path = RoutePage.Weather.MAIN)
public class WeatherActivity extends AppCompatActivity {

    private WeatherViewModel mWeatherModel;
    private WeatherMainActivityBinding mDataBinding;
    private CardAdapter mWeatherAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if (BuildConfig.IsDebug) {
            installCustomerFactory2();
        }
        super.onCreate(savedInstanceState);
        bindContentView();
        bindLifeCycle();
        mWeatherModel = createViewModel();
        initView();
        bindViewModel();
        registerListener();
        mWeatherModel.requestWeatherData(this);
    }

    private void initView() {
        mDataBinding.swipeCard.setIsNeedSwipe(true);
        mWeatherAdapter = new CardAdapter();
        mDataBinding.swipeCard.setAdapter(mWeatherAdapter);
    }

    private void bindViewModel() {
        mWeatherModel.getWeatherData().observe(this, new Observer<List<WeatherEntity.WeatherBean.FutureBean>>() {
            @Override
            public void onChanged(List<WeatherEntity.WeatherBean.FutureBean> futureBeans) {
                if (futureBeans.size() == 0) {
                    return;
                }
                if (mWeatherAdapter != null) {
                    mWeatherAdapter.setData(futureBeans);
                }
            }
        });
        mWeatherModel.getErrorMsg().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {

            }
        });
    }

    private void registerListener() {
        mDataBinding.swipeCard.setFlingListener(new SwpieFlingView.onFlingListener() {
            @Override
            public void removeFirstObjectInAdapter() {
                mWeatherAdapter.remove(0);
            }

            @Override
            public void onLeftCardExit(Object dataObject) {

            }

            @Override
            public void onRightCardExit(Object dataObject) {

            }

            @Override
            public void onAdapterAboutToEmpty(int itemsInAdapter) {
                mWeatherModel.requestWeatherData(WeatherActivity.this);
            }

            @Override
            public void onScroll(float progress, float scrollXProgress) {

            }
        });
    }


    private WeatherViewModel createViewModel() {
        return ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication()).create(WeatherViewModel.class);
    }

    private void bindLifeCycle() {
        mDataBinding.setLifecycleOwner(this);
    }

    private void bindContentView() {
        mDataBinding = DataBindingUtil.setContentView(this, R.layout.weather_main_activity);
    }

    private void asyncBindView() {
        new AsyncLayoutInflater(this).inflate(R.layout.weather_main_card_layout, null, new AsyncLayoutInflater.OnInflateFinishedListener() {

            @Override
            public void onInflateFinished(@NonNull View view, int resid, @Nullable ViewGroup parent) {
                setContentView(view);
                bindLifeCycle();
                mWeatherModel = createViewModel();
                initView();
                bindViewModel();
                registerListener();
                mWeatherModel.requestWeatherData(WeatherActivity.this);
            }
        });
    }

    private void installCustomerFactory2() {
        LayoutInflaterCompat.setFactory2(getLayoutInflater(), new LayoutInflater.Factory2() {
            @Nullable
            @Override
            public View onCreateView(@Nullable View parent, @NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                ALog.d("WeatherActivity", "view name = " + name);
                return getDelegate().createView(parent, name, context, attrs);
            }

            @Nullable
            @Override
            public View onCreateView(@NonNull String name, @NonNull Context context, @NonNull AttributeSet attrs) {
                return null;
            }
        });
    }
}
