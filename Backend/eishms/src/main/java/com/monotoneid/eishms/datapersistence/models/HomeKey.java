package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 *CLASS HOMEKEY MODEL. 
 */
public class HomeKey {
    private String keyName;
    private String userKey;
    private String unencryptedKey;
    private UserType userType;

    /**. */
    public HomeKey(String keyname, String userkey, UserType usertype) {
        setKeyName(keyname);
        setUserkey(userkey);
        setUnencryptedKey(userkey);
        setUsertype(usertype); 
    }

    public void setKeyName(String keyname) {
        this.keyName = keyname;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void  setUserkey(String userkey) {
        this.userKey = userkey;
    }

    @JsonIgnore
    public String getUserkey() {
        return this.userKey;
    }

    public void setUnencryptedKey(String unencrypted) {
        this.unencryptedKey = unencrypted;
    }

    public String getUnencryptedKey() {
        return this.unencryptedKey;
    }

    public void setUsertype(UserType usertype) {
        this.userType = usertype;
    }

    @JsonIgnore
    public UserType getUsertype() {
        return this.userType;
    }
}