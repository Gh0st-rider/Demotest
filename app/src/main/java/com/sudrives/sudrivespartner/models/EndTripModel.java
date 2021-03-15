package com.sudrives.sudrivespartner.models;

import java.util.List;

public class EndTripModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 1501
     * booking_id : 170
     * result : {"id":"170","passenger":"425","cancel_by":"","cancel_msg":"","driver_id":"402","booking_fee":"100","booking_id":"2112250757","book_reciever_name":"3853835838","book_reciever_mobile":"3853835838","book_from_address":"Gold Stone Building, Lala Banarasilal Dawar Marg, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.72667729876361","book_from_long":"75.88417802006006","book_to_address":"Shop no 223, Arbitto mall , nea C21 mall and Malhar mall, Vijay Nagar, Indore, Madhya Pradesh 452010, India","book_to_lat":"22.753284800000003","book_to_long":"75.8936962","booking_status":"3","driver_arrived_pickup":"1","booking_status_name":"Completed","booking_date":"1542706482","booking_start_dt":"1542706667","booking_end_dt":"1542706679","vehicle_name":"HL 3","driver_arrived":"1542706587","is_online_payment_accept":"0","create_dt":"1542706482","eta":"5 min","minutes_to_reach":"5 min","cancel_charge":"65","total_charge":"120","amount_to_pay":"0","total_fare":"0","base_fare":"0","total_distance":"3.8 km","total_time":"13 mins","trip_path":[],"vehicle_types":"4","trip_distance":"3.8 km","trip_time":"13 mins","payment_responce_data":"","driver_details":{"driver_id":"402","first_name":"Yy","last_name":"Uu","mobile":"5555555555","profile_img":"http://52.33.111.26/api/media/user/b470b40a90a3418fc6e20b84de3a3571.png","vehicle_number":"mp09qw6458","vehicle_name":"HL 3","avg_rating":"0","driver_lat":"22.7267051","driver_lang":"75.8841337"},"user_details":{"user_id":"425","first_name":"Yy","last_name":"Uu","mobile":"5555555555","profile_img":"http://52.33.111.26/api/media/user/b470b40a90a3418fc6e20b84de3a3571.png","driver_lat":"22.7267051","driver_lang":"75.8841337"}}
     * distance : 0 Km
     * fare : 10
     * userid : 402
     * message : ट्रिप एंड सफलतापूर्वक
     */

    private int status;
    private int error_code;
    private int error_line;
    private String booking_id;
    /**
     * id : 170
     * passenger : 425
     * cancel_by :
     * cancel_msg :
     * driver_id : 402
     * booking_fee : 100
     * booking_id : 2112250757
     * book_reciever_name : 3853835838
     * book_reciever_mobile : 3853835838
     * book_from_address : Gold Stone Building, Lala Banarasilal Dawar Marg, New Palasia, Indore, Madhya Pradesh 452001, India
     * book_from_lat : 22.72667729876361
     * book_from_long : 75.88417802006006
     * book_to_address : Shop no 223, Arbitto mall , nea C21 mall and Malhar mall, Vijay Nagar, Indore, Madhya Pradesh 452010, India
     * book_to_lat : 22.753284800000003
     * book_to_long : 75.8936962
     * booking_status : 3
     * driver_arrived_pickup : 1
     * booking_status_name : Completed
     * booking_date : 1542706482
     * booking_start_dt : 1542706667
     * booking_end_dt : 1542706679
     * vehicle_name : HL 3
     * driver_arrived : 1542706587
     * is_online_payment_accept : 0
     * create_dt : 1542706482
     * eta : 5 min
     * minutes_to_reach : 5 min
     * cancel_charge : 65
     * total_charge : 120
     * amount_to_pay : 0
     * total_fare : 0
     * base_fare : 0
     * total_distance : 3.8 km
     * total_time : 13 mins
     * trip_path : []
     * vehicle_types : 4
     * trip_distance : 3.8 km
     * trip_time : 13 mins
     * payment_responce_data :
     * driver_details : {"driver_id":"402","first_name":"Yy","last_name":"Uu","mobile":"5555555555","profile_img":"http://52.33.111.26/api/media/user/b470b40a90a3418fc6e20b84de3a3571.png","vehicle_number":"mp09qw6458","vehicle_name":"HL 3","avg_rating":"0","driver_lat":"22.7267051","driver_lang":"75.8841337"}
     * user_details : {"user_id":"425","first_name":"Yy","last_name":"Uu","mobile":"5555555555","profile_img":"http://52.33.111.26/api/media/user/b470b40a90a3418fc6e20b84de3a3571.png","driver_lat":"22.7267051","driver_lang":"75.8841337"}
     */

    private ResultBean result;
    private String distance;
    private String fare;
    private String userid;
    private String message;

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

    public ResultBean getResult() {
        return result;
    }

    public void setResult(ResultBean result) {
        this.result = result;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }

    public String getFare() {
        return fare;
    }

    public void setFare(String fare) {
        this.fare = fare;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean {
        private String id;
        private String passenger;
        private String cancel_by;
        private String cancel_msg;
        private String driver_id;
        private String booking_fee;
        private String booking_id;
        private String book_reciever_name;
        private String book_reciever_mobile;
        private String book_from_address;
        private String book_from_lat;
        private String book_from_long;
        private String book_to_address;
        private String book_to_lat;
        private String book_to_long;
        private String booking_status;
        private String driver_arrived_pickup;
        private String booking_status_name;
        private String booking_date;
        private String booking_start_dt;
        private String booking_end_dt;
        private String vehicle_name;
        private String driver_arrived;
        private String is_online_payment_accept;
        private String create_dt;
        private String need_builty;
        private String eta;
        private String minutes_to_reach;
        private String cancel_charge;
        private String total_charge;
        private String amount_to_pay;
        private String total_fare;
        private String base_fare;
        private String total_distance;
        private String total_time;
        private String vehicle_types;
        private String trip_distance;
        private String trip_time;
        private String payment_responce_data;

        public String getNeed_builty() {
            return need_builty;
        }

        public void setNeed_builty(String need_builty) {
            this.need_builty = need_builty;
        }

        /**
         * driver_id : 402
         * first_name : Yy
         * last_name : Uu
         * mobile : 5555555555
         * profile_img : http://52.33.111.26/api/media/user/b470b40a90a3418fc6e20b84de3a3571.png
         * vehicle_number : mp09qw6458
         * vehicle_name : HL 3
         * avg_rating : 0
         * driver_lat : 22.7267051
         * driver_lang : 75.8841337
         */

        private DriverDetailsBean driver_details;
        /**
         * user_id : 425
         * first_name : Yy
         * last_name : Uu
         * mobile : 5555555555
         * profile_img : http://52.33.111.26/api/media/user/b470b40a90a3418fc6e20b84de3a3571.png
         * driver_lat : 22.7267051
         * driver_lang : 75.8841337
         */

        private UserDetailsBean user_details;
        private List<?> trip_path;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getPassenger() {
            return passenger;
        }

        public void setPassenger(String passenger) {
            this.passenger = passenger;
        }

        public String getCancel_by() {
            return cancel_by;
        }

        public void setCancel_by(String cancel_by) {
            this.cancel_by = cancel_by;
        }

        public String getCancel_msg() {
            return cancel_msg;
        }

        public void setCancel_msg(String cancel_msg) {
            this.cancel_msg = cancel_msg;
        }

        public String getDriver_id() {
            return driver_id;
        }

        public void setDriver_id(String driver_id) {
            this.driver_id = driver_id;
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

        public String getDriver_arrived_pickup() {
            return driver_arrived_pickup;
        }

        public void setDriver_arrived_pickup(String driver_arrived_pickup) {
            this.driver_arrived_pickup = driver_arrived_pickup;
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

        public String getVehicle_name() {
            return vehicle_name;
        }

        public void setVehicle_name(String vehicle_name) {
            this.vehicle_name = vehicle_name;
        }

        public String getDriver_arrived() {
            return driver_arrived;
        }

        public void setDriver_arrived(String driver_arrived) {
            this.driver_arrived = driver_arrived;
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

        public String getCancel_charge() {
            return cancel_charge;
        }

        public void setCancel_charge(String cancel_charge) {
            this.cancel_charge = cancel_charge;
        }

        public String getTotal_charge() {
            return total_charge;
        }

        public void setTotal_charge(String total_charge) {
            this.total_charge = total_charge;
        }

        public String getAmount_to_pay() {
            return amount_to_pay;
        }

        public void setAmount_to_pay(String amount_to_pay) {
            this.amount_to_pay = amount_to_pay;
        }

        public String getTotal_fare() {
            return total_fare;
        }

        public void setTotal_fare(String total_fare) {
            this.total_fare = total_fare;
        }

        public String getBase_fare() {
            return base_fare;
        }

        public void setBase_fare(String base_fare) {
            this.base_fare = base_fare;
        }

        public String getTotal_distance() {
            return total_distance;
        }

        public void setTotal_distance(String total_distance) {
            this.total_distance = total_distance;
        }

        public String getTotal_time() {
            return total_time;
        }

        public void setTotal_time(String total_time) {
            this.total_time = total_time;
        }

        public String getVehicle_types() {
            return vehicle_types;
        }

        public void setVehicle_types(String vehicle_types) {
            this.vehicle_types = vehicle_types;
        }

        public String getTrip_distance() {
            return trip_distance;
        }

        public void setTrip_distance(String trip_distance) {
            this.trip_distance = trip_distance;
        }

        public String getTrip_time() {
            return trip_time;
        }

        public void setTrip_time(String trip_time) {
            this.trip_time = trip_time;
        }

        public String getPayment_responce_data() {
            return payment_responce_data;
        }

        public void setPayment_responce_data(String payment_responce_data) {
            this.payment_responce_data = payment_responce_data;
        }

        public DriverDetailsBean getDriver_details() {
            return driver_details;
        }

        public void setDriver_details(DriverDetailsBean driver_details) {
            this.driver_details = driver_details;
        }

        public UserDetailsBean getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsBean user_details) {
            this.user_details = user_details;
        }

        public List<?> getTrip_path() {
            return trip_path;
        }

        public void setTrip_path(List<?> trip_path) {
            this.trip_path = trip_path;
        }

        public static class DriverDetailsBean {
            private String driver_id;
            private String first_name;
            private String last_name;
            private String mobile;
            private String profile_img;
            private String vehicle_number;
            private String vehicle_name;
            private String avg_rating;
            private String driver_lat;
            private String driver_lang;

            public String getDriver_id() {
                return driver_id;
            }

            public void setDriver_id(String driver_id) {
                this.driver_id = driver_id;
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

            public String getVehicle_number() {
                return vehicle_number;
            }

            public void setVehicle_number(String vehicle_number) {
                this.vehicle_number = vehicle_number;
            }

            public String getVehicle_name() {
                return vehicle_name;
            }

            public void setVehicle_name(String vehicle_name) {
                this.vehicle_name = vehicle_name;
            }

            public String getAvg_rating() {
                return avg_rating;
            }

            public void setAvg_rating(String avg_rating) {
                this.avg_rating = avg_rating;
            }

            public String getDriver_lat() {
                return driver_lat;
            }

            public void setDriver_lat(String driver_lat) {
                this.driver_lat = driver_lat;
            }

            public String getDriver_lang() {
                return driver_lang;
            }

            public void setDriver_lang(String driver_lang) {
                this.driver_lang = driver_lang;
            }
        }

        public static class UserDetailsBean {
            private String user_id;
            private String first_name;
            private String last_name;
            private String mobile;
            private String profile_img;
            private String driver_lat;
            private String driver_lang;

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

            public String getDriver_lat() {
                return driver_lat;
            }

            public void setDriver_lat(String driver_lat) {
                this.driver_lat = driver_lat;
            }

            public String getDriver_lang() {
                return driver_lang;
            }

            public void setDriver_lang(String driver_lang) {
                this.driver_lang = driver_lang;
            }
        }
    }
}
