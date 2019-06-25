package com.monotoneid.eishms.dataPersistence.models;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;

public class HomeKey {
    private String keyName;
    private String userKey;
    private String unencryptedKey;
    private UserType userType;

    @Autowired
    PasswordEncoder encoder;

    public HomeKey(String keyname, String userkey, UserType usertype) {
        this.keyName = keyname;
        this.userKey = userkey;
        this.userType = usertype; 
    }

    public void setUsername(String keyname) {
        this.keyName = keyname;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void  setUserkey(String userkey) {
        this.unencryptedKey = userkey;
        this.userKey = encoder.encode(userKey);
    }

    public String getUserkey() {
        return this.userKey;
    }

    public String getUnencryptedKey() {
        return this.unencryptedKey;
    }

    public void setUsertype(UserType usertype) {
        this.userType = usertype;
    }

    public UserType getUsertype() {
        return this.userType;
    }
}