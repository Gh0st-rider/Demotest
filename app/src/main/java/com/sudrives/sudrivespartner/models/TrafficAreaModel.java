package com.sudrives.sudrivespartner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TrafficAreaModel {


    @Expose
    @SerializedName("message")
    public String message;
    @Expose
    @SerializedName("result")
    public List<Result> result;
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
        @SerializedName("distance")
        public String distance;
        @Expose
        @SerializedName("create_dt")
        public String create_dt;
        @Expose
        @SerializedName("address")
        public String address;
        @Expose
        @SerializedName("longitude")
        public String longitude;
        @Expose
        @SerializedName("latitude")
        public String latitude;
        @Expose
        @SerializedName("booking_id")
        public String booking_id;
        @Expose
        @SerializedName("id")
        public String id;
    }
}
