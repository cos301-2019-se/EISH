package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
/**
 * CLASS HOMEUSERPRESENCE. 
 */

@JsonIgnoreProperties({"homeUserPresenceTimestamp"})
@Entity(name = "homeuserpresence")
@Table(name = "homeuserpresence")
@EntityListeners(AuditingEntityListener.class)
public class HomeUserPresence {
  
    @EmbeddedId
    private HomeUserPresenceId homeUserPresenceId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "userid", 
                insertable = false, 
                updatable = false, 
                nullable = false)
    private HomeUser homeuser;

    @Column(name = "homeuserpresence", 
            columnDefinition = "boolean", 
            updatable = true, 
            nullable = false)
    private boolean homeUserPresence;

    @Column(name = "homeuserpresencetimestamp", 
            columnDefinition = "TIMESTAMP", 
            insertable = false, 
            updatable = false, 
            nullable = false)
    private Timestamp homeUserPresenceTimestamp;

    public HomeUserPresence() {}
    
    /**. */
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

    @JsonIgnore
    public Timestamp getHomeUserPresenceTimeStamp() {
        return homeUserPresenceTimestamp;
    }

    @JsonIgnore
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