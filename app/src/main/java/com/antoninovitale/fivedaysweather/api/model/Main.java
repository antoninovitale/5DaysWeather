package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Main implements Parcelable {

    public final static Creator<Main> CREATOR = new Creator<Main>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Main createFromParcel(Parcel in) {
            Main instance = new Main();
            instance.temp = ((double) in.readValue((double.class.getClassLoader())));
            instance.tempMin = ((double) in.readValue((double.class.getClassLoader())));
            instance.tempMax = ((double) in.readValue((double.class.getClassLoader())));
            instance.pressure = ((double) in.readValue((double.class.getClassLoader())));
            instance.seaLevel = ((double) in.readValue((double.class.getClassLoader())));
            instance.grndLevel = ((double) in.readValue((double.class.getClassLoader())));
            instance.humidity = ((int) in.readValue((int.class.getClassLoader())));
            instance.tempKf = ((double) in.readValue((double.class.getClassLoader())));
            return instance;
        }

        public Main[] newArray(int size) {
            return (new Main[size]);
        }

    };

    @SerializedName("temp")
    @Expose
    public double temp;

    @SerializedName("temp_min")
    @Expose
    public double tempMin;

    @SerializedName("temp_max")
    @Expose
    public double tempMax;

    @SerializedName("pressure")
    @Expose
    public double pressure;

    @SerializedName("sea_level")
    @Expose
    public double seaLevel;

    @SerializedName("grnd_level")
    @Expose
    public double grndLevel;

    @SerializedName("humidity")
    @Expose
    public int humidity;

    @SerializedName("temp_kf")
    @Expose
    public double tempKf;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(temp);
        dest.writeValue(tempMin);
        dest.writeValue(tempMax);
        dest.writeValue(pressure);
        dest.writeValue(seaLevel);
        dest.writeValue(grndLevel);
        dest.writeValue(humidity);
        dest.writeValue(tempKf);
    }

    public int describeContents() {
        return 0;
    }

}
