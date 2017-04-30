package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WeatherForecast implements Parcelable {

    public final static Creator<WeatherForecast> CREATOR = new Creator<WeatherForecast>() {


        @SuppressWarnings({
                "unchecked"
        })
        public WeatherForecast createFromParcel(Parcel in) {
            WeatherForecast instance = new WeatherForecast();
            instance.cod = ((String) in.readValue((String.class.getClassLoader())));
            instance.message = ((double) in.readValue((double.class.getClassLoader())));
            instance.cnt = ((int) in.readValue((int.class.getClassLoader())));
            in.readList(instance.list, (List.class.getClassLoader()));
            instance.city = ((City) in.readValue((City.class.getClassLoader())));
            return instance;
        }

        public WeatherForecast[] newArray(int size) {
            return (new WeatherForecast[size]);
        }

    };

    @SerializedName("cod")
    @Expose
    public String cod;

    @SerializedName("message")
    @Expose
    public double message;

    @SerializedName("cnt")
    @Expose
    public int cnt;

    @SerializedName("list")
    @Expose
    public java.util.List<List> list;

    @SerializedName("city")
    @Expose
    public City city;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(cod);
        dest.writeValue(message);
        dest.writeValue(cnt);
        dest.writeList(list);
        dest.writeValue(city);
    }

    public int describeContents() {
        return 0;
    }

}
