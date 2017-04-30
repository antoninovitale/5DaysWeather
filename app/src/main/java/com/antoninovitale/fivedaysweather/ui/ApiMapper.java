package com.antoninovitale.fivedaysweather.ui;

import android.content.Context;

import com.antoninovitale.fivedaysweather.R;
import com.antoninovitale.fivedaysweather.api.model.CurrentWeather;
import com.antoninovitale.fivedaysweather.api.model.List;
import com.antoninovitale.fivedaysweather.api.model.Weather;
import com.antoninovitale.fivedaysweather.api.model.WeatherForecast;
import com.antoninovitale.fivedaysweather.ui.model.CitySummaryModel;
import com.antoninovitale.fivedaysweather.ui.model.WeatherInfoModel;
import com.antoninovitale.fivedaysweather.util.AppUtil;

import java.util.ArrayList;

/**
 * Created by a.vitale on 30/04/2017.
 */

public class ApiMapper {

    public static CitySummaryModel createCitySummaryModel(Context context, CurrentWeather
            currentWeather) {
        CitySummaryModel citySummaryModel = new CitySummaryModel();
        if (currentWeather.weather != null && !currentWeather.weather.isEmpty()) {
            Weather weather = currentWeather.weather.get(0);
            citySummaryModel.weatherIcon = AppUtil.getWeatherIcon(context, weather.icon);
            citySummaryModel.weatherDescription = String.format("%s\n%s", weather
                    .main, weather.description);
        }
        if (currentWeather.main != null) {
            citySummaryModel.temperature = AppUtil.getFormattedTemperature(context,
                    currentWeather.main.temp);
        }
        citySummaryModel.cityName = currentWeather.name;
        return citySummaryModel;
    }

    public static java.util.List<WeatherInfoModel> createWeatherInfoModels(Context context,
                                                                           WeatherForecast
                                                                                   weatherForecast) {
        java.util.List<WeatherInfoModel> forecast = new ArrayList<>();
        if (weatherForecast.list != null) {
            for (List list : weatherForecast.list) {
                WeatherInfoModel weatherInfoModel = new WeatherInfoModel();
                weatherInfoModel.date = AppUtil.getFormattedDate(list.dt);
                if (list.weather != null && list.weather.size() > 0) {
                    Weather weather = list.weather.get(0);
                    weatherInfoModel.weatherDescription = String.format("%s\n%s", weather.main,
                            weather.description);
                    weatherInfoModel.weatherIcon = AppUtil.getWeatherIcon(context, weather.icon);
                }
                if (list.main != null) {
                    weatherInfoModel.maxTemperature = context.getString(R.string.max_temp,
                            AppUtil.getFormattedTemperature(context, list.main.tempMax));
                    weatherInfoModel.minTemperature = context.getString(R.string.min_temp,
                            AppUtil.getFormattedTemperature(context, list.main.tempMin));
                    weatherInfoModel.humidity = context.getString(R.string.humidity, list.main
                            .humidity);
                }
                if (list.wind != null) {
                    weatherInfoModel.windSpeed = context.getString(R.string.wind_speed, AppUtil
                            .getFormattedSpeed(context, list.wind.speed));
                }
                forecast.add(weatherInfoModel);
            }
        }
        return forecast;
    }

}