package com.monotoneid.eishms.dataPersistence.models;

import java.security.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;


 enum UserType {
    ADMIN,
    RESIDENT,
    GUEST;
}

@Entity(name="user")
@Table(name="user")
@EntityListeners(AuditingEntityListener.class)
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", columnDefinition = "serial", updatable = false, nullable = false)
    private long userId;

    @Column(name = "useremail", columnDefinition = "text", updatable = true, nullable = false)
    private String userEmail;

    @Column(name = "userpassword", columnDefinition = "text", updatable = true, nullable = false)
    private String userPassword;

    @Column(name = "userlocationtopic", columnDefinition = "text", updatable = true, nullable = false)
    private String userLocationTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype", columnDefinition = "text", updatable = true, nullable = false)
    private UserType userType;

    //check the time stamp column definition
    @Column(name = "userexpirydate", columnDefinition = "TIMESTAMP", updatable = true, nullable = false)
    private Timestamp userExpiryDate;

    //getters
    public long getUserId(){
        return userId;
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