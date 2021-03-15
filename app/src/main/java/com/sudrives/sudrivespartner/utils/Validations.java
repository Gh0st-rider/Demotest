package com.sudrives.sudrivespartner.utils;

import android.util.Log;

import java.util.regex.Pattern;

public class Validations {


    public static boolean getEmailValid(String email) {
        String emailRegex = "^[a-zA-Z0-9_+&*-]+(?:\\."+
                "[a-zA-Z0-9_+&*-]+)*@" +
                "(?:[a-zA-Z0-9-]+\\.)+[a-z" +
                "A-Z]{2,7}$";

        Pattern pat = Pattern.compile(emailRegex);
        if (email == null)
            return false;
        return pat.matcher(email).matches();
    }


    public static boolean isValidMobile(String phone) {
        Log.e("mobile", "mobile: "+phone.length());
        if (phone.length() == 10) {
            Log.e("mobile", "mobile");
            return true;
        }

        return false;
    }



}
