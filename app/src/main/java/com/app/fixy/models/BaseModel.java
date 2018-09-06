package com.app.fixy.models;

/**
 * Created by Shubham verma on 04-09-2018.
 */

public class BaseModel {

    /**
     * error : {"code":400,"message":"Required field(s) profile_image is missing or empty"}
     */

    private ErrorBean error;

    public ErrorBean getError() {
        return error;
    }

    public void setError(ErrorBean error) {
        this.error = error;
    }

    public static class ErrorBean {
        /**
         * code : 400
         * message : Required field(s) profile_image is missing or empty
         */

        private String message;

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
