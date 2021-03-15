package com.sudrives.sudrivespartner.models;

import java.util.List;

public class BookingDetailsBeans {


    /**
     * status : 1
     * error_code : 0
     * error_line : 1047
     * result : [{"id":"33","booking_fee":"0","booking_id":"899768788","book_reciever_name":"hhh","book_reciever_mobile":"5555535555","book_from_address":"15/8, New Palasia, Indore, Madhya Pradesh 452001, India","book_from_lat":"22.731566988898145","book_from_long":"75.82478620111942","book_to_address":"Vijay Nagar, Indore, Madhya Pradesh 452010, India","book_to_lat":"22.753284800000003","book_to_long":"75.8936962","booking_status":"5","booking_date":"1541081446","booking_start_dt":"0","booking_end_dt":"0","driver_arrived_pickup":"0","driver_arrived":"0","is_online_payment_accept":"0","create_dt":"1541081446","eta":"02:30 PM","minutes_to_reach":"30 Min","driver_details":[],"user_details":{"user_id":"84","first_name":"Sourabh","last_name":"Gajbhiye","mobile":"7509875098","email":"fgu@guy.fhj","profile_img":"http://52.33.111.26/api/media/user/36a06328680cf98713df3b88dc31deae.PNG"}}]
     * message :
     */

    public int status;
    public int error_code;
    public int error_line;
    public String message;
    public List<ResultBean> result;

    public static class ResultBean {
        /**
         * id : 33
         * booking_fee : 0
         * booking_id : 899768788
         * book_reciever_name : hhh
         * book_reciever_mobile : 5555535555
         * book_from_address : 15/8, New Palasia, Indore, Madhya Pradesh 452001, India
         * book_from_lat : 22.731566988898145
         * book_from_long : 75.82478620111942
         * book_to_address : Vijay Nagar, Indore, Madhya Pradesh 452010, India
         * book_to_lat : 22.753284800000003
         * book_to_long : 75.8936962
         * booking_status : 5
         * booking_date : 1541081446
         * booking_start_dt : 0
         * booking_end_dt : 0
         * driver_arrived_pickup : 0
         * driver_arrived : 0
         * is_online_payment_accept : 0
         * create_dt : 1541081446
         * eta : 02:30 PM
         * minutes_to_reach : 30 Min
         * driver_details : []
         * user_details : {"user_id":"84","first_name":"Sourabh","last_name":"Gajbhiye","mobile":"7509875098","email":"fgu@guy.fhj","profile_img":"http://52.33.111.26/api/media/user/36a06328680cf98713df3b88dc31deae.PNG"}
         */

        public String id;
        public String booking_fee;
        public String booking_id;
        public String book_reciever_name;
        public String book_reciever_mobile;
        public String book_from_address;
        public String book_from_lat;
        public String book_from_long;
        public String book_to_address;
        public String book_to_lat;
        public String book_to_long;
        public String booking_status;
        public String booking_date;
        public String payment_id;
        public String booking_start_dt;
        public String booking_end_dt;
        public String driver_arrived_pickup;
        public String driver_arrived;
        public String vehicle_name;
        public String vehicle_img;
        public String is_online_payment_accept;
        public String create_dt;
        public String eta;
        public String total_fare;
        public String type_of_booking;
        public String minutes_to_reach;
        public UserDetailsBean user_details;
        public List<?> driver_details;

        public static class UserDetailsBean {
            /**
             * user_id : 84
             * first_name : Sourabh
             * last_name : Gajbhiye
             * mobile : 7509875098
             * email : fgu@guy.fhj
             * profile_img : http://52.33.111.26/api/media/user/36a06328680cf98713df3b88dc31deae.PNG
             */

            public String user_id;
            public String first_name;
            public String last_name;
            public String mobile;
            public String email;
            public String profile_img;
        }
    }
}
