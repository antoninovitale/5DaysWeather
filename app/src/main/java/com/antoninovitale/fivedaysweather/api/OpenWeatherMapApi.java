package com.antoninovitale.fivedaysweather.api;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by a.vitale on 29/04/2017.
 */
public class OpenWeatherMapApi {
    public static final String OPEN_WEATHER_MAP_API_KEY = ""; //TODO insert your API key

    private static final String BASE_URL = "http://api.openweathermap.org/";

    private static OpenWeatherMapApiService openWeatherMapApiService = null;

    public static OpenWeatherMapApiService getOpenWeatherMapApi() {
        if (openWeatherMapApiService == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(60, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .writeTimeout(60, TimeUnit.SECONDS)
                    .addInterceptor(interceptor).build();

            openWeatherMapApiService = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build().create(OpenWeatherMapApiService.class);
        }
        return openWeatherMapApiService;
    }

}