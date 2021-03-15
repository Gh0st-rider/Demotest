package com.sudrives.sudrivespartner.models;

import java.util.List;

public class AcceptedBookingModel {


    /**
     * status : 1
     * error_code : 0
     * error_line : 1895
     * booking_id :
     * result : {"userid":"277","tripid":"188","booking_info":{"id":"188","passenger":"278","booking_fee":"100","booking_id":"1873795298","book_reciever_name":"Manish","book_reciever_mobile":"8656566666","book_from_address":"27/4, Manorama Ganj, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.722567065424666","book_from_long":"75.887904278934","book_to_address":"Vijay Nagar, Indore, Madhya Pradesh 452010, India","book_to_lat":"22.753284800000003","book_to_long":"75.8936962","booking_status":"1","driver_arrived_pickup":"0","booking_status_name":"Accepted","booking_date":"1542191371","booking_start_dt":"0","booking_end_dt":"0","vehicle_name":"Pickup / 8ft","driver_arrived":"0","is_online_payment_accept":"0","create_dt":"1542191371","eta":"02:30 PM","minutes_to_reach":"30 Min","cancel_charge":"100","total_charge":"120","amount_to_pay":0,"total_fare":0,"base_fare":0,"total_distance":"4.0 km","total_time":"14 mins","trip_path":[{"id":"1486","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191644"},{"id":"1488","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191666"},{"id":"1490","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191676"},{"id":"1492","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191696"},{"id":"1495","driver_id":"277","trip_id":"188","lat":"22.726694","lang":"75.884132","comments":"","createDt":"1542191726"},{"id":"1497","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191741"},{"id":"1498","driver_id":"277","trip_id":"188","lat":"22.726709","lang":"75.884132","comments":"","createDt":"1542191779"},{"id":"1499","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884125","comments":"","createDt":"1542191789"},{"id":"1500","driver_id":"277","trip_id":"188","lat":"22.726707","lang":"75.884132","comments":"","createDt":"1542191812"},{"id":"1501","driver_id":"277","trip_id":"188","lat":"22.726698","lang":"75.884125","comments":"","createDt":"1542191824"},{"id":"1502","driver_id":"277","trip_id":"188","lat":"22.726686","lang":"75.884140","comments":"","createDt":"1542191840"},{"id":"1503","driver_id":"277","trip_id":"188","lat":"22.726690","lang":"75.884125","comments":"","createDt":"1542191863"},{"id":"1504","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191880"}],"vehicle_types":"6","trip_distance":"4.0 km","trip_time":"14 mins","payment_responce_data":"","driver_details":{"driver_id":"277","first_name":"Test","last_name":"User","mobile":"9987654321","profile_img":"http://52.33.111.26/api/media/user/default.png","vehicle_number":"yy","vehicle_name":"Pickup / 8ft","avg_rating":0,"driver_lat":"22.7266911","driver_lang":"75.8841299"},"user_details":{"user_id":"278","first_name":"Test","last_name":"User","mobile":"9987654321","profile_img":"http://52.33.111.26/api/media/user/default.png","driver_lat":"22.7266911","driver_lang":"75.8841299"}}}
     * trip_status : 1
     * message : Booking added successfully. Pending for driver approval
     */

    private int status;
    private int error_code;
    private int error_line;
    private String booking_id;
    private ResultBean result;
    private String trip_status;
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

    public String getTrip_status() {
        return trip_status;
    }

    public void setTrip_status(String trip_status) {
        this.trip_status = trip_status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public static class ResultBean {
        /**
         * userid : 277
         * tripid : 188
         * booking_info : {"id":"188","passenger":"278","booking_fee":"100","booking_id":"1873795298","book_reciever_name":"Manish","book_reciever_mobile":"8656566666","book_from_address":"27/4, Manorama Ganj, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.722567065424666","book_from_long":"75.887904278934","book_to_address":"Vijay Nagar, Indore, Madhya Pradesh 452010, India","book_to_lat":"22.753284800000003","book_to_long":"75.8936962","booking_status":"1","driver_arrived_pickup":"0","booking_status_name":"Accepted","booking_date":"1542191371","booking_start_dt":"0","booking_end_dt":"0","vehicle_name":"Pickup / 8ft","driver_arrived":"0","is_online_payment_accept":"0","create_dt":"1542191371","eta":"02:30 PM","minutes_to_reach":"30 Min","cancel_charge":"100","total_charge":"120","amount_to_pay":0,"total_fare":0,"base_fare":0,"total_distance":"4.0 km","total_time":"14 mins","trip_path":[{"id":"1486","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191644"},{"id":"1488","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191666"},{"id":"1490","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191676"},{"id":"1492","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191696"},{"id":"1495","driver_id":"277","trip_id":"188","lat":"22.726694","lang":"75.884132","comments":"","createDt":"1542191726"},{"id":"1497","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191741"},{"id":"1498","driver_id":"277","trip_id":"188","lat":"22.726709","lang":"75.884132","comments":"","createDt":"1542191779"},{"id":"1499","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884125","comments":"","createDt":"1542191789"},{"id":"1500","driver_id":"277","trip_id":"188","lat":"22.726707","lang":"75.884132","comments":"","createDt":"1542191812"},{"id":"1501","driver_id":"277","trip_id":"188","lat":"22.726698","lang":"75.884125","comments":"","createDt":"1542191824"},{"id":"1502","driver_id":"277","trip_id":"188","lat":"22.726686","lang":"75.884140","comments":"","createDt":"1542191840"},{"id":"1503","driver_id":"277","trip_id":"188","lat":"22.726690","lang":"75.884125","comments":"","createDt":"1542191863"},{"id":"1504","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191880"}],"vehicle_types":"6","trip_distance":"4.0 km","trip_time":"14 mins","payment_responce_data":"","driver_details":{"driver_id":"277","first_name":"Test","last_name":"User","mobile":"9987654321","profile_img":"http://52.33.111.26/api/media/user/default.png","vehicle_number":"yy","vehicle_name":"Pickup / 8ft","avg_rating":0,"driver_lat":"22.7266911","driver_lang":"75.8841299"},"user_details":{"user_id":"278","first_name":"Test","last_name":"User","mobile":"9987654321","profile_img":"http://52.33.111.26/api/media/user/default.png","driver_lat":"22.7266911","driver_lang":"75.8841299"}}
         */

        private String userid;
        private String tripid;
        private BookingInfoBean booking_info;

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getTripid() {
            return tripid;
        }

        public void setTripid(String tripid) {
            this.tripid = tripid;
        }

        public BookingInfoBean getBooking_info() {
            return booking_info;
        }

        public void setBooking_info(BookingInfoBean booking_info) {
            this.booking_info = booking_info;
        }

        public static class BookingInfoBean {
            /**
             * id : 188
             * passenger : 278
             * booking_fee : 100
             * booking_id : 1873795298
             * book_reciever_name : Manish
             * book_reciever_mobile : 8656566666
             * book_from_address : 27/4, Manorama Ganj, Indore, Madhya Pradesh 452001, India
             * book_from_lat : 22.722567065424666
             * book_from_long : 75.887904278934
             * book_to_address : Vijay Nagar, Indore, Madhya Pradesh 452010, India
             * book_to_lat : 22.753284800000003
             * book_to_long : 75.8936962
             * booking_status : 1
             * driver_arrived_pickup : 0
             * booking_status_name : Accepted
             * booking_date : 1542191371
             * booking_start_dt : 0
             * booking_end_dt : 0
             * vehicle_name : Pickup / 8ft
             * driver_arrived : 0
             * is_online_payment_accept : 0
             * create_dt : 1542191371
             * eta : 02:30 PM
             * minutes_to_reach : 30 Min
             * cancel_charge : 100
             * total_charge : 120
             * amount_to_pay : 0
             * total_fare : 0
             * base_fare : 0
             * total_distance : 4.0 km
             * total_time : 14 mins
             * trip_path : [{"id":"1486","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191644"},{"id":"1488","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191666"},{"id":"1490","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191676"},{"id":"1492","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191696"},{"id":"1495","driver_id":"277","trip_id":"188","lat":"22.726694","lang":"75.884132","comments":"","createDt":"1542191726"},{"id":"1497","driver_id":"277","trip_id":"188","lat":"22.726688","lang":"75.884140","comments":"","createDt":"1542191741"},{"id":"1498","driver_id":"277","trip_id":"188","lat":"22.726709","lang":"75.884132","comments":"","createDt":"1542191779"},{"id":"1499","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884125","comments":"","createDt":"1542191789"},{"id":"1500","driver_id":"277","trip_id":"188","lat":"22.726707","lang":"75.884132","comments":"","createDt":"1542191812"},{"id":"1501","driver_id":"277","trip_id":"188","lat":"22.726698","lang":"75.884125","comments":"","createDt":"1542191824"},{"id":"1502","driver_id":"277","trip_id":"188","lat":"22.726686","lang":"75.884140","comments":"","createDt":"1542191840"},{"id":"1503","driver_id":"277","trip_id":"188","lat":"22.726690","lang":"75.884125","comments":"","createDt":"1542191863"},{"id":"1504","driver_id":"277","trip_id":"188","lat":"22.726700","lang":"75.884132","comments":"","createDt":"1542191880"}]
             * vehicle_types : 6
             * trip_distance : 4.0 km
             * trip_time : 14 mins
             * payment_responce_data :
             * driver_details : {"driver_id":"277","first_name":"Test","last_name":"User","mobile":"9987654321","profile_img":"http://52.33.111.26/api/media/user/default.png","vehicle_number":"yy","vehicle_name":"Pickup / 8ft","avg_rating":0,"driver_lat":"22.7266911","driver_lang":"75.8841299"}
             * user_details : {"user_id":"278","first_name":"Test","last_name":"User","mobile":"9987654321","profile_img":"http://52.33.111.26/api/media/user/default.png","driver_lat":"22.7266911","driver_lang":"75.8841299"}
             */

            private String id;
            private String passenger;
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
            private float amount_to_pay;
            private float total_fare;
            private float base_fare;
            private String total_distance;
            private String total_time;
            private String vehicle_types;
            private String trip_distance;
            private String trip_time;
            private String type_of_booking;
            private String payment_responce_data;
            private DriverDetailsBean driver_details;
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

            public float getAmount_to_pay() {
                return amount_to_pay;
            }

            public void setAmount_to_pay(int amount_to_pay) {
                this.amount_to_pay = amount_to_pay;
            }

            public float getTotal_fare() {
                return total_fare;
            }

            public void setTotal_fare(int total_fare) {
                this.total_fare = total_fare;
            }

            public float getBase_fare() {
                return base_fare;
            }

            public void setBase_fare(int base_fare) {
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

            public void setAmount_to_pay(float amount_to_pay) {
                this.amount_to_pay = amount_to_pay;
            }

            public void setTotal_fare(float total_fare) {
                this.total_fare = total_fare;
            }

            public void setBase_fare(float base_fare) {
                this.base_fare = base_fare;
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
                /**
                 * driver_id : 277
                 * first_name : Test
                 * last_name : User
                 * mobile : 9987654321
                 * profile_img : http://52.33.111.26/api/media/user/default.png
                 * vehicle_number : yy
                 * vehicle_name : Pickup / 8ft
                 * avg_rating : 0
                 * driver_lat : 22.7266911
                 * driver_lang : 75.8841299
                 */

                private String driver_id;
                private String first_name;
                private String last_name;
                private String mobile;
                private String profile_img;
                private String vehicle_number;
                private String vehicle_name;
                private float avg_rating;
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

                public float getAvg_rating() {
                    return avg_rating;
                }

                public void setAvg_rating(int avg_rating) {
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
                /**
                 * user_id : 278
                 * first_name : Test
                 * last_name : User
                 * mobile : 9987654321
                 * profile_img : http://52.33.111.26/api/media/user/default.png
                 * driver_lat : 22.7266911
                 * driver_lang : 75.8841299
                 */

                private String user_id;
                private String first_name;
                private String last_name;
                private String mobile;
                private String profile_img;
                private float avg_rating;

                private String driver_lat;
                private String driver_lang;

                public float getAvg_rating() {
                    return avg_rating;
                }

                public void setAvg_rating(float avg_rating) {
                    this.avg_rating = avg_rating;
                }

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
                /**
                 * id : 1486
                 * driver_id : 277
                 * trip_id : 188
                 * lat : 22.726700
                 * lang : 75.884132
                 * comments :
                 * createDt : 1542191644
                 */

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
}
