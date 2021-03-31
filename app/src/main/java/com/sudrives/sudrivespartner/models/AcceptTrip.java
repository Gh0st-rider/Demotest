package com.sudrives.sudrivespartner.models;

import java.io.Serializable;
import java.util.List;

public class AcceptTrip implements Serializable {
    /**
     * status : 1
     * error_code : 0
     * error_line : 848
     * popup_time : 20
     * booking_id :
     * driver_id : ["612"]
     * result : {"id":"12","passenger":"611","vehicle_marker_img":"http://52.33.111.26/api/media/vehicle/mhl-3.png","booking_date_time":"04:22 PM","cancel_by":"","cancel_msg":"","driver_id":"612","booking_fee":"170","booking_id":"1143574452","book_reciever_name":"9907300464","book_reciever_mobile":"9907300464","book_from_address":"Gold Stone Building, 302, 3/5, Lala Banarasilal Dawar Marg, Above Divine Jewellers, Near 56 Dukan, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.726777929274352","book_from_long":"75.88416964708591","book_to_address":"Pologround Industrial Estate, Nanda Nagar, Malti Vanaspati, Indore, Madhya Pradesh, India","book_to_lat":"22.737907","book_to_long":"75.8584478","booking_status":"6","driver_arrived_pickup":"1","booking_status_name":"Pending","booking_date":"1543315978","booking_start_dt":"1543317468","booking_end_dt":"1543317477","need_builty":"0","vehicle_name":"HL 3","driver_arrived":"1543317464","drive_arrived_destination":"0","is_online_payment_accept":"0","create_dt":"1543315978","eta":"05:04 PM","minutes_to_reach":"5 min","cancel_charge":"65","total_charge":"120","amount_to_pay":"0","total_fare":"169.88","base_fare":"151","total_distance":"0 km","total_time":"1 min","trip_path":[{"id":"1","driver_id":"612","trip_id ":"12","lat":"22.726695","lang":"75.8841356","comments":"","createDt":1543318433}],"vehicle_types":"1","trip_distance":"4.0 km","trip_time":"12 mins","payment_responce_data":"","driver_details":{"driver_id":"612","first_name":"RUser","last_name":"Goswamiuser","mobile":"8839727164","profile_img":"http://52.33.111.26/api/media/user/default_img.png","vehicle_number":"MP41SM2845","vehicle_name":"HL 3","avg_rating":"0","driver_lat":"22.726695","driver_lang":"75.8841356"},"user_details":{"user_id":"611","first_name":"RUser","last_name":"Goswamiuser","mobile":"8839727164","profile_img":"http://52.33.111.26/api/media/user/default_img.png","driver_lat":"22.726695","driver_lang":"75.8841356"}}
     * message : Notification send successfully
     */

    private int status;
    private int error_code;
    private int error_line;
    private int popup_time;
    private String booking_id;
    private ResultBean result;
    private String message;
    private List<String> driver_id;

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

    public int getPopup_time() {
        return popup_time;
    }

    public void setPopup_time(int popup_time) {
        this.popup_time = popup_time;
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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<String> getDriver_id() {
        return driver_id;
    }

    public void setDriver_id(List<String> driver_id) {
        this.driver_id = driver_id;
    }

    public static class ResultBean implements Serializable{
        /**
         * id : 12
         * passenger : 611
         * vehicle_marker_img : http://52.33.111.26/api/media/vehicle/mhl-3.png
         * booking_date_time : 04:22 PM
         * cancel_by :
         * cancel_msg :
         * driver_id : 612
         * booking_fee : 170
         * booking_id : 1143574452
         * book_reciever_name : 9907300464
         * book_reciever_mobile : 9907300464
         * book_from_address : Gold Stone Building, 302, 3/5, Lala Banarasilal Dawar Marg, Above Divine Jewellers, Near 56 Dukan, New Palasia, Indore, Madhya Pradesh 452001, India
         * book_from_lat : 22.726777929274352
         * book_from_long : 75.88416964708591
         * book_to_address : Pologround Industrial Estate, Nanda Nagar, Malti Vanaspati, Indore, Madhya Pradesh, India
         * book_to_lat : 22.737907
         * book_to_long : 75.8584478
         * booking_status : 6
         * driver_arrived_pickup : 1
         * booking_status_name : Pending
         * booking_date : 1543315978
         * booking_start_dt : 1543317468
         * booking_end_dt : 1543317477
         * need_builty : 0
         * vehicle_name : HL 3
         * driver_arrived : 1543317464
         * drive_arrived_destination : 0
         * is_online_payment_accept : 0
         * create_dt : 1543315978
         * eta : 05:04 PM
         * minutes_to_reach : 5 min
         * cancel_charge : 65
         * total_charge : 120
         * amount_to_pay : 0
         * total_fare : 169.88
         * base_fare : 151
         * total_distance : 0 km
         * total_time : 1 min
         * trip_path : [{"id":"1","driver_id":"612","trip_id ":"12","lat":"22.726695","lang":"75.8841356","comments":"","createDt":1543318433}]
         * vehicle_types : 1
         * trip_distance : 4.0 km
         * trip_time : 12 mins
         * payment_responce_data :
         * driver_details : {"driver_id":"612","first_name":"RUser","last_name":"Goswamiuser","mobile":"8839727164","profile_img":"http://52.33.111.26/api/media/user/default_img.png","vehicle_number":"MP41SM2845","vehicle_name":"HL 3","avg_rating":"0","driver_lat":"22.726695","driver_lang":"75.8841356"}
         * user_details : {"user_id":"611","first_name":"RUser","last_name":"Goswamiuser","mobile":"8839727164","profile_img":"http://52.33.111.26/api/media/user/default_img.png","driver_lat":"22.726695","driver_lang":"75.8841356"}
         */

        private String id;
        private String passenger;
        private String vehicle_marker_img;
        private String booking_date_time;
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
        private String need_builty;
        private String vehicle_name;
        private String driver_arrived;
        private String drive_arrived_destination;
        private String is_online_payment_accept;
        private String create_dt;
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
        private String type_of_booking;
        private String payment_mode;

        public String getPayment_mode() {
            return payment_mode;
        }

        public void setPayment_mode(String payment_mode) {
            this.payment_mode = payment_mode;
        }

        // private DriverDetailsBean driver_details;
        private UserDetailsBean user_details;
        private List<TripPathBean> trip_path;

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

        public String getVehicle_marker_img() {
            return vehicle_marker_img;
        }

        public void setVehicle_marker_img(String vehicle_marker_img) {
            this.vehicle_marker_img = vehicle_marker_img;
        }

        public String getBooking_date_time() {
            return booking_date_time;
        }

        public void setBooking_date_time(String booking_date_time) {
            this.booking_date_time = booking_date_time;
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

        public String getNeed_builty() {
            return need_builty;
        }

        public void setNeed_builty(String need_builty) {
            this.need_builty = need_builty;
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

        public String getDrive_arrived_destination() {
            return drive_arrived_destination;
        }

        public void setDrive_arrived_destination(String drive_arrived_destination) {
            this.drive_arrived_destination = drive_arrived_destination;
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

        public String getType_of_booking() {
            return type_of_booking;
        }

        public void setType_of_booking(String type_of_booking) {
            this.type_of_booking = type_of_booking;
        }

        //        public DriverDetailsBean getDriver_details() {
//            return driver_details;
//        }
//
//        public void setDriver_details(DriverDetailsBean driver_details) {
//            this.driver_details = driver_details;
//        }

        public UserDetailsBean getUser_details() {
            return user_details;
        }

        public void setUser_details(UserDetailsBean user_details) {
            this.user_details = user_details;
        }

        public List<TripPathBean> getTrip_path() {
            return trip_path;
        }

        public void setTrip_path(List<TripPathBean> trip_path) {
            this.trip_path = trip_path;
        }

        public static class DriverDetailsBean implements Serializable{
            /**
             * driver_id : 612
             * first_name : RUser
             * last_name : Goswamiuser
             * mobile : 8839727164
             * profile_img : http://52.33.111.26/api/media/user/default_img.png
             * vehicle_number : MP41SM2845
             * vehicle_name : HL 3
             * avg_rating : 0
             * driver_lat : 22.726695
             * driver_lang : 75.8841356
             */

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

        public static class UserDetailsBean implements Serializable{
            /**
             * user_id : 611
             * first_name : RUser
             * last_name : Goswamiuser
             * mobile : 8839727164
             * profile_img : http://52.33.111.26/api/media/user/default_img.png
             * driver_lat : 22.726695
             * driver_lang : 75.8841356
             */

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

        public static class TripPathBean implements Serializable{
            /**
             * id : 1
             * driver_id : 612
             * trip_id  : 12
             * lat : 22.726695
             * lang : 75.8841356
             * comments :
             * createDt : 1543318433
             */

            private String id;
            private String driver_id;
            private String trip_id;
            private String lat;
            private String lang;
            private String comments;
            private int createDt;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getDriver_id() {
                return driver_id;
            }

            public void setDriver_id(String driver_id) {
                this.driver_id = driver_id;
            }

            public String getTrip_id() {
                return trip_id;
            }

            public void setTrip_id(String trip_id) {
                this.trip_id = trip_id;
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

            public String getComments() {
                return comments;
            }

            public void setComments(String comments) {
                this.comments = comments;
            }

            public int getCreateDt() {
                return createDt;
            }

            public void setCreateDt(int createDt) {
                this.createDt = createDt;
            }
        }
    }


//
//    /**
//     * status : 1
//     * error_code : 0
//     * error_line : 816
//     * popup_time : 20
//     * booking_id :
//     * driver_id : ["402"]
//     * result : {"id":"403","passenger":"565","cancel_by":"","cancel_msg":"","driver_id":"0","booking_fee":"100","booking_id":"2089983614","book_reciever_name":"9956549519","book_reciever_mobile":"9956549519","book_from_address":"Shop No. 302, 3rd Floor, Gold Stone Building, Lala Banarasilal Dawar Marg, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.72690799513127","book_from_long":"75.88413141667843","book_to_address":"Arrivals, Indore - Pithampur Rd, Devi Ahillyabai Holkar Airport Area, Indore, Madhya Pradesh 453112, India","book_to_lat":"22.7276836","book_to_long":"75.8044115","booking_status":"6","driver_arrived_pickup":"0","booking_status_name":"Pending","booking_date":"1542957734","booking_start_dt":"0","booking_end_dt":"0","vehicle_name":"HL 3","driver_arrived":"0","is_online_payment_accept":"0","create_dt":"1542957734","eta":"5 min","minutes_to_reach":"5 min","cancel_charge":"65","total_charge":"120","amount_to_pay":"0","total_fare":"0","base_fare":"0","total_distance":"10.5 km","total_time":"28 mins","trip_path":[],"vehicle_types":"4","trip_distance":"10.5 km","trip_time":"28 mins","payment_responce_data":"","driver_details":[],"user_details":{"user_id":"565","first_name":"Aman","last_name":"Jain","mobile":"9806112711","profile_img":"http://52.33.111.26/api/media/user/default_img.png","driver_lat":"0","driver_lang":"0"}}
//     * message : Notification send successfully
//     */
//
//    private int status;
//    private int error_code;
//    private int error_line;
//    private int popup_time;
//    private String booking_id;
//    /**
//     * id : 403
//     * passenger : 565
//     * cancel_by :
//     * cancel_msg :
//     * driver_id : 0
//     * booking_fee : 100
//     * booking_id : 2089983614
//     * book_reciever_name : 9956549519
//     * book_reciever_mobile : 9956549519
//     * book_from_address : Shop No. 302, 3rd Floor, Gold Stone Building, Lala Banarasilal Dawar Marg, New Palasia, Indore, Madhya Pradesh 452001, India
//     * book_from_lat : 22.72690799513127
//     * book_from_long : 75.88413141667843
//     * book_to_address : Arrivals, Indore - Pithampur Rd, Devi Ahillyabai Holkar Airport Area, Indore, Madhya Pradesh 453112, India
//     * book_to_lat : 22.7276836
//     * book_to_long : 75.8044115
//     * booking_status : 6
//     * driver_arrived_pickup : 0
//     * booking_status_name : Pending
//     * booking_date : 1542957734
//     * booking_start_dt : 0
//     * booking_end_dt : 0
//     * vehicle_name : HL 3
//     * driver_arrived : 0
//     * is_online_payment_accept : 0
//     * create_dt : 1542957734
//     * eta : 5 min
//     * minutes_to_reach : 5 min
//     * cancel_charge : 65
//     * total_charge : 120
//     * amount_to_pay : 0
//     * total_fare : 0
//     * base_fare : 0
//     * total_distance : 10.5 km
//     * total_time : 28 mins
//     * trip_path : []
//     * vehicle_types : 4
//     * trip_distance : 10.5 km
//     * trip_time : 28 mins
//     * payment_responce_data :
//     * driver_details : []
//     * user_details : {"user_id":"565","first_name":"Aman","last_name":"Jain","mobile":"9806112711","profile_img":"http://52.33.111.26/api/media/user/default_img.png","driver_lat":"0","driver_lang":"0"}
//     */
//
//    private ResultBean result;
//    private String message;
//    private List<String> driver_id;
//
//    public int getStatus() {
//        return status;
//    }
//
//    public void setStatus(int status) {
//        this.status = status;
//    }
//
//    public int getError_code() {
//        return error_code;
//    }
//
//    public void setError_code(int error_code) {
//        this.error_code = error_code;
//    }
//
//    public int getError_line() {
//        return error_line;
//    }
//
//    public void setError_line(int error_line) {
//        this.error_line = error_line;
//    }
//
//    public int getPopup_time() {
//        return popup_time;
//    }
//
//    public void setPopup_time(int popup_time) {
//        this.popup_time = popup_time;
//    }
//
//    public String getBooking_id() {
//        return booking_id;
//    }
//
//    public void setBooking_id(String booking_id) {
//        this.booking_id = booking_id;
//    }
//
//    public ResultBean getResult() {
//        return result;
//    }
//
//    public void setResult(ResultBean result) {
//        this.result = result;
//    }
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public List<String> getDriver_id() {
//        return driver_id;
//    }
//
//    public void setDriver_id(List<String> driver_id) {
//        this.driver_id = driver_id;
//    }
//
//    public static class ResultBean {
//        private String id;
//        private String passenger;
//        private String cancel_by;
//        private String cancel_msg;
//        private String driver_id;
//        private String booking_fee;
//        private String booking_id;
//        private String book_reciever_name;
//        private String book_reciever_mobile;
//        private String book_from_address;
//        private String book_from_lat;
//        private String book_from_long;
//        private String book_to_address;
//        private String book_to_lat;
//        private String book_to_long;
//        private String booking_status;
//        private String driver_arrived_pickup;
//        private String booking_status_name;
//        private String booking_date;
//        private String booking_start_dt;
//        private String booking_end_dt;
//        private String vehicle_name;
//        private String driver_arrived;
//        private String is_online_payment_accept;
//        private String create_dt;
//        private String eta;
//        private String minutes_to_reach;
//        private String cancel_charge;
//        private String total_charge;
//        private String amount_to_pay;
//        private String total_fare;
//        private String base_fare;
//        private String total_distance;
//        private String total_time;
//        private String vehicle_types;
//        private String trip_distance;
//        private String trip_time;
//        private String payment_responce_data;
//        /**
//         * user_id : 565
//         * first_name : Aman
//         * last_name : Jain
//         * mobile : 9806112711
//         * profile_img : http://52.33.111.26/api/media/user/default_img.png
//         * driver_lat : 0
//         * driver_lang : 0
//         */
//
//        private UserDetailsBean user_details;
//        private List<?> trip_path;
//        private List<?> driver_details;
//
//        public String getId() {
//            return id;
//        }
//
//        public void setId(String id) {
//            this.id = id;
//        }
//
//        public String getPassenger() {
//            return passenger;
//        }
//
//        public void setPassenger(String passenger) {
//            this.passenger = passenger;
//        }
//
//        public String getCancel_by() {
//            return cancel_by;
//        }
//
//        public void setCancel_by(String cancel_by) {
//            this.cancel_by = cancel_by;
//        }
//
//        public String getCancel_msg() {
//            return cancel_msg;
//        }
//
//        public void setCancel_msg(String cancel_msg) {
//            this.cancel_msg = cancel_msg;
//        }
//
//        public String getDriver_id() {
//            return driver_id;
//        }
//
//        public void setDriver_id(String driver_id) {
//            this.driver_id = driver_id;
//        }
//
//        public String getBooking_fee() {
//            return booking_fee;
//        }
//
//        public void setBooking_fee(String booking_fee) {
//            this.booking_fee = booking_fee;
//        }
//
//        public String getBooking_id() {
//            return booking_id;
//        }
//
//        public void setBooking_id(String booking_id) {
//            this.booking_id = booking_id;
//        }
//
//        public String getBook_reciever_name() {
//            return book_reciever_name;
//        }
//
//        public void setBook_reciever_name(String book_reciever_name) {
//            this.book_reciever_name = book_reciever_name;
//        }
//
//        public String getBook_reciever_mobile() {
//            return book_reciever_mobile;
//        }
//
//        public void setBook_reciever_mobile(String book_reciever_mobile) {
//            this.book_reciever_mobile = book_reciever_mobile;
//        }
//
//        public String getBook_from_address() {
//            return book_from_address;
//        }
//
//        public void setBook_from_address(String book_from_address) {
//            this.book_from_address = book_from_address;
//        }
//
//        public String getBook_from_lat() {
//            return book_from_lat;
//        }
//
//        public void setBook_from_lat(String book_from_lat) {
//            this.book_from_lat = book_from_lat;
//        }
//
//        public String getBook_from_long() {
//            return book_from_long;
//        }
//
//        public void setBook_from_long(String book_from_long) {
//            this.book_from_long = book_from_long;
//        }
//
//        public String getBook_to_address() {
//            return book_to_address;
//        }
//
//        public void setBook_to_address(String book_to_address) {
//            this.book_to_address = book_to_address;
//        }
//
//        public String getBook_to_lat() {
//            return book_to_lat;
//        }
//
//        public void setBook_to_lat(String book_to_lat) {
//            this.book_to_lat = book_to_lat;
//        }
//
//        public String getBook_to_long() {
//            return book_to_long;
//        }
//
//        public void setBook_to_long(String book_to_long) {
//            this.book_to_long = book_to_long;
//        }
//
//        public String getBooking_status() {
//            return booking_status;
//        }
//
//        public void setBooking_status(String booking_status) {
//            this.booking_status = booking_status;
//        }
//
//        public String getDriver_arrived_pickup() {
//            return driver_arrived_pickup;
//        }
//
//        public void setDriver_arrived_pickup(String driver_arrived_pickup) {
//            this.driver_arrived_pickup = driver_arrived_pickup;
//        }
//
//        public String getBooking_status_name() {
//            return booking_status_name;
//        }
//
//        public void setBooking_status_name(String booking_status_name) {
//            this.booking_status_name = booking_status_name;
//        }
//
//        public String getBooking_date() {
//            return booking_date;
//        }
//
//        public void setBooking_date(String booking_date) {
//            this.booking_date = booking_date;
//        }
//
//        public String getBooking_start_dt() {
//            return booking_start_dt;
//        }
//
//        public void setBooking_start_dt(String booking_start_dt) {
//            this.booking_start_dt = booking_start_dt;
//        }
//
//        public String getBooking_end_dt() {
//            return booking_end_dt;
//        }
//
//        public void setBooking_end_dt(String booking_end_dt) {
//            this.booking_end_dt = booking_end_dt;
//        }
//
//        public String getVehicle_name() {
//            return vehicle_name;
//        }
//
//        public void setVehicle_name(String vehicle_name) {
//            this.vehicle_name = vehicle_name;
//        }
//
//        public String getDriver_arrived() {
//            return driver_arrived;
//        }
//
//        public void setDriver_arrived(String driver_arrived) {
//            this.driver_arrived = driver_arrived;
//        }
//
//        public String getIs_online_payment_accept() {
//            return is_online_payment_accept;
//        }
//
//        public void setIs_online_payment_accept(String is_online_payment_accept) {
//            this.is_online_payment_accept = is_online_payment_accept;
//        }
//
//        public String getCreate_dt() {
//            return create_dt;
//        }
//
//        public void setCreate_dt(String create_dt) {
//            this.create_dt = create_dt;
//        }
//
//        public String getEta() {
//            return eta;
//        }
//
//        public void setEta(String eta) {
//            this.eta = eta;
//        }
//
//        public String getMinutes_to_reach() {
//            return minutes_to_reach;
//        }
//
//        public void setMinutes_to_reach(String minutes_to_reach) {
//            this.minutes_to_reach = minutes_to_reach;
//        }
//
//        public String getCancel_charge() {
//            return cancel_charge;
//        }
//
//        public void setCancel_charge(String cancel_charge) {
//            this.cancel_charge = cancel_charge;
//        }
//
//        public String getTotal_charge() {
//            return total_charge;
//        }
//
//        public void setTotal_charge(String total_charge) {
//            this.total_charge = total_charge;
//        }
//
//        public String getAmount_to_pay() {
//            return amount_to_pay;
//        }
//
//        public void setAmount_to_pay(String amount_to_pay) {
//            this.amount_to_pay = amount_to_pay;
//        }
//
//        public String getTotal_fare() {
//            return total_fare;
//        }
//
//        public void setTotal_fare(String total_fare) {
//            this.total_fare = total_fare;
//        }
//
//        public String getBase_fare() {
//            return base_fare;
//        }
//
//        public void setBase_fare(String base_fare) {
//            this.base_fare = base_fare;
//        }
//
//        public String getTotal_distance() {
//            return total_distance;
//        }
//
//        public void setTotal_distance(String total_distance) {
//            this.total_distance = total_distance;
//        }
//
//        public String getTotal_time() {
//            return total_time;
//        }
//
//        public void setTotal_time(String total_time) {
//            this.total_time = total_time;
//        }
//
//        public String getVehicle_types() {
//            return vehicle_types;
//        }
//
//        public void setVehicle_types(String vehicle_types) {
//            this.vehicle_types = vehicle_types;
//        }
//
//        public String getTrip_distance() {
//            return trip_distance;
//        }
//
//        public void setTrip_distance(String trip_distance) {
//            this.trip_distance = trip_distance;
//        }
//
//        public String getTrip_time() {
//            return trip_time;
//        }
//
//        public void setTrip_time(String trip_time) {
//            this.trip_time = trip_time;
//        }
//
//        public String getPayment_responce_data() {
//            return payment_responce_data;
//        }
//
//        public void setPayment_responce_data(String payment_responce_data) {
//            this.payment_responce_data = payment_responce_data;
//        }
//
//        public UserDetailsBean getUser_details() {
//            return user_details;
//        }
//
//        public void setUser_details(UserDetailsBean user_details) {
//            this.user_details = user_details;
//        }
//
//        public List<?> getTrip_path() {
//            return trip_path;
//        }
//
//        public void setTrip_path(List<?> trip_path) {
//            this.trip_path = trip_path;
//        }
//
//        public List<?> getDriver_details() {
//            return driver_details;
//        }
//
//        public void setDriver_details(List<?> driver_details) {
//            this.driver_details = driver_details;
//        }
//
//        public static class UserDetailsBean {
//            private String user_id;
//            private String first_name;
//            private String last_name;
//            private String mobile;
//            private String profile_img;
//            private String driver_lat;
//            private String driver_lang;
//
//            public String getUser_id() {
//                return user_id;
//            }
//
//            public void setUser_id(String user_id) {
//                this.user_id = user_id;
//            }
//
//            public String getFirst_name() {
//                return first_name;
//            }
//
//            public void setFirst_name(String first_name) {
//                this.first_name = first_name;
//            }
//
//            public String getLast_name() {
//                return last_name;
//            }
//
//            public void setLast_name(String last_name) {
//                this.last_name = last_name;
//            }
//
//            public String getMobile() {
//                return mobile;
//            }
//
//            public void setMobile(String mobile) {
//                this.mobile = mobile;
//            }
//
//            public String getProfile_img() {
//                return profile_img;
//            }
//
//            public void setProfile_img(String profile_img) {
//                this.profile_img = profile_img;
//            }
//
//            public String getDriver_lat() {
//                return driver_lat;
//            }
//
//            public void setDriver_lat(String driver_lat) {
//                this.driver_lat = driver_lat;
//            }
//
//            public String getDriver_lang() {
//                return driver_lang;
//            }
//
//            public void setDriver_lang(String driver_lang) {
//                this.driver_lang = driver_lang;
//            }
//        }
//    }
}
