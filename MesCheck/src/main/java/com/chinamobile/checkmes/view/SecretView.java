package com.chinamobile.checkmes.view;

public class SecretView {
    private String secret;
    private Integer secretID;
    private Integer type;
    private Integer length;

    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public Integer getSecretID() {
        return secretID;
    }

    public void setSecretID(Integer secretID) {
        this.secretID = secretID;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
