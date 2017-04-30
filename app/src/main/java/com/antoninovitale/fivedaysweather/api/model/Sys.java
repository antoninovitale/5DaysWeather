package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys implements Parcelable {

    public final static Creator<Sys> CREATOR = new Creator<Sys>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Sys createFromParcel(Parcel in) {
            Sys instance = new Sys();
            instance.pod = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Sys[] newArray(int size) {
            return (new Sys[size]);
        }

    };

    @SerializedName("pod")
    @Expose
    public String pod;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(pod);
    }

    public int describeContents() {
        return 0;
    }

}
