package com.example.liverpooldemo.utils;

import android.app.Activity;
import android.util.DisplayMetrics;

public class MyUtilsJava {

    public static String APP_NAME = "LIVERPOOL";

    public static float convertDPToPixels(int dp, Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        float logicalDensity = metrics.density;
        return dp * logicalDensity;
    }
}
