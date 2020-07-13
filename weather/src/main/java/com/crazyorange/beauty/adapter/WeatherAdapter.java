package com.crazyorange.beauty.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.crazyorange.beauty.entity.WeatherEntity;
import com.crazyorange.beauty.weather.R;

import java.util.List;

public class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.WeatherHolder> {
    public List<WeatherEntity.WeatherBean.FutureBean> mWeatherEntity;

    public void setData(List<WeatherEntity.WeatherBean.FutureBean> entity) {
        this.mWeatherEntity = entity;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public WeatherHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.weather_main_card_layout, parent, false);
        return new WeatherHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull WeatherHolder holder, int position) {
        WeatherEntity.WeatherBean.FutureBean mData = mWeatherEntity.get(position);
        holder.mTvTemperature.setText(mData.getTemperature());
        holder.mWeatherView.playAnimation();

    }

    @Override
    public int getItemCount() {
        return mWeatherEntity == null ? 0 : mWeatherEntity.size();
    }

    class WeatherHolder extends RecyclerView.ViewHolder {
        TextView mTvTemperature;
        LottieAnimationView mWeatherView;

        public WeatherHolder(@NonNull View itemView) {
            super(itemView);
            mTvTemperature = itemView.findViewById(R.id.tv_temperature);
            mWeatherView = itemView.findViewById(R.id.img_climate);
        }
    }
}
