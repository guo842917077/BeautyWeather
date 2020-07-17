package com.crazyorange.beauty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.asynclayoutinflater.view.AsyncLayoutInflater;

import com.airbnb.lottie.LottieAnimationView;
import com.crazyorange.beauty.entity.WeatherEntity;
import com.crazyorange.beauty.viewmodel.WeatherViewModel;
import com.crazyorange.beauty.weather.R;

import java.util.List;

public class CardAdapter extends BaseAdapter {
    private List<WeatherEntity.WeatherBean.FutureBean> mEntitiys;

    public void setData(List<WeatherEntity.WeatherBean.FutureBean> entitys) {
        mEntitiys = entitys;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mEntitiys == null ? 0 : mEntitiys.size();
    }

    @Override
    public WeatherEntity.WeatherBean.FutureBean getItem(int position) {
        if (mEntitiys != null && mEntitiys.size() > 0) {
            return mEntitiys.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        CardHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_main_card_layout, parent, false);
            holder = new CardHolder();
            convertView.setTag(holder);
            holder.mTvTemperature = convertView.findViewById(R.id.tv_temperature);
            holder.mWeatherView = convertView.findViewById(R.id.img_weather);
            holder.mTvDirect = convertView.findViewById(R.id.tv_direct);
            holder.mTvWeather = convertView.findViewById(R.id.tv_climate);
            holder.mTvDate = convertView.findViewById(R.id.tv_date);
        } else {
            holder = (CardHolder) convertView.getTag();
        }
        WeatherEntity.WeatherBean.FutureBean bean = mEntitiys.get(position);

        holder.mTvDirect.setText(bean.getDirect());
        holder.mTvWeather.setText(bean.getWeather());
        if (bean.getDate().equals(WeatherViewModel.CURRENT_DATE)) {
            holder.mTvDate.setText("今天");
            holder.mTvTemperature.setText(bean.getTemperature() + "℃");
        } else {
            holder.mTvDate.setText(bean.getDate());
            holder.mTvTemperature.setText(bean.getTemperature());
        }
        String climate = bean.getWeather();
        changeWeatherImg(holder.mWeatherView, climate);

        return convertView;
    }

    private void changeWeatherImg(LottieAnimationView mWeatherView, String climate) {
        if (climate.contains("雨")) {
            mWeatherView.setAnimation(R.raw.weather_img_rain);
        } else if (climate.contains("阴") || climate.contains("云")) {
            mWeatherView.setAnimation(R.raw.weather_raw_cloud);
        } else {
            mWeatherView.setAnimation(R.raw.weather_raw_sun);
        }
        mWeatherView.playAnimation();
    }

    public void remove(int index) {
        if (index > -1 && index < mEntitiys.size()) {
            mEntitiys.remove(index);
        }
        notifyDataSetChanged();
    }

    private static class CardHolder {
        TextView mTvTemperature;
        LottieAnimationView mWeatherView;
        TextView mTvDirect;
        TextView mTvWeather;
        TextView mTvDate;
    }
}
