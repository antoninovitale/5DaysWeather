package com.antoninovitale.fivedaysweather.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class City implements Parcelable {

    public final static Creator<City> CREATOR = new Creator<City>() {


        @SuppressWarnings({
                "unchecked"
        })
        public City createFromParcel(Parcel in) {
            City instance = new City();
            instance.id = ((int) in.readValue((int.class.getClassLoader())));
            instance.name = ((String) in.readValue((String.class.getClassLoader())));
            instance.coord = ((Coord) in.readValue((Coord.class.getClassLoader())));
            instance.country = ((String) in.readValue((String.class.getClassLoader())));
            return instance;
        }

        public City[] newArray(int size) {
            return (new City[size]);
        }

    };

    @SerializedName("id")
    @Expose
    public int id;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("coord")
    @Expose
    public Coord coord;

    @SerializedName("country")
    @Expose
    public String country;

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
        dest.writeValue(name);
        dest.writeValue(coord);
        dest.writeValue(country);
    }

    public int describeContents() {
        return 0;
    }

}
