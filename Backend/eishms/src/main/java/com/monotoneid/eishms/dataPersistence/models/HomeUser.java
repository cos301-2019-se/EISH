package com.monotoneid.eishms.dataPersistence.models;

import static com.monotoneid.eishms.dataPersistence.models.UserType.ROLE_GUEST;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

enum UserType {
    ROLE_ADMIN, ROLE_RESIDENT, ROLE_GUEST;
}

@Entity(name = "homeuser")
@Table(name = "homeuser")
@EntityListeners(AuditingEntityListener.class)
public class HomeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", columnDefinition = "serial", updatable = false, nullable = false)
    private long userId;

    @Column(name = "username", columnDefinition = "text", updatable = true, nullable = false)
    private String userName;

    @Column(name = "useremail", columnDefinition = "text", updatable = true, nullable = false)
    private String userEmail;

    @Column(name = "userpassword", columnDefinition = "text", updatable = true, nullable = false)
    private String userPassword;

    @Column(name = "userlocationtopic", columnDefinition = "text", updatable = true, nullable = false)
    private String userLocationTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype", columnDefinition = "text", updatable = true, nullable = false)
    private UserType userType;

    
    @Column(name = "userexpirydate", columnDefinition = "TIMESTAMP", updatable = true, nullable = false)
    private Timestamp userExpiryDate;

    public HomeUser() {}
    
    public HomeUser(@JsonProperty("userName") String newUserName, @JsonProperty("userEmail") String newUserEmail,
            @JsonProperty("userPassword") String newUserPassword,
            @JsonProperty("userLocationTopic") String newUserLocationTopic) {
        setUserName(newUserName);
        setUserEmail(newUserEmail);
        setUserPassword(newUserPassword);
        setUserLocationTopic(newUserLocationTopic);
        setUserType(ROLE_GUEST);
    }

    //getters
    public long getUserId(){
        return userId;
    }

    public String getUserName(){
        return userName;
    }

    public String getUserEmail(){
        return userEmail;
    }

    public String getUserPassword(){
        return userPassword;
    }

    public String getUserLocationTopic(){
        return userLocationTopic;
    }

    public UserType getUserType(){
        return userType;
    }

    public Timestamp getUserExpiryDate(){
        return userExpiryDate;
    }

    //setters
    public void setUserName(String newUserName){
        this.userName = newUserName;
    }

    public void setUserEmail(String newUserEmail){
        this.userEmail = newUserEmail;
    }

    public void setUserPassword(String newUserPassword){
        this.userPassword = newUserPassword;
    }

    public void setUserLocationTopic(String newUserLocationTopic){
        this.userLocationTopic = newUserLocationTopic;
    }

    public void setUserType(UserType newUserType){
        this.userType = newUserType;
    }

    public void setUserExpiryDate(Timestamp newUserExpiryDate){
        this.userExpiryDate = newUserExpiryDate;
    }
}