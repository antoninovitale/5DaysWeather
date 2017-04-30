package com.antoninovitale.fivedaysweather.util;

import android.util.Log;

import com.antoninovitale.fivedaysweather.BuildConfig;

/**
 * Created by a.vitale on 30/04/2017.
 */

public class DebugLog {

    public static void log(String tag, String message, Throwable e) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message, e);
        }
    }

    public static void log(String tag, String message) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, message);
        }
    }
}
