package com.example.common;

import android.content.Context;
import android.content.SharedPreferences;
import android.widget.TextView;

public class AppPrefsHelper {

    private final SharedPreferences prefs;

    public AppPrefsHelper(Context context, String prefsName) {
        prefs = context.getSharedPreferences(prefsName, Context.MODE_PRIVATE);
    }
    public void putString(String key, String value) {
        prefs.edit().putString(key, value).apply();
    }

    public void putInt(String key, int value) {
        prefs.edit().putInt(key, value).apply();
    }

    public String getString(String key, String defaultValue) {
        return prefs.getString(key, defaultValue);
    }

    public int getInt(String key, int defaultValue) {
        return prefs.getInt(key, defaultValue);
    }

    public void saveSmokingData(String username, int smokedToday, int maxPerDay) {
        putString("username", username);
        putInt("smokedToday", smokedToday);
        putInt("maxPerDay", maxPerDay);
    }

    public void saveWaterData(String username, int stepsAvg, int weight) {
        putString("username", username);
        putInt("stepsAvg", stepsAvg);
        putInt("weight", weight);
    }

    public int resetCounter(TextView counterView) {
        if (counterView != null) {
            counterView.setText("0");
        }
        return 0;
    }
}

