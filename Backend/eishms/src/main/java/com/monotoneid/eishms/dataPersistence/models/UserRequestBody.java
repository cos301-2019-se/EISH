package com.monotoneid.eishms.dataPersistence.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequestBody {
    private long userId;
    private String userName;
    private String userEmail;
    private String userPassword;
    private String userLocationTopic;

    public UserRequestBody() {}

    public UserRequestBody(@JsonProperty("userId") long id,
                           @JsonProperty("userName") String name,
                           @JsonProperty("userEmail") String email,
                           @JsonProperty("userPassword") String pass,
                           @JsonProperty("userLocationTopic") String topic) {
                            
        this.userId = id;
        this.userName = name;
        this.userEmail = email;
        this.userPassword = pass;
        this.userLocationTopic = topic;
    }

    public long getUserId() {
        return this.userId;
    }

    public String getUserName() {
        return this.userName;
    }

    public String getUserEmail() {
        return this.userEmail;
    }

    public String getUserPassword() {
        return this.userPassword;
    }

    public String getUserLocationTopic() {
        return this.userLocationTopic;
    }
}