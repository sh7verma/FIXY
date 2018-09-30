package com.app.fixy.models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Shubham verma on 30-09-2018.
 */

public class CityModel extends BaseModel {
    /**
     * response : [{"id":"1","city_name":"Chandigarh","created_at":"2018-09-20 00:00:00","updated_at":"2018-09-20 05:09:07"},{"id":"2","city_name":"Panchkula","created_at":"2018-09-20 00:00:00","updated_at":"2018-09-20 05:09:07"},{"id":"3","city_name":"Mohali","created_at":"2018-09-20 00:00:00","updated_at":"2018-09-20 05:10:09"}]
     * message : All cities.
     * code : 200
     */

    private ArrayList<ResponseBean> response;

    public ArrayList<ResponseBean> getResponse() {
        return response;
    }

    public void setResponse(ArrayList<ResponseBean> response) {
        this.response = response;
    }

    public static class ResponseBean {
        /**
         * id : 1
         * city_name : Chandigarh
         * created_at : 2018-09-20 00:00:00
         * updated_at : 2018-09-20 05:09:07
         */

        private String id;
        private String city_name;
        private String created_at;
        private String updated_at;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getCity_name() {
            return city_name;
        }

        public void setCity_name(String city_name) {
            this.city_name = city_name;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }
    }
}