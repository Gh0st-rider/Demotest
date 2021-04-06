package com.sudrives.sudrivespartner.utils;

import android.content.Context;
import android.media.MediaPlayer;

public class AppConstants {

    public static float ANGLE_VALUE = 0;
    public static String VERSION_CODE = "5";
    public static MediaPlayer mediaPlayer = new MediaPlayer();
    public static String Hitra = "com";
    public static final String EMAIL = "email";
    public static final String PROFILE_IMAGE = "profile_img";
    public static final String PROFILE_PIC = "profile_pic";
    public static final String TOPIC_ID = "topic_id";
    public static final String COMMENT = "comments";
    public static final String PICTURE = "picture";
    public static final String KEY_TRIP_TYPE = "trips_type";
    public static final String HIN = "Hin";
    public static final String ENG = "Eng";
    public static final String KEY_USER_ROLE = "user_role";
    public static final String BOOKING_ID = "booking_id";
    public static final String USER_EMAIL = "EMAILID";
    public static final String MOBILE_NUMBER = "MOBILENUMBER";
    public static final String TRIP_ID = "trip_id";
    public static final String KEY_MOBILE = "mobile";
    public static final String NOTIFICATION_ID = "notification_id";
    public static final String EARNING_DATA = "earningData";
    public static final String ANGLE = "angle";

    //Change on 25-07-2019
    public static String DEV_BASE_URL = "http://admin.sudrives.com/api/";//"http://www.iotnods.com/admin/api/";
    public static String DEV_SOCKET_URL = "http://103.56.36.39:3000";//"http://103.56.36.39:3000";//"http://13.235.134.181:3000/";
//    public static String DEV_SMS_KEY = "r/TOJWtaOlZ";

    //Account details key
    public static String KEY_HOLDER_NAME= "holdername";
    public static String KEY_BANK_NAME = "bankname";
    public static String KEY_IFSC = "ifsc";
    public static String KEY_ACCOUNT_NUMBER = "accountnumber";
    public static String KEY_ACCOUNTTYPE = "account_type";
    public static String KEY_UP_NUMBER = "up_number";
    public static String KEY_PAYTM_NUMBER = "paytm_number";


    //Production
    public static String PRO_BASE_URL = "http://admin.sudrives.com/api/";//"http://www.iotnods.com/admin/api/";
    public static String PRO_SOCKET_URL = "http://103.56.36.39:3000";//http://103.56.36.39:3000";//"http://13.235.134.181:3000/";
    //    public static String PRO_SMS_KEY = "dH+Z9qprZyr";
    public static String SMS_KEY = "";

    // Base URL
    public static final boolean isLive = true;
    public static final String BASE_URL = isLive ? PRO_BASE_URL : DEV_BASE_URL;
    public static final String BASE_SOCKET_URL = isLive ? PRO_SOCKET_URL : DEV_SOCKET_URL;


    public static String KEY_STATUS = "status";
    public static String KEY_MESSAGE = "message";
    public static String KEY_ERROR_CODE = "error_code";
    public static String KEY_NO_POPUP = "no_popup";
    public static String KEY_ERROR_LINE = "error_code";

    public static String KEY_FARE = "fare";
    public static String KEY_DISTANCE = "distance";


    public static String KEY_APP_VERSION = "version";
    public static String KEY_TOKEN = "token";
    public static String KEY_USER_ID = "userid";
    public static String KEY_DRIVERID = "driverid";
    public static String KEY_RESULT = "result";
    public static String KEY_DRIVER_ID = "driver_id";
    public static String KEY_LANGUAGE = "language";
    public static String KEY_LANGU = "lang";
    public static String KEY_TYPE_FOR = "typefor";
    public static String KEY_CANCEL_ORDER_ID = "cancel_order_id";
    public static String KEY_CANCEL_MSG = "cancel_msg";
    public static String KEY_TIMEZONE = "timeZone";
    public static String KEY_CONTENT_TYPE = "Content-Type";

    public static String KEY_SUBSCRIPTION_PLAN_ID = "subscription_plan_id";
    public static String KEY_ONLINE_PAYMENT_ID = "online_payment_id";
    public static String KEY_ONLINE_PAYMENT_STATUS="online_payment_status";
    public static String KEY_ONLINE_PAYMENT_AMOUNT ="online_payment_amount";

    public static int SHOW_BOOKING_POPUP = 0;

    public static String DRIVE_USER_ID = "user_id";
    public static String KEY_FILTER = "filter";

    public static String KEY_LAT = "lat";
    public static String KEY_LANG = "lang";
    public static String KEY_TRIPID = "tripid";
    public static String KEY_TRIP_ID = "trip_id";
    public static String KEY_RATING_VOTE = "rating_vote";
    public static String KEY_COMMENT = "comment";
    public static String KEY_IMG_BILTI = "img_bilti";


    public static String KEY_FILE = "file";


    /*
     * AppPreferecnce key
     * */
    public static final String KEY_DEVICE_FCM_TOKEN = "fcmtoken";
    public static final String KEY_DEVICE_TYPE = "devicetype";
    public static final String KEY_ID = "camera_id";
    public static final String KEY_LOCATION_ID = "loc_id";
    public static final String KEY_LOCATION_NAME = "name";

    /*
     * weather api
     * */

    public static final String WEATHER_API_KEY = "511359073acf0d7cfcb8d729b8df4302";
    public static final String EVENT_WEATHER_REPOSNE = "WeatherResponse";


    public static final String API_CHECK_VERSION = BASE_URL + "api/check_version_driver";
    public static final String EVENT_API_CHECK_VERSION = "api/check_version_driver";

    public static final String API_CHECK_HEALTH = BASE_URL + "driver/driver_health";
    public static final String EVENT_API_CHECK_HEALTH = "driver/driver_health";

    public static final String API_MOBLINE_NUMBER_CHECK = BASE_URL + "api/mobileNumberCheckDriver";
    public static final String EVENT_API_MOBLINE_NUMBER_CHECK = "api/mobileNumberCheckDriver";


    public static final String API_GET_TYPE = BASE_URL + "CommonController/get_types";
    public static final String EVENT_API_GET_TYPE = "CommonController/get_types";

    public static final String API_GET_MAXBOOKINGADDRESS = BASE_URL + "api/getMaxBookingAddress";
    public static final String EVENT_API_GET_MAXBOOKINGADDRESS = "api/getMaxBookingAddress";


    public static final String API_GET_VEHICLE_NAME = BASE_URL + "api/getVehiclesNameList";
    public static final String EVENT_API_GET_VEHICLE_NAME = "api/getVehiclesNameList";


    public static final String API_STATE = BASE_URL + "api/getStates";
    public static final String EVENT_API_STATE = "api/getStates";

    public static final String API_CITY = BASE_URL + "api/getCities";
    public static final String EVENT_API_CITY = "api/getCities";

    public static final String API_OTP_VERIFY_NEW = BASE_URL + "api/otp_verify_new";
    public static final String EVENT_API_OTP_VERIFY_NEW = "api/otp_verify_new";


    public static final String API_CHANGE_MODE_TO_DRIVER = BASE_URL + "api/change_mode_to_driver";
    public static final String EVENT_API_CHANGE_MODE_TO_DRIVER = "api/change_mode_to_driver";

    public static final String API_REGISTRATION = BASE_URL + "driver/registration";
    public static final String EVENT_API_REGISTRATION = "driver/registration";

    public static final String API_REGISTRATION_THREE = BASE_URL + "driver/registration_three";
    public static final String EVENT_API_REGISTRATION_THREE = "driver/registration_three";


    public static final String API_UPLOAD_PROFILE_IMAGE = BASE_URL + "Driver/upload_profile_image";
    public static final String EVENT_API_UPLOAD_PROFILE_IMAGE = "Driver/upload_profile_image";


    public static final String API_UPLOAD_BILTI = BASE_URL + "api/uploadBilti";
    public static final String EVENT_API_UPLOAD_BILTI = "api/uploadBilti";


    public static final String API_VERIFICATION = BASE_URL + "api/mobile_verification";
    public static final String EVENT_API_VERIFICATION = "api/mobile_verification";

    public static final String API_REGISTRATION_TWO = BASE_URL + "driver/registration_two";
    public static final String EVENT_API_REGISTRATION_TWO = "driver/registration_two";


    public static final String API_LOGIN = BASE_URL + "api/otp_verify";
    public static final String EVENT_API_LOGIN = "api/otp_verify";


    public static final String PROFILE_DETAIl = BASE_URL + "driver/get_profile";
    public static final String EVENT_PROFILE_DETAIL = "driver/get_profile";

    public static final String PROFILE_UPDATE = BASE_URL + "driver/profile_update";
    public static final String EVENT_PROFILE_UPDATE = "driver/profile_update";

    public static final String USER_BOOKINGS = BASE_URL + "SocketApi/user_trips";
    public static final String EVENT_USER_BOOKINGS = "SocketApi/user_trips";

    public static final String GET_TYPE = BASE_URL + "CommonController/get_types";
    public static final String EVENT_GET_TYPE = "CommonController/get_types";

    public static final String REPORT_ISSUE = BASE_URL + "driver/report_issue";
    public static final String EVENT_REPORT_ISSUE = "driver/report_issue";

    public static final String ABOUT_US = BASE_URL + "CommonController/pages";
    public static final String EVENT_ABOUT_US = "CommonController/pages";

    public static final String DAILY_COLLECTION = BASE_URL + "driver/dailyCollections";
    public static final String EVENT_DAILY_COLLECTION = "driver/dailyCollections";

    public static final String NOTIFICATION_LIST = BASE_URL + "api/get_notifications";
    public static final String EVENT_NOTIFICATION_LIST = "api/get_notifications";

    public static final String NOTIFICATION_READ = BASE_URL + "api/notification_read";
    public static final String EVENT_NOTIFICATION_READ = "api/notification_read";


    public static final String STATIC_PAGE = BASE_URL + "CommonController/pages";
    public static final String EVENT_STATIC_PAGE = "CommonController/pages";

    public static final String CONTACT_US = BASE_URL + "CommonController/get_contact_info";
    public static final String EVENT_CONTACT_US = "CommonController/get_contact_info";

    public static final String REPORT_ISSUE_LIST = BASE_URL + "api/get_report_issue";
    public static final String EVENT_REPORT_ISSUE_LIST = "api/get_report_issue";

    public static final String CLEAR_NOTIFICATION = BASE_URL + "api/clear_notification";
    public static final String EVENT_CLEAR_NOTIFICATION = "api/clear_notification";


    public static final String DONATE_AMOUNT = BASE_URL + "api/donateAmount";
    public static final String EVENT_DONATE_AMOUNT = "api/donateAmount";


    //Login


    //Registration
    public static String KEY_TYPE = "type";

    public static String KEY_OTP = "otp";

    public static String KEY_DRIVING_LICENSE = "driving_license";
    public static String KEY_RC_NUMBER = "rc_number";


    //online
    public static String KEY_DRIVER_STATUS = "driver_status";


    //Contact Us
    public static String KEY_PAGE_NAME = "page_name";
    public static String KEY_CONTACT_US = "contact-us";

    //About Us
    public static String KEY_TYPES = "types";
    public static String KEY_ABOUT_US = "about_us";
    public static String KEY_VALUE_TRIPID = "0";

    public static String KEY_PAGE_NO = "page";


    //Static Key

    public static String KEY_VALUE_MOBILE = "mobile";
    public static String KEY_VALUE_FIRST_NAME = "firstname";
    public static String KEY_VALUE_FULL_NAME = "firstname";
    public static String KEY_VALUE_LAST_NAME = "lastname";
    public static String KEY_VALUE_VEHICLE_NO = "vehicle_number";
    public static String KEY_VALUE_LOCATION = "address";
    public static String KEY_VALUE_VEHICLE_TYPE = "vehicle_types";
    public static String KEY_VALUE_ABOUT_US = "hear_us";
    public static String KEY_VALUE_DRIVING_LICENSE_PATH = "driving_license";
    public static String KEY_VALUE_DRIVING_LICENSE_RC_PATH = "rc_number";
    public static String KEY_VALUE_POLLUTION = "pollution";
    public static String KEY_VALUE_ADHAR_CARD = "aadhar_card";
    public static String KEY_VALUE_ROAD_TAX = "road_tax";
    public static String KEY_VALUE_FITNESS_CERTIFICATE = "fitness_certificate";
    public static String KEY_VALUE_INSURANCE = "insurance";
    public static String KEY_VALUE_PERMIT = "permit";


    public static String KEY_VALUE_DAILY = "daily";
    public static String KEY_VALUE_RENTAL = "rental";
    public static String KEY_VALUE_OUTSTATION = "outstation";

    public static String KEY_latitude = "latitude";
    public static String KEY_VALUE_longitude = "longitude";
    public static String KEY_VALUE_home_address = "home_address";
    public static String KEY_VALUE_city = "city";
    public static String KEY_VALUE_state = "state";


    public static String KEY_VALUE_ACCEPT = "Accept";
    public static String KEY_VALUE_REJECT = "Reject";

    public static String KEY_VALUE_DRIVER_INFO = "driverInfo";

    public static String KEY_VALUE_LOGIN = "login";
    public static String KEY_VALUE_REGISTER = "register";
    public static String KEY_VALUE_ENGLISH = "english";
    public static String KEY_VALUE_HINDI = "hindi";
    public static String KEY_VALUE_ONLINE = "Online";
    public static String KEY_VALUE_OFFLINE = "Offline";
    public static String KEY_VALUE_PROFILE_UPDATE = "ProfileUpdate";
    public static String KEY_VALUE_DRIVER = "driver";
    public static String KEY_START_TRIP = "start_otp";

    public static String KEY_VALUE_UPLOAD_BILTI = "uploadBilti";
    public static String KEY_VALUE_RATE_USER = "rateUser";


    public static Context KEY_CONTEXT;


    //payment KEy
    public static String KEY_PAYMENT_TYPE = "payment_type";
    public static String KEY_AMOUNT = "amount";
    public static String KEY_USING_TYPE = "using_type";


    //Socket
//    EMIT
    public static String EMIT_GET_PROFILE = "get_profile";
    public static String EMIT_GET_TRIP_DETAILS = "get_trip_details";
    public static String EMIT_GET_RATING_TO_USER = "get_rating_to_user";
    public static String EMIT_ONLIE_OFFLINE = "get_driver_online_offline";
    public static String EMIT_GET_DRIVER_CURRENT_LOCATION_UPDATE = "get_driver_current_location_update";
    public static String EMIT_GET_BOOKING_ACCEPT = "get_booking_accept";
    public static String EMIT_GET_TRIP_START = "get_trip_start";
    public static String EMIT_GET_TRIP_END = "get_trip_end";
    public static String EMIT_GET_TRIP_END_SEND_SMS = "get_trip_end_send_sms";
    public static String EMIT_ROOM = "room";
    public static String EMIT_DISCONNECT = "disconnect";
    public static String EMIT_GET_DRIVER_ARRIVED_AT_PICKUP = "get_driver_arrived_at_pickup";
    public static String EMIT_GET_TRIP_CANCEL = "get_trip_cancel";
    public static String EMIT_GET_DRIVER_LAST_TRIP_STATUS = "get_driver_last_trip_status";
    public static String EMIT_GET_NOTIFICATION_UNREAD_COUNT = "get_notification_unread_count";
    public static String EMIT_GET_LOGOUT = "get_logout";

    //On
    public static String ON_GET_PROFILE = "responce_profile";
    public static String ON_ONLINE_OFFLINE = "responce_driver_online_offline";
    public static String ON_CHANGE_DESTINATION_ADDRESS_DRIVER = "responce_change_destination_address_driver";
    public static String ON_RESPONSE_NEW_BOOKING_NOTIFICATION = "responce_new_booking_notification";
    public static String ON_RESPONSE_BOOKING_ACCEPT = "responce_booking_accept";
    public static String ON_RESPONSE_BOOKING_ARRIVED_AT_PICKUP = "responce_booking_arrived_at_pickup";
    public static String ON_RESPONSE_TRIP_START = "responce_trip_start";
    public static String ON_RESPONSE_TRIP_END = "responce_trip_end";
    public static String ON_RESPONSE_TRIP_CANCEL = "responce_trip_cancel";
    public static String ON_RESPONSE_DRIVER_LAST_TRIP_STATUS = "responce_driver_last_trip_status";
    public static String ON_TRIP_END_SEND_SMS = "responce_trip_end_send_sms";
    public static String ON_RESPONCE_RATING_TO_USER = "responce_rating_to_user";
    public static String ON_GET_NOTIFICATION_UNREAD = "responce_notification_unread_count";
    public static String ON_RESPONCE_LOGOUT = "responce_logout";


    //locations

    public static final int SUCCESS_RESULT = 0;

    public static final int FAILURE_RESULT = 1;

    public static final String PACKAGE_NAME = "com.move_cabs.driver";

    public static final String RECEIVER = PACKAGE_NAME + ".RECEIVER";

    public static final String RESULT_DATA_KEY = PACKAGE_NAME + ".RESULT_DATA_KEY";

    public static final String LOCATION_DATA_EXTRA = PACKAGE_NAME + ".LOCATION_DATA_EXTRA";

    public static final String LOCATION_DATA_AREA = PACKAGE_NAME + ".LOCATION_DATA_AREA";
    public static final String LOCATION_DATA_CITY = PACKAGE_NAME + ".LOCATION_DATA_CITY";
    public static final String LOCATION_DATA_STREET = PACKAGE_NAME + ".LOCATION_DATA_STREET";


    public static String ONPAUSE = "onpause";
    public static String notificationCount = "0";

    public static String KEY_VEHICLE_NAME = "vehicle_name";

    public static double VALUE_LATITUDE = 0;
    public static double VALUE_LONGITUDE = 0;
    public static int notificationId = 10;


    public static final String GET_PERDAY_AMOUNT = BASE_URL + "driver/driver_today_earning";
    public static final String GET_DOCUMENTS = BASE_URL + "driver/fetchDocument";

    // new api added

    public static final String GET_WALLET_DETAILS = BASE_URL + "driver/fetchDriverWallet";
    public static final String WALLET_DETAILS = "driver/get_profile";

    public static final String GET_ACCOUNT_DETAILS = BASE_URL + "driver/getaccountdetails";
    public static final String ACCOUNT_DETAILS_TAG = "driver/getaccountdetails";

    public static final String SAVE_ACCOUNT_DETAILS = BASE_URL + "driver/savedriveraccount";
    public static final String SAVE_ACCOUNT_DETAILS_TAG = "driver/savedriveraccount";

    public static final String UPDATE_ACCOUNT_DETAILS = BASE_URL + "driver/updatedriveraccount";
    public static final String UPDATE_ACCOUNT_DETAILS_TAG = "driver/updatedriveraccount";
    public static final String KEY_ACCOUNT_ID = "id";

    public static final String WITHDRAW_MONEY = BASE_URL + "driver/saveWithdrawalRequest";
    public static final String WITHDRAW_MONEY_TAG = "driver/saveWithdrawalRequest";

    public static final String SAVE_DRIVER_ACCOUNT_DETAILS = BASE_URL + "driver/savedriveraccount";
    public static final String EVENT_SAVE_DRIVER_ACCOUNT_DETAILS = "driver/savedriveraccount";


    public static final String GET_DRIVER_ACCOUNT_DETAILS = BASE_URL + "driver/getaccountdetails";
    public static final String EVENT_GET_DRIVER_ACCOUNT_DETAILS = "driver/getaccountdetails";

    public static final String UPDATE_DRIVER_ACCOUNT_DETAILS = BASE_URL + "driver/updatedriveraccount";
    public static final String EVENT_UPDATE_DRIVER_ACCOUNT_DETAILS = "driver/updatedriveraccount";

    public static final String REQUEST_CASHOUT= BASE_URL + "driver/saveWithdrawalRequest";
    public static final String EVENT_REQUEST_CASHOUT = "driver/saveWithdrawalRequest";

    public static final String REQUEST_CASHOUT_DETAILS = BASE_URL + "driver/driver_cashout_details";
    public static final String EVENT_REQUEST_CASHOUT_DETAILS="driver/driver_cashout_details";

    //driver Home status Api
    public static final String UPDATE_DRIVER_HOME_STATUS = BASE_URL + "driver/changehomestatus";
    public static final String GET_DRIVER_HOME_STATUS = BASE_URL + "driver/fetchHomestatus";
    public static final String DRIVER_HOME_STATUS = "driver/fetchHomestatus";
    public static final String DRIVER_HOME_STATUS_GET = "driver/changehomestatus";


    public static final String API_SUBSCRIPTION_PLANS_LIST = BASE_URL + "driver/fetch_all_subscription_plan";
    public static final String API_BUY_SUBSCRIPTION = BASE_URL + "driver/buy_subscription_plan";


    //payment gateway
    public static final  String API_GENERATE_ORDERID = BASE_URL + "driver/generaterazorpayorder";
    public static final  String EVENT_API_GENERATE_ORDERID = "driver/generaterazorpayorder";


    //get subscription Active list
    public static final  String API_FETCH_ACTIVE_SUBSCRIPTION = BASE_URL + "driver/fetch_drive_subscription_plan";
    public static final  String EVENT_FETCH_ACTIVE_SUBSCRIPTION = "driver/fetch_drive_subscription_plan";

    //get cashout list
    public static final  String API_FETCH_CASHOUT_REQ = BASE_URL + "driver/driver_coshout_list";
    public static final  String EVENT_API_FETCH_CASHOUT_REQ = "driver/driver_coshout_list";

}