package com.sudrives.sudrivespartner.models;

public class ContactUsModel {

    public int status;
    public int error_code;
    public int error_line;
    public String message;
    public ResultBean result;

    public static class ResultBean {


        public String site_phone;
        public String site_email;
        public String site_url;
        
    }
}
