package com.app.fixy.models;

public class UserModel extends BaseModel {
    /**
     * response : {"id":"15","fullname":null,"access_token":"HF0etnqiQWSzACY63LNpMV8xcvdl7jKo5fBOgXE94hUJaPZsRT","gender":"0","email":null,"user_type":"1","profile_pic":null,"refferal_code":"","profile_status":"0","coins":"100","refferal_coins":"","city":null,"city_id":null,"number_verify":"0"}
     * message : Create user successfully.
     * code : 200
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
         * id : 15
         * fullname : null
         * access_token : HF0etnqiQWSzACY63LNpMV8xcvdl7jKo5fBOgXE94hUJaPZsRT
         * gender : 0
         * email : null
         * user_type : 1
         * profile_pic : null
         * refferal_code :
         * profile_status : 0
         * coins : 100
         * refferal_coins :
         * city : null
         * city_id : null
         * number_verify : 0
         */

        private String id;
        private String fullname;
        private String access_token;
        private String gender;
        private String email;
        private String user_type;
        private String profile_pic;
        private String refferal_code;
        private String profile_status;
        private String coins;
        private String refferal_coins;
        private String city;
        private String city_id;
        private String number_verify;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public String getGender() {
            return gender;
        }

        public void setGender(String gender) {
            this.gender = gender;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getUser_type() {
            return user_type;
        }

        public void setUser_type(String user_type) {
            this.user_type = user_type;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getRefferal_code() {
            return refferal_code;
        }

        public void setRefferal_code(String refferal_code) {
            this.refferal_code = refferal_code;
        }

        public String getProfile_status() {
            return profile_status;
        }

        public void setProfile_status(String profile_status) {
            this.profile_status = profile_status;
        }

        public String getCoins() {
            return coins;
        }

        public void setCoins(String coins) {
            this.coins = coins;
        }

        public String getRefferal_coins() {
            return refferal_coins;
        }

        public void setRefferal_coins(String refferal_coins) {
            this.refferal_coins = refferal_coins;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getCity_id() {
            return city_id;
        }

        public void setCity_id(String city_id) {
            this.city_id = city_id;
        }

        public String getNumber_verify() {
            return number_verify;
        }

        public void setNumber_verify(String number_verify) {
            this.number_verify = number_verify;
        }
    }
}