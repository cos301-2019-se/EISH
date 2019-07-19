package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "homeuserpresence")
@Table(name = "homeuserpresence")
@EntityListeners(AuditingEntityListener.class)
public class HomeUserPresence {
  
    @EmbeddedId
    private HomeUserPresenceId homeUserPresenceId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userid", insertable = false, updatable = false, nullable = false)
    private HomeUser homeuser;

    @Column(name = "homeuserpresence", columnDefinition = "boolean", updatable = true, nullable = false)
    private boolean homeUserPresence;

    //@Id
    @Column(name = "homeuserpresencetimestamp", columnDefinition = "TIMESTAMP", insertable = false, updatable = false, nullable = false)
    private Timestamp homeUserPresenceTimestamp;

    public HomeUserPresence() {}

    public HomeUserPresence(boolean newHomeUserPresence, 
        HomeUser newHomeUser, 
        Timestamp newHomeUserPresenceTimestamp) {
        setHomeUserPresence(newHomeUserPresence);
        setHomeUser(newHomeUser);
        setHomeUserPresenceTimeStamp(newHomeUserPresenceTimestamp);
        setHomeUserPresenceId();

    }

    //getters
    @JsonIgnore
    public HomeUserPresenceId getHomeUserPresenceId() {
        return homeUserPresenceId;
    }

    public HomeUser getHomeUser() {
        return homeuser;
    }

    public Timestamp getHomeUserPresenceTimeStamp() {
        return homeUserPresenceTimestamp;
    }

    public boolean getHomeUserPresence() {
        return homeUserPresence;
    }

    //setter
    public void setHomeUserPresenceId() {
        this.homeUserPresenceId = new HomeUserPresenceId(getHomeUser().getUserId(),
        getHomeUserPresenceTimeStamp());
    }

    public void setHomeUser(HomeUser newHomeUser) {
        this.homeuser = newHomeUser;
    }

    public void setHomeUserPresenceTimeStamp(Timestamp newHomeUserPresenceTimeStamp) {
        this.homeUserPresenceTimestamp = newHomeUserPresenceTimeStamp;
    }

    public void setHomeUserPresence(boolean newHomeUserPresence) {
        this.homeUserPresence = newHomeUserPresence;
    }

}