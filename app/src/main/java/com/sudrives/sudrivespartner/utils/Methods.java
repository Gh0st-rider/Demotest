package com.sudrives.sudrivespartner.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.location.Address;
import android.location.Geocoder;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.text.format.DateFormat;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

public class Methods {



    public static DisplayMetrics getDeviceResolutions(Context mContext){

        DisplayMetrics displayMetrics = new DisplayMetrics();
        ((Activity)mContext).getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int height = displayMetrics.heightPixels;
        int width = displayMetrics.widthPixels;

        AppPreference.saveStringPref(mContext, AppPreference.KEY_WIDTH , ""+width );
        AppPreference.saveStringPref(mContext, AppPreference.KEY_HEIGHT , ""+height );

        Log.d("getDeviceResolutions", "width: "+width+" height: "+height);
        return displayMetrics;

    }

    public static void changeStatusBarColor(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            ((Activity) context).getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
            Window window = ((Activity) context).getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.WHITE);
        }
    }




    public static String getAddress(Context mContext, double latitude, double longitude) {
        StringBuilder result = new StringBuilder();
        try {
            Geocoder geocoder = new Geocoder(mContext, Locale.getDefault());
            List<Address> addresses = geocoder.getFromLocation(latitude, longitude, 1);
            if (addresses.size() > 0) {
                Address address = addresses.get(0);
                result.append(address.getAddressLine(0));
                //result.append(address.getLocality()).append("\n");
                // result.append(address.getCountryName());

            }
        } catch (Exception e) {
            Log.e("tag", e.getMessage());
        }

        return result.toString();
    }

    public static boolean isVehicleNumber( String number){

//        Log.e("Exception", "isVehicleNumber: "+Pattern.compile("^[a-zA-z]{2}\\s[0-9]{2}\\s[a-zA-z]{2}\\s[0-9]{4}$").matcher(number).matches());

        return Pattern.compile("^[a-zA-z]{2}[0-9]{2}[a-zA-z]{2}[0-9]{4}$").matcher(number).matches();

    }



    public static boolean isVehicleNumberDL( String number){

//        Log.e("Exception", "isVehicleNumber: "+Pattern.compile("^[a-zA-z]{2}\\s[0-9]{2}\\s[a-zA-z]{2}\\s[0-9]{4}$").matcher(number).matches());

        return Pattern.compile("^[a-zA-z]{2}[0-9]{1}[a-zA-z]{3}[0-9]{4}$").matcher(number).matches();

    }

    public static String getTimeStampToDate (String time){

//            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//            cal.setTimeInMillis(Long.parseLong(time));
//          //  String date = DateFormat.format("dd/MM/yyyy hh:mm a", cal).toString();
//        String date = DateFormat.format("dd-MMM-yy, hh:mm a", cal).toString();
        String date="";
        try {
            long l = Long.parseLong(time);

            Date d = new Date((long)l*1000);
            date = DateFormat.format("dd-MMM-yy hh:mm a", d).toString();
            System.out.println(date);



        }catch (Exception e){

            Log.e("Exception", "getTime: "+e.getMessage());
        }



        return date;

    }


    public static String getTimeStampToTime (String time){

//            Calendar cal = Calendar.getInstance(Locale.ENGLISH);
//            cal.setTimeInMillis(Long.parseLong(time));
//          //  String date = DateFormat.format("dd/MM/yyyy hh:mm a", cal).toString();
//        String date = DateFormat.format("dd-MMM-yy, hh:mm a", cal).toString();
        String date="";
        try {
            long l = Long.parseLong(time);

            Date d = new Date((long)l*1000);
            date = DateFormat.format("hh:mm a", d).toString();
            System.out.println(date);



        }catch (Exception e){

            Log.e("Exception", "getTime: "+e.getMessage());
        }



        return date;

    }


    public static boolean isPhoneNumberValid(String number){




        return true;


       // return  Pattern.compile("^[6-9][0-9]{10}$").matcher(number).matches();
    }

    public static boolean isNetworkConnected(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null;
    }


    public static boolean checkNetworkConnectivity(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;
    }


}
