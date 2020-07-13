package com.crazyorange.beauty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.airbnb.lottie.LottieAnimationView;
import com.crazyorange.beauty.entity.WeatherEntity;
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
        } else {
            holder = (CardHolder) convertView.getTag();
        }
        holder.mTvTemperature.setText(mEntitiys.get(position).getTemperature());
        holder.mWeatherView.playAnimation();
        return convertView;
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
    }
}
