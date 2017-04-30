package com.antoninovitale.fivedaysweather.util;

import android.content.Context;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;

import com.antoninovitale.fivedaysweather.R;

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by a.vitale on 30/04/2017.
 */

public class AppUtil {
    private static final String TAG = AppUtil.class.getSimpleName();

    public static String getFormattedDate(int unixTimestamp) {
        try {
            Date date = new Date(unixTimestamp * 1000L);
            SimpleDateFormat sdf = new SimpleDateFormat("EEE, MMM d, HH:mm", Locale.getDefault());
            sdf.setTimeZone(TimeZone.getDefault());
            return sdf.format(date);
        } catch (Exception e) {
            DebugLog.log(TAG, "getFormattedDate", e);
        }
        return "";
    }

    public static String getFormattedTemperature(Context context, double value) {
        String units = getUnitsFormat(context);
        String symbol = "metric".equalsIgnoreCase(units) ? Constants.CELSIUS_SYMBOL : Constants
                .FAHRENHEIT_SYMBOL;
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(0);
            nf.setRoundingMode(RoundingMode.HALF_UP);
            return String.format("%s %s", nf.format(value), symbol);
        } catch (Exception e) {
            DebugLog.log(TAG, "getFormattedValue", e);
        }
        return String.format("%s %s", ((int) value), symbol);
    }

    public static String getFormattedSpeed(Context context, double value) {
        String units = getUnitsFormat(context);
        String symbol = "metric".equalsIgnoreCase(units) ? Constants.SPEED_UNIT_METRIC :
                Constants.SPEED_UNIT_IMPERIAL;
        try {
            NumberFormat nf = NumberFormat.getInstance();
            nf.setMaximumFractionDigits(2);
            nf.setRoundingMode(RoundingMode.HALF_UP);
            return String.format("%s %s", nf.format(value), symbol);
        } catch (Exception e) {
            DebugLog.log(TAG, "getFormattedValue", e);
        }
        return String.format(Locale.getDefault(), "%s %s", value, symbol);
    }

    @NonNull
    public static String getUnitsFormat(Context context) {
        boolean useImperialUnits = PreferenceManager.getDefaultSharedPreferences(context)
                .getBoolean("pref_units", false);
        String units = "metric";
        if (useImperialUnits) {
            units = "imperial";
        }
        return units;
    }

    public static String getWeatherIcon(Context context, String icon) {
        int iconRes;
        switch (icon) {
            case "01d":
                iconRes = R.string.wi_day_sunny;
                break;
            case "02d":
                iconRes = R.string.wi_cloudy_gusts;
                break;
            case "03d":
                iconRes = R.string.wi_cloud_down;
                break;
            case "04d":
                iconRes = R.string.wi_cloudy;
                break;
            case "09d":
                iconRes = R.string.wi_day_showers;
                break;
            case "10d":
                iconRes = R.string.wi_day_rain_mix;
                break;
            case "11d":
                iconRes = R.string.wi_day_thunderstorm;
                break;
            case "13d":
                iconRes = R.string.wi_day_snow;
                break;
            case "50d":
                iconRes = R.string.wi_day_fog;
                break;
            case "01n":
                iconRes = R.string.wi_night_clear;
                break;
            case "02n":
                iconRes = R.string.wi_night_cloudy;
                break;
            case "03n":
                iconRes = R.string.wi_night_cloudy_gusts;
                break;
            case "04n":
                iconRes = R.string.wi_night_cloudy;
                break;
            case "09n":
                iconRes = R.string.wi_night_showers;
                break;
            case "10n":
                iconRes = R.string.wi_night_cloudy_gusts;
                break;
            case "11n":
                iconRes = R.string.wi_night_rain;
                break;
            case "13n":
                iconRes = R.string.wi_night_snow;
                break;
            case "50n":
                iconRes = R.string.wi_night_fog;
                break;
            default:
                iconRes = R.string.wi_na;
                break;
        }
        return context.getString(iconRes);
    }

}