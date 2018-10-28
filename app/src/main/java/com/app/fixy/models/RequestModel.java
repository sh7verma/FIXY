package com.app.fixy.models;

import java.util.List;

/**
 * Created by Shubham verma on 28-10-2018.
 */

public class RequestModel extends BaseModel {

    /**
     * response : [{"id":"25","worker_id":"","user_id":"10","original_price":"","offer_percentage":"","offer_period":"","offer_description":null,"category_name":"Body message","category_pic":"","request_price":"20","fullname":"Shubham Verma","address":"Chandigarh","additional_notes":"","request_status":"0","email":"shubham.verma740@gmail.com","request_otp":"1111","accepted_time":"","completed_time":"","created_at":"2018-10-28 05:10:52","average_rating":"5","profile_pic":"","expired_time":"2018-10-29 05:10:52","schedule_time":"","ontheway_time":"","confirm_time":""}]
     */

    private List<ResponseBean> response;

    public List<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(List<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 25
         * worker_id :
         * user_id : 10
         * original_price :
         * offer_percentage :
         * offer_period :
         * offer_description : null
         * category_name : Body message
         * category_pic :
         * request_price : 20
         * fullname : Shubham Verma
         * address : Chandigarh
         * additional_notes :
         * request_status : 0
         * email : shubham.verma740@gmail.com
         * request_otp : 1111
         * accepted_time :
         * completed_time :
         * created_at : 2018-10-28 05:10:52
         * average_rating : 5
         * profile_pic :
         * expired_time : 2018-10-29 05:10:52
         * schedule_time :
         * ontheway_time :
         * confirm_time :
         */

        private String id;
        private String worker_id;
        private String user_id;
        private String original_price;
        private String offer_percentage;
        private String offer_period;
        private Object offer_description;
        private String category_name;
        private String category_pic;
        private String request_price;
        private String fullname;
        private String address;
        private String additional_notes;
        private String request_status;
        private String email;
        private String request_otp;
        private String accepted_time;
        private String completed_time;
        private String created_at;
        private String average_rating;
        private String profile_pic;
        private String expired_time;
        private String schedule_time;
        private String ontheway_time;
        private String confirm_time;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getWorker_id() {
            return worker_id;
        }

        public void setWorker_id(String worker_id) {
            this.worker_id = worker_id;
        }

        public String getUser_id() {
            return user_id;
        }

        public void setUser_id(String user_id) {
            this.user_id = user_id;
        }

        public String getOriginal_price() {
            return original_price;
        }

        public void setOriginal_price(String original_price) {
            this.original_price = original_price;
        }

        public String getOffer_percentage() {
            return offer_percentage;
        }

        public void setOffer_percentage(String offer_percentage) {
            this.offer_percentage = offer_percentage;
        }

        public String getOffer_period() {
            return offer_period;
        }

        public void setOffer_period(String offer_period) {
            this.offer_period = offer_period;
        }

        public Object getOffer_description() {
            return offer_description;
        }

        public void setOffer_description(Object offer_description) {
            this.offer_description = offer_description;
        }

        public String getCategory_name() {
            return category_name;
        }

        public void setCategory_name(String category_name) {
            this.category_name = category_name;
        }

        public String getCategory_pic() {
            return category_pic;
        }

        public void setCategory_pic(String category_pic) {
            this.category_pic = category_pic;
        }

        public String getRequest_price() {
            return request_price;
        }

        public void setRequest_price(String request_price) {
            this.request_price = request_price;
        }

        public String getFullname() {
            return fullname;
        }

        public void setFullname(String fullname) {
            this.fullname = fullname;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getAdditional_notes() {
            return additional_notes;
        }

        public void setAdditional_notes(String additional_notes) {
            this.additional_notes = additional_notes;
        }

        public String getRequest_status() {
            return request_status;
        }

        public void setRequest_status(String request_status) {
            this.request_status = request_status;
        }

        public String getEmail() {
            return email;
        }

        public void setEmail(String email) {
            this.email = email;
        }

        public String getRequest_otp() {
            return request_otp;
        }

        public void setRequest_otp(String request_otp) {
            this.request_otp = request_otp;
        }

        public String getAccepted_time() {
            return accepted_time;
        }

        public void setAccepted_time(String accepted_time) {
            this.accepted_time = accepted_time;
        }

        public String getCompleted_time() {
            return completed_time;
        }

        public void setCompleted_time(String completed_time) {
            this.completed_time = completed_time;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getAverage_rating() {
            return average_rating;
        }

        public void setAverage_rating(String average_rating) {
            this.average_rating = average_rating;
        }

        public String getProfile_pic() {
            return profile_pic;
        }

        public void setProfile_pic(String profile_pic) {
            this.profile_pic = profile_pic;
        }

        public String getExpired_time() {
            return expired_time;
        }

        public void setExpired_time(String expired_time) {
            this.expired_time = expired_time;
        }

        public String getSchedule_time() {
            return schedule_time;
        }

        public void setSchedule_time(String schedule_time) {
            this.schedule_time = schedule_time;
        }

        public String getOntheway_time() {
            return ontheway_time;
        }

        public void setOntheway_time(String ontheway_time) {
            this.ontheway_time = ontheway_time;
        }

        public String getConfirm_time() {
            return confirm_time;
        }

        public void setConfirm_time(String confirm_time) {
            this.confirm_time = confirm_time;
        }
    }
}
