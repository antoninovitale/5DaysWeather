package com.antoninovitale.fivedaysweather.ui;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.antoninovitale.fivedaysweather.R;
import com.antoninovitale.fivedaysweather.ui.model.WeatherInfoModel;
import com.github.pwittchen.weathericonview.WeatherIconView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by a.vitale on 30/04/2017.
 */
public class WeatherInfoAdapter extends android.support.v7.widget.RecyclerView.Adapter {
    private List<WeatherInfoModel> forecast;

    public WeatherInfoAdapter() {
        this.forecast = new ArrayList<>();
    }

    public void setItems(List<WeatherInfoModel> forecast) {
        this.forecast = forecast;
        notifyDataSetChanged();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.section_city_forecast_item_layout, parent, false);
        return new CityForecastSectionViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        CityForecastSectionViewHolder viewHolder = (CityForecastSectionViewHolder) holder;
        WeatherInfoModel weatherInfoModel = forecast.get(position);
        viewHolder.ivWeatherIcon.setIconResource(weatherInfoModel.weatherIcon);
        viewHolder.tvDate.setText(weatherInfoModel.date);
        viewHolder.tvMaxTemperature.setText(weatherInfoModel.maxTemperature);
        viewHolder.tvMinTemperature.setText(weatherInfoModel.minTemperature);
        viewHolder.tvWeatherInfo.setText(weatherInfoModel.weatherDescription);
        viewHolder.tvHumidity.setText(weatherInfoModel.humidity);
        viewHolder.tvWind.setText(weatherInfoModel.windSpeed);
    }

    @Override
    public int getItemCount() {
        return forecast != null ? forecast.size() : 0;
    }

    @SuppressWarnings("WeakerAccess")
    class CityForecastSectionViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.ivWeatherIcon)
        WeatherIconView ivWeatherIcon;

        @BindView(R.id.tvDate)
        TextView tvDate;

        @BindView(R.id.tvMaxTemperature)
        TextView tvMaxTemperature;

        @BindView(R.id.tvMinTemperature)
        TextView tvMinTemperature;

        @BindView(R.id.tvWeatherInfo)
        TextView tvWeatherInfo;

        @BindView(R.id.tvWind)
        TextView tvWind;

        @BindView(R.id.tvHumidity)
        TextView tvHumidity;

        public CityForecastSectionViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}