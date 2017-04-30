package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Coord implements Parcelable {

    public final static Creator<Coord> CREATOR = new Creator<Coord>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Coord createFromParcel(Parcel in) {
            Coord instance = new Coord();
            instance.lat = ((double) in.readValue((double.class.getClassLoader())));
            instance.lon = ((double) in.readValue((double.class.getClassLoader())));
            return instance;
        }

        public Coord[] newArray(int size) {
            return (new Coord[size]);
        }

    };

    @SerializedName("lat")
    @Expose
    public double lat;

    @SerializedName("lon")
    @Expose
    public double lon;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(lat);
        dest.writeValue(lon);
    }

    public int describeContents() {
        return 0;
    }

}
