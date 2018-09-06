package com.app.fixy.interfaces;

public interface InterConst {

    public int PICKUP_ADD = 1;
    public int DELIVERY_ADD = 2;
    public int ANT_GO = 1;
    public int ANT_EXPRESS = 2;
    public int ZERO = 0;
    public int ONE = 1;
    public int TWO = 2;
    public String NUMBER_REGISTERED = "number_registered";
    public String EXTRA = "extra";
    public String EXTRA2 = "extra2";
    String READ_PHONE_STATE_PERMISSION= "phone_state";
    String LOCATION_DATA_EXTRA="location_extra";
    String RECEIVER ="reciver_location";
    String RESULT_DATA_KEY = ".RESULT_DATA_KEY";
    int SUCCESS_RESULT = 200;
    int HOME = 1;
    int WORK= 2;
    int DEFAULT= -1;
    int OTHER = 3;
    String ACCESS_TOKEN = "access_token";
    String COUNTRY_CODE = "country_code";
    String PHONE_NUMBER = "phone_number";
    String GENDER = "gender";
    String PROFILE_STATUS = "profile_status";
    String PROFILE_IMAGE = "profile_image";
    String EMAIL = "email";
    String USER_NAME = "username";
    String USER_ID = "user_id";
    int ERROR_RESULT =404 ;
    int CAMERA_PERMISSION_REQUEST_CODE = 1;
    String SHOW_PIC = "show_pic";
    String NULL = "null";
    String TYPE = "type";
    String EDIT_PROFILE = "1";
    String CREATE_PROFILE = "0";
    String MALE = "male";
    String FEMALE = "female";
    String USER = "1";

    public enum WEBVIEW{
        TERM_CONDITION;
    }

    public enum GOOGLE{
        NEARBY,AUTOCOMPLETE
    }

    public enum Address{
        HOME,WORK,OTHER
    }
}
