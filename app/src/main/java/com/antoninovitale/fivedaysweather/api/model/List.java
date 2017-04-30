package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class List implements Parcelable {

    public final static Creator<List> CREATOR = new Creator<List>() {


        @SuppressWarnings({
                "unchecked"
        })
        public List createFromParcel(Parcel in) {
            List instance = new List();
            instance.dt = ((int) in.readValue((int.class.getClassLoader())));
            instance.main = ((Main) in.readValue((Main.class.getClassLoader())));
            in.readList(instance.weather, (com.antoninovitale.fivedaysweather.api.model.Weather
                    .class.getClassLoader()));
            instance.clouds = ((Clouds) in.readValue((Clouds.class.getClassLoader())));
            instance.wind = ((Wind) in.readValue((Wind.class.getClassLoader())));
            instance.snow = ((Snow) in.readValue((Snow.class.getClassLoader())));
            instance.sys = ((Sys) in.readValue((Sys.class.getClassLoader())));
            instance.dtTxt = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public List[] newArray(int size) {
            return (new List[size]);
        }

    };

    @SerializedName("dt")
    @Expose
    public int dt;

    @SerializedName("main")
    @Expose
    public Main main;

    @SerializedName("weather")
    @Expose
    public java.util.List<Weather> weather;

    @SerializedName("clouds")
    @Expose
    public Clouds clouds;

    @SerializedName("wind")
    @Expose
    public Wind wind;

    @SerializedName("snow")
    @Expose
    public Snow snow;

    @SerializedName("sys")
    @Expose
    public Sys sys;

    @SerializedName("dt_txt")
    @Expose
    public String dtTxt;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(dt);
        dest.writeValue(main);
        dest.writeList(weather);
        dest.writeValue(clouds);
        dest.writeValue(wind);
        dest.writeValue(snow);
        dest.writeValue(sys);
        dest.writeValue(dtTxt);
    }

    public int describeContents() {
        return 0;
    }

}
