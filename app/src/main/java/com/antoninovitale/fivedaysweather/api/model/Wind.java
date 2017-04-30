package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Wind implements Parcelable {

    public final static Creator<Wind> CREATOR = new Creator<Wind>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Wind createFromParcel(Parcel in) {
            Wind instance = new Wind();
            instance.speed = ((double) in.readValue((double.class.getClassLoader())));
            instance.deg = ((double) in.readValue((double.class.getClassLoader())));
            return instance;
        }

        public Wind[] newArray(int size) {
            return (new Wind[size]);
        }

    };

    @SerializedName("speed")
    @Expose
    public double speed;

    @SerializedName("deg")
    @Expose
    public double deg;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(speed);
        dest.writeValue(deg);
    }

    public int describeContents() {
        return 0;
    }

}
