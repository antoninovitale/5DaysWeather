package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Snow implements Parcelable {

    public final static Creator<Snow> CREATOR = new Creator<Snow>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Snow createFromParcel(Parcel in) {
            Snow instance = new Snow();
            instance._3h = ((double) in.readValue((double.class.getClassLoader())));
            return instance;
        }

        public Snow[] newArray(int size) {
            return (new Snow[size]);
        }

    };

    @SerializedName("3h")
    @Expose
    public double _3h;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(_3h);
    }

    public int describeContents() {
        return 0;
    }

}
