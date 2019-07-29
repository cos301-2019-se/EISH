package com.monotoneid.eishms.datapersistence.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class HomeUserPresenceId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "userid")
    private long userId;

    @Column(name = "homeuserpresencetimestamp")
    private Timestamp homePresenceTimestamp;

    public HomeUserPresenceId() {

    }

    public HomeUserPresenceId(long newUserId, Timestamp newHomePresenceTimestamp) {
        this.userId = newUserId;
        this.homePresenceTimestamp = newHomePresenceTimestamp;
    }

    //getters
    public long getUserId() {
        return userId;
    }

    public Timestamp getHomePresenceTimeStamp() {
        return homePresenceTimestamp;
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof HomeUserPresenceId)) {
            return false;
        }
        HomeUserPresenceId that = (HomeUserPresenceId) o;
        return Objects.equals(getUserId(), that.getUserId()) 
            && Objects.equals(getHomePresenceTimeStamp(), that.getHomePresenceTimeStamp());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getUserId(), getHomePresenceTimeStamp());
    }
}