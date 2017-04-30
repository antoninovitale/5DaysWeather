package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class CurrentWeather implements Parcelable {

    public final static Creator<CurrentWeather> CREATOR = new Creator<CurrentWeather>() {


        @SuppressWarnings({
                "unchecked"
        })
        public CurrentWeather createFromParcel(Parcel in) {
            CurrentWeather instance = new CurrentWeather();
            instance.coord = ((Coord) in.readValue((Coord.class.getClassLoader())));
            in.readList(instance.weather, (Weather.class.getClassLoader()));
            instance.base = ((String) in.readValue((String.class.getClassLoader())));
            instance.main = ((Main) in.readValue((Main.class.getClassLoader())));
            instance.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
            instance.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
            instance.rain = ((Rain) in.readValue((Rain.class.getClassLoader())));
            instance.dt = ((int) in.readValue((int.class.getClassLoader())));
            instance.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.cod = ((int) in.readValue((int.class.getClassLoader())));
            return instance;
        }

        public CurrentWeather[] newArray(int size) {
            return (new CurrentWeather[size]);
        }

    };

    @SerializedName("coord")
    @Expose
    public Coord coord;

    @SerializedName("weather")
    @Expose
    public List<Weather> weather = null;

    @SerializedName("base")
    @Expose
    public String base;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("rain")
    @Expose
    public Rain rain;

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("cod")
    @Expose
    public int cod;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(coord);
        dest.writeList(weather);
        dest.writeValue(base);
        dest.writeValue(main);
        dest.writeValue(wind);
        dest.writeValue(clouds);
        dest.writeValue(rain);
        dest.writeValue(dt);
        dest.writeValue(sys);
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(cod);
    }

    public int describeContents() {
        return 0;
    }

}
