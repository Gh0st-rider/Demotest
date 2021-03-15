package com.sudrives.sudrivespartner.models;

public class LoginModel {




    private int status;
    private String error_code;
    private int error_line;
    private String message;
    private String userid;
    private String mobile;


    private ResultBean result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public int getError_line() {
        return error_line;
    }

    public void setError_line(int error_line) {
        this.error_line = error_line;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String user_id;
        private String userid;
        private String role_id;
        private int token;
        private String driver_status;
        private String verify_account_status;
        private String first_name;
        private String last_name;
        private String mobile;
        private String email;
        private String profile_img;
        private String unique_no;
        private String dob;
        private String gender;
        private String vehicle_number;
        private String vehicle_types;
        private String gst_number;
        private String address;
        private String lat;
        private String lang;
        private String device_token;
        private String device_type;
        private String referral_code;
        private String activation_code;
        private String login_count;
        private String registration_notification;
        private String usage_for;
        private String about_us;
        private String status;
        private String create_dt;
        private String update_dt;
        private String driver_vehicle;
        private String driving_license;
        private String rc_number;
        private int trips;
        private String tripid;
        private double rating;
        private double years;
        private int driver_trip_status;
        private String user_donations;

        public String getUser_donations() {
            return user_donations;
        }

        public void setUser_donations(String user_donations) {
            this.user_donations = user_donations;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getRole_id() {
            return role_id;
        }

        public void setRole_id(String role_id) {
            this.role_id = role_id;
        }

        public int getToken() {
            return token;
        }

        public void setToken(int token) {
            this.token = token;
        }

        public String getDriver_status() {
            return driver_status;
        }

        public void setDriver_status(String driver_status) {
            this.driver_status = driver_status;
        }

        public String getVerify_account_status() {
            return verify_account_status;
        }

        public void setVerify_account_status(String verify_account_status) {
            this.verify_account_status = verify_account_status;
        }

        public String getFirst_name() {
            return first_name;
        }

        public void setFirst_name(String first_name) {
            this.first_name = first_name;
        }

        public String getLast_name() {
            return last_name;
        }

        public void setLast_name(String last_name) {
            this.last_name = last_name;
        }

        public String getMobile() {
            return mobile;
        }

        public void setMobile(String mobile) {
            this.mobile = mobile;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getProfile_img() {
            return profile_img;
        }

        public void setProfile_img(String profile_img) {
            this.profile_img = profile_img;
        }

        public String getUnique_no() {
            return unique_no;
        }

        public void setUnique_no(String unique_no) {
            this.unique_no = unique_no;
        }

        public String getDob() {
            return dob;
        }

        public void setDob(String dob) {
            this.dob = dob;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getVehicle_number() {
            return vehicle_number;
        }

        public void setVehicle_number(String vehicle_number) {
            this.vehicle_number = vehicle_number;
        }

        public String getVehicle_types() {
            return vehicle_types;
        }

        public void setVehicle_types(String vehicle_types) {
            this.vehicle_types = vehicle_types;
        }

        public String getGst_number() {
            return gst_number;
        }

        public void setGst_number(String gst_number) {
            this.gst_number = gst_number;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLang() {
            return lang;
        }

        public void setLang(String lang) {
            this.lang = lang;
        }

        public String getDevice_token() {
            return device_token;
        }

        public void setDevice_token(String device_token) {
            this.device_token = device_token;
        }

        public String getDevice_type() {
            return device_type;
        }

        public void setDevice_type(String device_type) {
            this.device_type = device_type;
        }

        public String getReferral_code() {
            return referral_code;
        }

        public void setReferral_code(String referral_code) {
            this.referral_code = referral_code;
        }

        public String getActivation_code() {
            return activation_code;
        }

        public void setActivation_code(String activation_code) {
            this.activation_code = activation_code;
        }

        public String getLogin_count() {
            return login_count;
        }

        public void setLogin_count(String login_count) {
            this.login_count = login_count;
        }

        public String getRegistration_notification() {
            return registration_notification;
        }

        public void setRegistration_notification(String registration_notification) {
            this.registration_notification = registration_notification;
        }

        public String getUsage_for() {
            return usage_for;
        }

        public void setUsage_for(String usage_for) {
            this.usage_for = usage_for;
        }

        public String getAbout_us() {
            return about_us;
        }

        public void setAbout_us(String about_us) {
            this.about_us = about_us;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getCreate_dt() {
            return create_dt;
        }

        public void setCreate_dt(String create_dt) {
            this.create_dt = create_dt;
        }

        public String getUpdate_dt() {
            return update_dt;
        }

        public void setUpdate_dt(String update_dt) {
            this.update_dt = update_dt;
        }

        public String getDriver_vehicle() {
            return driver_vehicle;
        }

        public void setDriver_vehicle(String driver_vehicle) {
            this.driver_vehicle = driver_vehicle;
        }

        public String getDriving_license() {
            return driving_license;
        }

        public void setDriving_license(String driving_license) {
            this.driving_license = driving_license;
        }

        public String getRc_number() {
            return rc_number;
        }

        public void setRc_number(String rc_number) {
            this.rc_number = rc_number;
        }

        public int getTrips() {
            return trips;
        }

        public void setTrips(int trips) {
            this.trips = trips;
        }

        public String getTripid() {
            return tripid;
        }

        public void setTripid(String tripid) {
            this.tripid = tripid;
        }

        public double getRating() {
            return rating;
        }

        public void setRating(double rating) {
            this.rating = rating;
        }

        public double getYears() {
            return years;
        }

        public void setYears(double years) {
            this.years = years;
        }

        public int getDriver_trip_status() {
            return driver_trip_status;
        }

        public void setDriver_trip_status(int driver_trip_status) {
            this.driver_trip_status = driver_trip_status;
        }
    }
}
