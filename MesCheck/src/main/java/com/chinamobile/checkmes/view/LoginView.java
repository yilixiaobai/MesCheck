package com.chinamobile.checkmes.view;

public class LoginView {
    private String date;
    private Integer count;
    private String verCode;
    private Long verCodeTime;

    public Long getVerCodeTime() {
        return verCodeTime;
    }

    public void setVerCodeTime(Long verCodeTime) {
        this.verCodeTime = verCodeTime;
    }

    public String getVerCode() {
        return verCode;
    }

    public void setVerCode(String verCode) {
        this.verCode = verCode;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
