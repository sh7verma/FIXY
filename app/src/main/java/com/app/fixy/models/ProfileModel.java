package com.app.fixy.models;

/**
 * Created by Shubham verma on 04-09-2018.
 */

public class ProfileModel extends BaseModel {


    /**
     * response : {"code":200,"profile_status":1,"auth_token":"215216a1195f35436399314965632ad9","message":"Profile created successfully"}
     */

    private ResponseBean response;

    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * code : 200
         * profile_status : 1
         * auth_token : 215216a1195f35436399314965632ad9
         * message : Profile created successfully
         */

        private int code;
        private int profile_status;
        private String auth_token;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public int getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(int profile_status) {
            this.profile_status = profile_status;
        }

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
