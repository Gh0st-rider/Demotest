package com.sudrives.sudrivespartner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public  class RegistrationTwoModel {


    @Expose
    @SerializedName("result")
    public Result result;
    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("error_line")
    public int error_line;
    @Expose
    @SerializedName("error_code")
    public int error_code;
    @Expose
    @SerializedName("status")
    public int status;

    public static class Result {
        @Expose
        @SerializedName("city_id")
        public String city_id;
        @Expose
        @SerializedName("state_id")
        public String state_id;
        @Expose
        @SerializedName("country_id")
        public String country_id;
        @Expose
        @SerializedName("franchise_id")
        public String franchise_id;
        @Expose
        @SerializedName("documents_verified")
        public String documents_verified;
        @Expose
        @SerializedName("outstation")
        public String outstation;
        @Expose
        @SerializedName("rental")
        public String rental;
        @Expose
        @SerializedName("daily")
        public String daily;
        @Expose
        @SerializedName("update_dt")
        public String update_dt;
        @Expose
        @SerializedName("create_dt")
        public String create_dt;
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("verify_account_status")
        public String verify_account_status;
        @Expose
        @SerializedName("last_login_date")
        public String last_login_date;
        @Expose
        @SerializedName("registration_notification")
        public String registration_notification;
        @Expose
        @SerializedName("login_count")
        public String login_count;
        @Expose
        @SerializedName("activation_code")
        public String activation_code;
        @Expose
        @SerializedName("promocode")
        public String promocode;
        @Expose
        @SerializedName("referral_code")
        public String referral_code;
        @Expose
        @SerializedName("device_type")
        public String device_type;
        @Expose
        @SerializedName("device_token")
        public String device_token;
        @Expose
        @SerializedName("gst_number")
        public String gst_number;
        @Expose
        @SerializedName("usage_for")
        public String usage_for;
        @Expose
        @SerializedName("reff_id")
        public String reff_id;
        @Expose
        @SerializedName("about_us")
        public String about_us;
        @Expose
        @SerializedName("angle")
        public String angle;
        @Expose
        @SerializedName("lang")
        public String lang;
        @Expose
        @SerializedName("lat")
        public String lat;
        @Expose
        @SerializedName("address")
        public String address;
        @Expose
        @SerializedName("permit")
        public String permit;
        @Expose
        @SerializedName("insurance")
        public String insurance;
        @Expose
        @SerializedName("fitness_certificate")
        public String fitness_certificate;
        @Expose
        @SerializedName("rc_number")
        public String rc_number;
        @Expose
        @SerializedName("driving_license")
        public String driving_license;
        @Expose
        @SerializedName("vehicle_types")
        public String vehicle_types;
        @Expose
        @SerializedName("vehicle_number")
        public String vehicle_number;
        @Expose
        @SerializedName("vehicle_name")
        public String vehicle_name;
        @Expose
        @SerializedName("dob")
        public String dob;
        @Expose
        @SerializedName("unique_no")
        public String unique_no;
        @Expose
        @SerializedName("profile_img")
        public String profile_img;
        @Expose
        @SerializedName("password")
        public String password;
        @Expose
        @SerializedName("email")
        public String email;
        @Expose
        @SerializedName("mobile")
        public String mobile;
        @Expose
        @SerializedName("lname")
        public String lname;
        @Expose
        @SerializedName("fname")
        public String fname;
        @Expose
        @SerializedName("trip_status")
        public String trip_status;
        @Expose
        @SerializedName("driver_status")
        public String driver_status;
        @Expose
        @SerializedName("role_id")
        public String role_id;
        @Expose
        @SerializedName("id")
        public String id;
    }
}
