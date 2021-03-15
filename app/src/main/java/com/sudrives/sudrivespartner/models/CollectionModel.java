package com.sudrives.sudrivespartner.models;

import java.util.List;

/**
 * Created by pankaj on 10/30/18.
 */

public class CollectionModel {


    /**
     * status : 1
     * error_code : 1
     * error_line : 516
     * result : [{"id":"65","passenger":"405","driver_id":"402","booking_fee":"100","booking_id":"1931078078","book_reciever_name":"6666676776","book_reciever_mobile":"6666676776","book_from_address":"Aashay Complex, 56 Dukan St, Behind 56 Dukan, 11 Bungalow Colony, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.724298258434708","book_from_long":"75.8842983841896","book_to_address":"29, Mill Road, Dewas, Madhya Pradesh 455001, India","book_to_lat":"22.962267199999996","book_to_long":"76.0507949","booking_status":"3","driver_arrived_pickup":"1","booking_status_name":"Completed","booking_date":"1542460683","booking_start_dt":"1542460732","booking_end_dt":"1542637154","vehicle_name":"HL 1","driver_arrived":"1542460699","is_online_payment_accept":"0","create_dt":"1542460683","eta":"07:51 PM","minutes_to_reach":"5 min","cancel_charge":"150","total_charge":"120","amount_to_pay":"738.5","total_fare":"738.5","base_fare":"180","total_distance":"37.7 km","total_time":"53 mins","trip_path":[{"id":"654","driver_id":"402","trip_id":"65","lat":"22.726700","lang":"75.884140","comments":"","createDt":"1542460752"},{"id":"655","driver_id":"402","trip_id":"65","lat":"22.726492","lang":"75.884644","comments":"","createDt":"1542460763"},{"id":"656","driver_id":"402","trip_id":"65","lat":"22.726545","lang":"75.884552","comments":"","createDt":"1542460774"},{"id":"657","driver_id":"402","trip_id":"65","lat":"22.726606","lang":"75.884445","comments":"","createDt":"1542460784"}],"vehicle_types":"1","trip_distance":"37.7 km","trip_time":"53 mins","payment_responce_data":"","driver_details":{"driver_id":"402","first_name":"Archana","last_name":"Patidar","mobile":"8305647729","profile_img":"http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png","vehicle_number":"mp09qw6458","vehicle_name":"HL 1","avg_rating":"0","driver_lat":"22.7267333","driver_lang":"75.8841483"},"user_details":{"user_id":"405","first_name":"Archana","last_name":"Patidar","mobile":"8305647729","profile_img":"http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png","driver_lat":"22.7267333","driver_lang":"75.8841483"}},{"id":"67","passenger":"405","driver_id":"402","booking_fee":"100","booking_id":"593540972","book_reciever_name":"6666666666","book_reciever_mobile":"6666666666","book_from_address":"Aashay Complex, 56 Dukan St, Behind 56 Dukan, 11 Bungalow Colony, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.724298258434708","book_from_long":"75.8842983841896","book_to_address":"29, Mill Road, Dewas, Madhya Pradesh 455001, India","book_to_lat":"22.962267199999996","book_to_long":"76.0507949","booking_status":"3","driver_arrived_pickup":"1","booking_status_name":"Completed","booking_date":"1542460932","booking_start_dt":"1542460945","booking_end_dt":"1542637154","vehicle_name":"HL 3","driver_arrived":"1542460943","is_online_payment_accept":"0","create_dt":"1542460932","eta":"07:51 PM","minutes_to_reach":"5 min","cancel_charge":"150","total_charge":"120","amount_to_pay":"0","total_fare":"0","base_fare":"0","total_distance":"37.7 km","total_time":"53 mins","trip_path":[],"vehicle_types":"4","trip_distance":"37.7 km","trip_time":"53 mins","payment_responce_data":"","driver_details":{"driver_id":"402","first_name":"Archana","last_name":"Patidar","mobile":"8305647729","profile_img":"http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png","vehicle_number":"mp09qw6458","vehicle_name":"HL 3","avg_rating":"0","driver_lat":"22.7267333","driver_lang":"75.8841483"},"user_details":{"user_id":"405","first_name":"Archana","last_name":"Patidar","mobile":"8305647729","profile_img":"http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png","driver_lat":"22.7267333","driver_lang":"75.8841483"}}]
     * amount_earned_today : 200
     * total_online_payment : 0
     * total_cash_payment : 200
     * trip_count : 2
     * message :
     */

    private int status;
    private int error_code;
    private int error_line;
    private String amount_earned_today;
    private String total_online_payment;
    private String total_cash_payment;
    private String total_donations;
    private int trip_count;
    private String message;

    public String getTotal_donations() {
        return total_donations;
    }

    public void setTotal_donations(String total_donations) {
        this.total_donations = total_donations;
    }

    /**
     * id : 65
     * passenger : 405
     * driver_id : 402
     * booking_fee : 100
     * booking_id : 1931078078
     * book_reciever_name : 6666676776
     * book_reciever_mobile : 6666676776
     * book_from_address : Aashay Complex, 56 Dukan St, Behind 56 Dukan, 11 Bungalow Colony, New Palasia, Indore, Madhya Pradesh 452001, India
     * book_from_lat : 22.724298258434708
     * book_from_long : 75.8842983841896
     * book_to_address : 29, Mill Road, Dewas, Madhya Pradesh 455001, India
     * book_to_lat : 22.962267199999996
     * book_to_long : 76.0507949
     * booking_status : 3
     * driver_arrived_pickup : 1
     * booking_status_name : Completed
     * booking_date : 1542460683
     * booking_start_dt : 1542460732
     * booking_end_dt : 1542637154
     * vehicle_name : HL 1
     * driver_arrived : 1542460699
     * is_online_payment_accept : 0
     * create_dt : 1542460683
     * eta : 07:51 PM
     * minutes_to_reach : 5 min
     * cancel_charge : 150
     * total_charge : 120
     * amount_to_pay : 738.5
     * total_fare : 738.5
     * base_fare : 180
     * total_distance : 37.7 km
     * total_time : 53 mins
     * trip_path : [{"id":"654","driver_id":"402","trip_id":"65","lat":"22.726700","lang":"75.884140","comments":"","createDt":"1542460752"},{"id":"655","driver_id":"402","trip_id":"65","lat":"22.726492","lang":"75.884644","comments":"","createDt":"1542460763"},{"id":"656","driver_id":"402","trip_id":"65","lat":"22.726545","lang":"75.884552","comments":"","createDt":"1542460774"},{"id":"657","driver_id":"402","trip_id":"65","lat":"22.726606","lang":"75.884445","comments":"","createDt":"1542460784"}]
     * vehicle_types : 1
     * trip_distance : 37.7 km
     * trip_time : 53 mins
     * payment_responce_data :
     * driver_details : {"driver_id":"402","first_name":"Archana","last_name":"Patidar","mobile":"8305647729","profile_img":"http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png","vehicle_number":"mp09qw6458","vehicle_name":"HL 1","avg_rating":"0","driver_lat":"22.7267333","driver_lang":"75.8841483"}
     * user_details : {"user_id":"405","first_name":"Archana","last_name":"Patidar","mobile":"8305647729","profile_img":"http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png","driver_lat":"22.7267333","driver_lang":"75.8841483"}
     */

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

    public String getAmount_earned_today() {
        return amount_earned_today;
    }

    public void setAmount_earned_today(String amount_earned_today) {
        this.amount_earned_today = amount_earned_today;
    }

    public String getTotal_online_payment() {
        return total_online_payment;
    }

    public void setTotal_online_payment(String total_online_payment) {
        this.total_online_payment = total_online_payment;
    }

    public String getTotal_cash_payment() {
        return total_cash_payment;
    }

    public void setTotal_cash_payment(String total_cash_payment) {
        this.total_cash_payment = total_cash_payment;
    }

    public int getTrip_count() {
        return trip_count;
    }

    public void setTrip_count(int trip_count) {
        this.trip_count = trip_count;
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

    public static class ResultBean {
        private String id;
        private String passenger;
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
        private String booking_date_time;
        private String trip_donations;
        private String type_of_booking;

        public String getTrip_donations() {
            return trip_donations;
        }

        public void setTrip_donations(String trip_donations) {
            this.trip_donations = trip_donations;
        }

        public String getBooking_date_time() {
            return booking_date_time;
        }

        public void setBooking_date_time(String booking_date_time) {
            this.booking_date_time = booking_date_time;
        }

        /**
         * driver_id : 402
         * first_name : Archana
         * last_name : Patidar
         * mobile : 8305647729
         * profile_img : http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png
         * vehicle_number : mp09qw6458
         * vehicle_name : HL 1
         * avg_rating : 0
         * driver_lat : 22.7267333
         * driver_lang : 75.8841483
         */

        private DriverDetailsBean driver_details;
        /**
         * user_id : 405
         * first_name : Archana
         * last_name : Patidar
         * mobile : 8305647729
         * profile_img : http://52.33.111.26/api/media/user/e7ad52303ba6d41e6d312331849c30fc.png
         * driver_lat : 22.7267333
         * driver_lang : 75.8841483
         */

        private UserDetailsBean user_details;
        /**
         * id : 654
         * driver_id : 402
         * trip_id : 65
         * lat : 22.726700
         * lang : 75.884140
         * comments :
         * createDt : 1542460752
         */

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

        public String getType_of_booking() {
            return type_of_booking;
        }

        public void setType_of_booking(String type_of_booking) {
            this.type_of_booking = type_of_booking;
        }

        public List<TripPathBean> getTrip_path() {
            return trip_path;
        }

        public void setTrip_path(List<TripPathBean> trip_path) {
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

        public static class TripPathBean {
            private String id;
            private String driver_id;
            private String trip_id;
            private String lat;
            private String lang;
            private String comments;
            private String createDt;

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

            public String getCreateDt() {
                return createDt;
            }

            public void setCreateDt(String createDt) {
                this.createDt = createDt;
            }
        }
    }
}
