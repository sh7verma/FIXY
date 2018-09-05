package com.app.fixy.models;

public class LoginModel extends BaseModel {
    /**
     * response : {"code":200,"profile_status":0,"profile_image":"","user_id":3,"email":"","country_code":"+91","phone":9988639725,"gender":"","name":"","auth_token":"215216a1195f35436399314965632ad9"}
     */

    private ResponseBean response;
    /**
     * error : {"error":true,"message":"Required field(s) phone is missing or empty"}
     */
    public ResponseBean getResponse() {
        return response;
    }

    public void setResponse(ResponseBean response) {
        this.response = response;
    }


    public static class ResponseBean {
        /**
         * code : 200
         * profile_status : 0
         * profile_image :
         * user_id : 3
         * email :
         * country_code : +91
         * phone : 9988639725
         * gender :
         * name :
         * auth_token : 215216a1195f35436399314965632ad9
         */

        private int code;
        private String profile_status;
        private String profile_image;
        private String user_id;
        private String email;
        private String country_code;
        private String phone;
        private String gender;
        private String name;
        private String auth_token;

        public String getProfile_image() {
            return profile_image;
        }

        public void setProfile_image(String profile_image) {
            this.profile_image = profile_image;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(String profile_status) {
            this.profile_status = profile_status;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getCountry_code() {
            return country_code;
        }

        public void setCountry_code(String country_code) {
            this.country_code = country_code;
        }

        public String getPhone() {
            return phone;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getAuth_token() {
            return auth_token;
        }

        public void setAuth_token(String auth_token) {
            this.auth_token = auth_token;
        }
    }

    public static class ErrorBean {
        /**
         * error : true
         * message : Required field(s) phone is missing or empty
         */

        private int code;
        private String message;

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
    /**
     * response : {"code":200,"otp":1234,"message":"OTP send successfully"}
     */

    /**
     * error : {"code":404,"message":"Please enter correct phone number"}
     */


}
