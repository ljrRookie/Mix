package com.example.library_base.bean;

public class LoginBean {
    private User data;
    private int errorCode;
    private String errorMsg;

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg == null ? "" : errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
