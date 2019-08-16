package com.monotoneid.eishms.datapersistence.models;

public class BlacklistItem {
    private String userName;
    private String userToken;
    private boolean blacklisted;

    public BlacklistItem(String username, String token) {
        this.userName = username;
        this.userToken = token;
        this.blacklisted = false;
    }

    public void blacklist() {
        this.blacklisted = true;
    }

    public boolean isBlacklisted() {
        return this.blacklisted;
    }

    public boolean isSameToken(String token) {
        return this.userToken.matches(token);
    }

    public boolean isSameUserName(String username) {
        return this.userName.matches(username);
    }
}