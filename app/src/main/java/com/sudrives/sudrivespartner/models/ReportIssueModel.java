package com.sudrives.sudrivespartner.models;

import java.io.Serializable;
import java.util.List;

public class ReportIssueModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 1875
     * result : [{"mobile_no":"","email":"s@s.com","subject":"","comments":"test purpose","status":"pending","create_dt":"17-Nov-2018 04:47 PM","book_from_address":"Gold Stone Building, Lala Banarasilal Dawar Marg, New Palasia, Indore, Madhya Pradesh 452001, India","book_to_address":"L.I.G, Anoop Nagar, Indore, Madhya Pradesh 452018, India","booking_id":"471815865","vehicle_name":"HL 1"}]
     * message : Successfully
     */

    private int status;
    private int error_code;
    private int error_line;
    private String message;
    private List<ResultBean> result;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public int getError_code() {
        return error_code;
    }

    public void setError_code(int error_code) {
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

    public List<ResultBean> getResult() {
        return result;
    }

    public void setResult(List<ResultBean> result) {
        this.result = result;
    }

    public static class ResultBean implements Serializable{
        /**
         * mobile_no :
         * email : s@s.com
         * subject :
         * comments : test purpose
         * status : pending
         * create_dt : 17-Nov-2018 04:47 PM
         * book_from_address : Gold Stone Building, Lala Banarasilal Dawar Marg, New Palasia, Indore, Madhya Pradesh 452001, India
         * book_to_address : L.I.G, Anoop Nagar, Indore, Madhya Pradesh 452018, India
         * booking_id : 471815865
         * vehicle_name : HL 1
         */

        private String mobile_no;
        private String email;
        private String subject;
        private String comments;
        private String status;
        private String create_dt;
        private String trip_price;
        private String book_from_address;
        private String book_to_address;
        private String booking_id;
        private String vehicle_name;

        public String getTrip_price() {
            return trip_price;
        }

        public void setTrip_price(String trip_price) {
            this.trip_price = trip_price;
        }

        public String getMobile_no() {
            return mobile_no;
        }

        public void setMobile_no(String mobile_no) {
            this.mobile_no = mobile_no;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }

        public String getComments() {
            return comments;
        }

        public void setComments(String comments) {
            this.comments = comments;
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

        public String getBook_from_address() {
            return book_from_address;
        }

        public void setBook_from_address(String book_from_address) {
            this.book_from_address = book_from_address;
        }

        public String getBook_to_address() {
            return book_to_address;
        }

        public void setBook_to_address(String book_to_address) {
            this.book_to_address = book_to_address;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(String booking_id) {
            this.booking_id = booking_id;
        }

        public String getVehicle_name() {
            return vehicle_name;
        }

        public void setVehicle_name(String vehicle_name) {
            this.vehicle_name = vehicle_name;
        }
    }
}
