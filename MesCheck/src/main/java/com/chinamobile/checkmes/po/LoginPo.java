package com.chinamobile.checkmes.po;

import java.sql.Date;

public class LoginPo {
    private String ip;
    private  String phone;
    private Date date;
    private  Integer count;
    private String comPhone;
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

    public String getComPhone() {
        return comPhone;
    }

    public void setComPhone(String comPhone) {
        this.comPhone = comPhone;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
