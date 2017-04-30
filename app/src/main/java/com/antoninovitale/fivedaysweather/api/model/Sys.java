package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Sys implements Parcelable {

    public final static Parcelable.Creator<Sys> CREATOR = new Creator<Sys>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Sys createFromParcel(Parcel in) {
            Sys instance = new Sys();
            instance.type = ((int) in.readValue((int.class.getClassLoader())));
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.message = ((double) in.readValue((double.class.getClassLoader())));
            instance.country = ((String) in.readValue((String.class.getClassLoader())));
            instance.sunrise = ((int) in.readValue((int.class.getClassLoader())));
            instance.sunset = ((int) in.readValue((int.class.getClassLoader())));
            instance.pod = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public Sys[] newArray(int size) {
            return (new Sys[size]);
        }

    };

    @SerializedName("type")
    @Expose
    public int type;

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("message")
    @Expose
    public double message;

    @SerializedName("country")
    @Expose
    public String country;

    @SerializedName("sunrise")
    @Expose
    public int sunrise;

    @SerializedName("sunset")
    @Expose
    public int sunset;

    @SerializedName("pod")
    @Expose
    public String pod;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(type);
        dest.writeValue(id);
        dest.writeValue(message);
        dest.writeValue(country);
        dest.writeValue(sunrise);
        dest.writeValue(sunset);
        dest.writeValue(pod);
    }

    public int describeContents() {
        return 0;
    }

}