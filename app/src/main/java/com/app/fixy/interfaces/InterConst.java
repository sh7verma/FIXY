package com.app.fixy.interfaces;

public interface InterConst {

    int PICKUP_ADD = 1;
    int DELIVERY_ADD = 2;
    int ANT_GO = 1;
    int ANT_EXPRESS = 2;
    int ZERO = 0;
    int ONE = 1;
    int TWO = 2;

    String NUMBER_REGISTERED = "number_registered";
    String EXTRA = "extra";
    String EXTRA2 = "extra2";
    String READ_PHONE_STATE_PERMISSION = "phone_state";
    String LOCATION_DATA_EXTRA = "location_extra";
    String RECEIVER = "reciver_location";
    String RESULT_DATA_KEY = ".RESULT_DATA_KEY";
    String SUCCESS_RESULT = "200";

    int HOME = 1;
    int WORK = 2;
    int DEFAULT = -1;
    int OTHER = 3;

    int FRAG_NULL = 10;
    int FRAG_HOME = 0;
    int FRAG_BOOKINGS = 1;
    int FRAG_COINS = 2;
    int FRAG_PROFILE = 3;

    String ACCESS_TOKEN = "access_token";
    String COUNTRY_CODE = "country_code";
    String PHONE_NUMBER = "phone_number";
    String GENDER = "gender";
    String PROFILE_STATUS = "profile_status";
    String PROFILE_IMAGE = "profile_image";
    String EMAIL = "email";
    String USER_NAME = "username";
    String USER_ID = "user_id";
    String ERROR_RESULT = "404";
    int CAMERA_PERMISSION_REQUEST_CODE = 1;
    String SHOW_PIC = "show_pic";
    String NULL = "null";
    String TYPE = "type";
    String EDIT_PROFILE = "1";
    String CREATE_PROFILE = "0";
    String MALE = "male";
    String FEMALE = "female";
    String USER_TYPE = "1";
    String APPLICATION_MODE = "1";
    String PLATFORM_TYPE = "1";
    String PROFILE_IS_CREATED = "4";


    String CITY_NAME = "city_name";
    String CITY_ID = "city_id";
    String NUMBER_IS_REGISTERED = "1";

    String STATUS_PENDING_REQUEST = "0";
    String STATUS_BOOKING_REQUEST = "1";

    String FRAG_MY_REQUEST_CLICK = "FRAG_MY_REQUEST_CLICK";

    public enum WEBVIEW {
        TERM_CONDITION;
    }

    public enum GOOGLE {
        NEARBY, AUTOCOMPLETE
    }

    public enum Address {
        HOME, WORK, OTHER
    }
}
