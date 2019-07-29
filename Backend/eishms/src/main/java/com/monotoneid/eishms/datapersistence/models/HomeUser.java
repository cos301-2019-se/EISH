package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.sql.Timestamp;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Pattern;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *CLASS HOMEUSER MODEL. 
 */
@JsonIgnoreProperties({"homeUserPresence"})
@Entity(name = "homeuser")
@Table(name = "homeuser")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)
public class HomeUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid", 
            columnDefinition = "serial", 
            updatable = false, 
            nullable = false)
    private long userId;

    @Column(name = "username", 
            columnDefinition = "text",
            updatable = true, 
            unique = true, 
            nullable = false)
    private String userName;

    @Column(name = "useremail", 
            columnDefinition = "text", 
            updatable = true, 
            nullable = false)
    // @Pattern(regexp="^\\w+[\\w-\\.]*\\@\\w+((-\\w+)|(\\w*))\\.[a-z]{2,3}$")
    @Pattern(regexp = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$")
    private String userEmail;


    @Column(name = "userpassword", 
            columnDefinition = "text", 
            updatable = true, 
            nullable = false)
    private String userPassword;

    @Column(name = "userlocationtopic", 
            columnDefinition = "text", 
            updatable = true, 
            unique = true, 
            nullable = false)
    private String userLocationTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "usertype", 
            columnDefinition = "userType", 
            updatable = true, 
            nullable = false)
    @Type(type = "pgsql_enum")
    private UserType userType;

    
    @Column(name = "userexpirydate", 
            columnDefinition = "TIMESTAMP", 
            updatable = true, 
            nullable = false)
    private Timestamp userExpiryDate;

    @JsonManagedReference
    @OneToMany(mappedBy = "homeuser")
    private List<HomeUserPresence> homeUserPresence = new ArrayList<HomeUserPresence>();

    public HomeUser() {

    }

    public HomeUser(String userName, String userEmail) {
            
    }

    /**
     * .
     * @param newUserName represents the users name
     * @param newEmail represents the users email
     * @param newPassword represents the users password
     * @param newLocationTopic represents the users location topic
     */
    public HomeUser(@JsonProperty("userName") String newUserName,
        @JsonProperty("userEmail") String newEmail, 
        @JsonProperty("userPassword") String newPassword,
        @JsonProperty("userLocationTopic") String newLocationTopic) {
        setUserName(newUserName);
        setUserEmail(newEmail);
        setUserPassword(newPassword);
        setUserLocationTopic(newLocationTopic);
        setUserType(UserType.ROLE_GUEST);
    }

    //getters
    public long getUserId() {
        return userId;
    }

    public String getUserName() {
        return userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    @JsonIgnore
    public String getUserPassword() {
        return userPassword;
    }

    public String getUserLocationTopic() {
        return userLocationTopic;
    }

    public UserType getUserType() {
        return userType;
    }

    public Timestamp getUserExpiryDate() {
        return userExpiryDate;
    }

    @JsonIgnore
    public List<HomeUserPresence> getHomeUserPresence() {
        return homeUserPresence;
    }

    //setters
    public void setUserName(String newUserName) {
        this.userName = newUserName;
    }

    public void setUserEmail(String newUserEmail) {
        this.userEmail = newUserEmail;
    }

    public void setUserPassword(String newUserPassword) {
        this.userPassword = newUserPassword;
    }

    public void setUserLocationTopic(String newUserLocationTopic) {
        this.userLocationTopic = newUserLocationTopic;
    }

    public void setUserType(UserType newUserType) {
        this.userType = newUserType;
    }

    public void setUserExpiryDate(Timestamp newUserExpiryDate) {
        this.userExpiryDate = newUserExpiryDate;
    }

    public void setHomeUserPresence(List<HomeUserPresence> newHomeUserPresence) {
        this.homeUserPresence = newHomeUserPresence;
    }
}