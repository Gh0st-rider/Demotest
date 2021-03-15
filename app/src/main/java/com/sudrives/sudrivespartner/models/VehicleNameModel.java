package com.sudrives.sudrivespartner.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class VehicleNameModel {


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
        @SerializedName("update_dt")
        public String update_dt;
        @Expose
        @SerializedName("create_dt")
        public String create_dt;
        @Expose
        @SerializedName("status")
        public String status;
        @Expose
        @SerializedName("vehicle_type_id")
        public String vehicle_type_id;
        @Expose
        @SerializedName("name")
        public String name;
        @Expose
        @SerializedName("id")
        public String id;

        public Result(String update_dt, String create_dt, String status, String vehicle_type_id, String name, String id) {
            this.update_dt = update_dt;
            this.create_dt = create_dt;
            this.status = status;
            this.vehicle_type_id = vehicle_type_id;
            this.name = name;
            this.id = id;
        }
    }
}
