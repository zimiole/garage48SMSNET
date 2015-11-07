package com.staremisto.smsnet.utils;

import android.app.Activity;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.DisplayMetrics;

import com.staremisto.smsnet.R;

public class Utility {

    /**
     * Returns true if the network is available or about to become available.
     *
     * @param c Context used to get the ConnectivityManager
     * @return current state
     */
    static public boolean isNetworkAvailable(Context c) {
        ConnectivityManager cm =
                (ConnectivityManager) c.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        return activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
    }

    /**
     * Determine if the device is a tablet (i.e. it has a large screen).
     *
     * @param context The calling context.
     */
    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    /**
     * Determine if the device is a seven inches tablet.
     *
     * @param activity Default screen activity.
     */
    public static boolean isSevenInchesTablet(Activity activity) {
        return getDiagonalInches(activity) >= 600;
    }

    /**
     * Determine if the device is a ten inches tablet.
     *
     * @param activity Default screen activity.
     */
    public static boolean isTenInchesTablet(Activity activity) {
        return getDiagonalInches(activity) >= 720;
    }

    /**
     * Determine if the device is in landscape mode(i.e. horizontal screen position)
     *
     * @param context The calling context.
     */
    public static boolean isLandscape(Context context) {
        return context.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE;
    }

    public static void lockScreenOrientation(Activity activity) {
        int currentOrientation = activity.getResources().getConfiguration().orientation;
        if (currentOrientation == Configuration.ORIENTATION_LANDSCAPE) {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE);
        } else {
            activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_SENSOR_PORTRAIT);
        }
    }

    public static void unLockScreenOrientation(Activity activity) {
        activity.setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED);
    }

    private static float getDiagonalInches(Activity activity) {
        DisplayMetrics metrics = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metrics);
        return Math.min(metrics.widthPixels / metrics.density, metrics.heightPixels / metrics.density);
    }

    /**
     * Helper method to provide the art resource id according to the weather condition id returned
     * by the OpenWeatherMap call.
     *
     * @param weatherId from OpenWeatherMap API response
     * @return resource id for the corresponding image. -1 if no relation is found.
     */
    public static int getArtResourceForWeatherCondition(String weatherId) {
        switch (weatherId) {
            case "Clear sky":
                return R.drawable.art_clear;
            case "Few clouds":
                return R.drawable.art_light_clouds;
            case "Scattered clouds":
                return R.drawable.art_clouds;
            case "Broken clouds":
                return R.drawable.art_clouds;
            case "Shower rain":
                return R.drawable.art_light_rain;
            case "Rain":
                return R.drawable.art_rain;
            case "Thunderstorm":
                return R.drawable.art_storm;
            case "Snow":
                return R.drawable.art_snow;
            case "Mist":
                return R.drawable.art_fog;
            default:
                return -1;
        }
    }

}
