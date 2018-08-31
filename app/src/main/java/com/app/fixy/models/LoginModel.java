package com.app.fixy.models;

public class LoginModel {

    /**
     * success : 1
     * status : 200
     * message : OTP verified successfully
     */

    private int success;
    private int status;
    private String message;

    public int getSuccess() {
        return success;
    }

    public void setSuccess(int success) {
        this.success = success;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
