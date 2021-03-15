package com.sudrives.sudrivespartner.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreference {

    public static final String PREFS_NAME = "SU Drives Partner";
    public static final String TOKEN_PREFENCE = "TokenPrefence";

    public static final String KEY_NAME = "Name";
    public static final String KEY_MOBILE = "Mobile";
    public static final String KEY_PROFILE_IMAGE = "Profile_img";
    public static final String KEY_UNIQUE_NUMBER = "unique_no";
    public static final String KEY_TOKEN = "token";
    public static final String KEY_FCM_TOKEN = "fcm_token";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_TRIP_ID = "tripid";

    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WIDTH = "width";
    public static final String KEY_ERROR_CODE = "errorCode";





    public static void saveStringPref(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);
        editor.commit();

    }



    public static void saveBooleanPref(Context context, String key, boolean value ) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putBoolean(key, value);
        editor.commit();

    }





    public static String loadStringPref(Context context, String key) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        String status = settings.getString(key, "");
        return status;

    }






    /*
    * FCM TOKEN PREFENCE Load
    * @Param context
    * @Param key -: KEY
    * */

    public static String loadTokenStringPref(Context context, String key) {
        SharedPreferences settings;
        settings = context.getSharedPreferences(TOKEN_PREFENCE, Context.MODE_PRIVATE);
        String status = settings.getString(key, "");
        return status;

    }


    /*
    * Save FCM TOKEN
    * */

    public static void saveTokenStringPref(Context context, String key, String value) {
        SharedPreferences settings;
        SharedPreferences.Editor editor;
        settings = context.getSharedPreferences(TOKEN_PREFENCE,
                Context.MODE_PRIVATE);
        editor = settings.edit();
        editor.putString(key, value);
        editor.commit();

    }


   /* public static final String PREFS_NAME = "Haulage";
    public static final String TOKEN_PREFENCE = "TokenPrefence";

    public static final String KEY_NAME = "Name";
    public static final String KEY_MOBILE = "Mobile";
    public static final String KEY_PROFILE_IMAGE = "Profile_img";

    public static final String KEY_TOKEN = "token";
    public static final String KEY_FCM_TOKEN = "fcm_token";
    public static final String KEY_USER_ID = "userId";
    public static final String KEY_LANGUAGE = "language";
    public static final String KEY_TRIP_ID = "tripid";

    public static final String KEY_HEIGHT = "height";
    public static final String KEY_WIDTH = "width";*/



    public static void clear(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();

        editor.remove(KEY_NAME);
        editor.remove(KEY_MOBILE);
        editor.remove(KEY_PROFILE_IMAGE);
        editor.remove(KEY_TOKEN);
        editor.remove(KEY_FCM_TOKEN);
        editor.remove(KEY_USER_ID);
        editor.remove(KEY_TRIP_ID);
        editor.remove(KEY_HEIGHT);
        editor.remove(KEY_WIDTH);
        editor.remove(KEY_UNIQUE_NUMBER);
        //editor.clear();
        editor.commit();

        //saveStringPref(context, KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);

    }


    public static void clearAll(Context context) {
        SharedPreferences.Editor editor = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.clear();
        editor.commit();

        saveStringPref(context, KEY_LANGUAGE, AppConstants.KEY_VALUE_HINDI);

    }



}
