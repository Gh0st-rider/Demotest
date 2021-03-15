package com.sudrives.sudrivespartner.models;

public class IhaveArrivedAtPickupLocationModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 731
     * booking_id :
     * message :
     * userid : 86
     * result : {"id":"1","booking_fee":"0","booking_id":"1716945472","book_reciever_name":"test","book_reciever_mobile":"8796435451","book_from_address":"Gold Stone Building, 2/1, Lala Banarasilal Dawar Marg, Near UCO Bank, Near Palasia, 56 Dukan, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"","book_from_long":"","book_to_address":"New Palasia, Indore, Madhya Pradesh 452001, India","book_to_lat":"","book_to_long":"","booking_status":"1","booking_status_name":"Accepted","booking_date":"1541065250","booking_start_dt":"1541073319","booking_end_dt":"1541072376","driver_arrived_pickup":"1","driver_arrived":"1541076457","is_online_payment_accept":"0","create_dt":"1541065250","eta":"02:30 PM","minutes_to_reach":"30 Min","user_details":{"user_id":"84","first_name":"Sourabh","last_name":"Gajbhiye","mobile":"7509875098","profile_img":"http://52.33.111.26/api/media/user/36a06328680cf98713df3b88dc31deae.PNG"}}
     */

    private int status;
    private int error_code;
    private int error_line;
    private String booking_id;
    private String message;
    private String userid;
    /**
     * id : 1
     * booking_fee : 0
     * booking_id : 1716945472
     * book_reciever_name : test
     * book_reciever_mobile : 8796435451
     * book_from_address : Gold Stone Building, 2/1, Lala Banarasilal Dawar Marg, Near UCO Bank, Near Palasia, 56 Dukan, New Palasia, Indore, Madhya Pradesh 452001, India
     * book_from_lat :
     * book_from_long :
     * book_to_address : New Palasia, Indore, Madhya Pradesh 452001, India
     * book_to_lat :
     * book_to_long :
     * booking_status : 1
     * booking_status_name : Accepted
     * booking_date : 1541065250
     * booking_start_dt : 1541073319
     * booking_end_dt : 1541072376
     * driver_arrived_pickup : 1
     * driver_arrived : 1541076457
     * is_online_payment_accept : 0
     * create_dt : 1541065250
     * eta : 02:30 PM
     * minutes_to_reach : 30 Min
     * user_details : {"user_id":"84","first_name":"Sourabh","last_name":"Gajbhiye","mobile":"7509875098","profile_img":"http://52.33.111.26/api/media/user/36a06328680cf98713df3b88dc31deae.PNG"}
     */

    private ResultBean result;

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

    public String getBooking_id() {
        return booking_id;
    }

    public void setBooking_id(String booking_id) {
        this.booking_id = booking_id;
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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public static class ResultBean {
        private String id;
        private String booking_fee;
        private String booking_id;
        private String booking_date_time;
        private String book_reciever_name;
        private String book_reciever_mobile;
        private String book_from_address;
        private String book_from_lat;
        private String book_from_long;
        private String book_to_address;
        private String book_to_lat;
        private String book_to_long;
        private String booking_status;
        private String booking_status_name;
        private String booking_date;
        private String booking_start_dt;
        private String booking_end_dt;
        private String driver_arrived_pickup;
        private String driver_arrived;
        private String is_online_payment_accept;
        private String create_dt;
        private String eta;
        private String minutes_to_reach;
        private String total_time;
        private String type_of_booking;

        public String getBooking_date_time() {
            return booking_date_time;
        }

        public void setBooking_date_time(String booking_date_time) {
            this.booking_date_time = booking_date_time;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }

        /**
         * user_id : 84
         * first_name : Sourabh
         * last_name : Gajbhiye
         * mobile : 7509875098
         * profile_img : http://52.33.111.26/api/media/user/36a06328680cf98713df3b88dc31deae.PNG
         */

        private UserDetailsBean user_details;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBooking_fee() {
            return booking_fee;
        }

        public void setBooking_fee(String booking_fee) {
            this.booking_fee = booking_fee;
        }

        public String getBooking_id() {
            return booking_id;
        }

        public void setBooking_id(String booking_id) {
            this.booking_id = booking_id;
        }

        public String getBook_reciever_name() {
            return book_reciever_name;
        }

        public void setBook_reciever_name(String book_reciever_name) {
            this.book_reciever_name = book_reciever_name;
        }

        public String getBook_reciever_mobile() {
            return book_reciever_mobile;
        }

        public void setBook_reciever_mobile(String book_reciever_mobile) {
            this.book_reciever_mobile = book_reciever_mobile;
        }

        public String getBook_from_address() {
            return book_from_address;
        }

        public void setBook_from_address(String book_from_address) {
            this.book_from_address = book_from_address;
        }

        public String getBook_from_lat() {
            return book_from_lat;
        }

        public void setBook_from_lat(String book_from_lat) {
            this.book_from_lat = book_from_lat;
        }

        public String getBook_from_long() {
            return book_from_long;
        }

        public void setBook_from_long(String book_from_long) {
            this.book_from_long = book_from_long;
        }

        public String getBook_to_address() {
            return book_to_address;
        }

        public void setBook_to_address(String book_to_address) {
            this.book_to_address = book_to_address;
        }

        public String getBook_to_lat() {
            return book_to_lat;
        }

        public void setBook_to_lat(String book_to_lat) {
            this.book_to_lat = book_to_lat;
        }

        public String getBook_to_long() {
            return book_to_long;
        }

        public void setBook_to_long(String book_to_long) {
            this.book_to_long = book_to_long;
        }

        public String getBooking_status() {
            return booking_status;
        }

        public void setBooking_status(String booking_status) {
            this.booking_status = booking_status;
        }

        public String getBooking_status_name() {
            return booking_status_name;
        }

        public void setBooking_status_name(String booking_status_name) {
            this.booking_status_name = booking_status_name;
        }

        public String getBooking_date() {
            return booking_date;
        }

        public void setBooking_date(String booking_date) {
            this.booking_date = booking_date;
        }

        public String getBooking_start_dt() {
            return booking_start_dt;
        }

        public void setBooking_start_dt(String booking_start_dt) {
            this.booking_start_dt = booking_start_dt;
        }

        public String getBooking_end_dt() {
            return booking_end_dt;
        }

        public void setBooking_end_dt(String booking_end_dt) {
            this.booking_end_dt = booking_end_dt;
        }

        public String getDriver_arrived_pickup() {
            return driver_arrived_pickup;
        }

        public void setDriver_arrived_pickup(String driver_arrived_pickup) {
            this.driver_arrived_pickup = driver_arrived_pickup;
        }

        public String getDriver_arrived() {
            return driver_arrived;
        }

        public void setDriver_arrived(String driver_arrived) {
            this.driver_arrived = driver_arrived;
        }

        public String getType_of_booking() {
            return type_of_booking;
        }

        public void setType_of_booking(String type_of_booking) {
            this.type_of_booking = type_of_booking;
        }

        public String getIs_online_payment_accept() {
            return is_online_payment_accept;
        }

        public void setIs_online_payment_accept(String is_online_payment_accept) {
            this.is_online_payment_accept = is_online_payment_accept;
        }

        public String getCreate_dt() {
            return create_dt;
        }

        public void setCreate_dt(String create_dt) {
            this.create_dt = create_dt;
        }

        public String getEta() {
            return eta;
        }

        public void setEta(String eta) {
            this.eta = eta;
        }

        public String getMinutes_to_reach() {
            return minutes_to_reach;
        }

        public void setMinutes_to_reach(String minutes_to_reach) {
            this.minutes_to_reach = minutes_to_reach;
        }

        public UserDetailsBean getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsBean user_details) {
            this.user_details = user_details;
        }

        public static class UserDetailsBean {
            private String user_id;
            private String first_name;
            private String last_name;
            private String mobile;
            private String profile_img;

            public String getUser_id() {
                return user_id;
            }

            public void setUser_id(String user_id) {
                this.user_id = user_id;
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

            public String getProfile_img() {
                return profile_img;
            }

            public void setProfile_img(String profile_img) {
                this.profile_img = profile_img;
            }
        }
    }
}
