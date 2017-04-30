package com.antoninovitale.fivedaysweather.api;

import com.antoninovitale.fivedaysweather.api.model.CurrentWeather;
import com.antoninovitale.fivedaysweather.api.model.WeatherForecast;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by a.vitale on 29/04/2017.
 */
@SuppressWarnings("WeakerAccess")
public interface OpenWeatherMapApiService {

    @GET("data/2.5/weather")
    Call<CurrentWeather> getCurrentWeatherForLocation(@Query("lat") String latitude,
                                                      @Query("lon") String longitude,
                                                      @Query("units") String units,
                                                      @Query("appid") String appId);

    @GET("data/2.5/forecast")
    Call<WeatherForecast> getWeatherForecastForLocation(@Query("lat") String latitude,
                                                        @Query("lon") String longitude,
                                                        @Query("cnt") String cnt,
                                                        @Query("units") String units,
                                                        @Query("appid") String appId);

}