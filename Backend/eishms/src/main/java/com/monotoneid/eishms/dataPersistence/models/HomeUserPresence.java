package com.monotoneid.eishms.dataPersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;
/*
@Entity(name = "homeuserpresence")
@Table(name = "homeuserpresence")
@EntityListeners(AuditingEntityListener.class)
*/public class HomeUserPresence{
  /*  
    @Column(name = "homeuserpresencetimestamp", columnDefinition = "TIMESTAMP", insertable = true , updatable = true, nullable = false)
    private Timestamp homeUserPresenceTimestamp;

    @Column(name ="homeuserpresence", columnDefinition = "boolean", updatable = true, nullable = false)
    private boolean homeUserPresence;

    //getter
    public Timestamp getHomeUserPresenceTimeStamp(){
        return homeUserPresenceTimestamp;
    }
    public boolean getHomeUserPresence(){
        return homeUserPresence;
    }
    //setter
    public void setHomeUserPresenceTimeStamp(Timestamp newHomeUserPresenceTimeStamp){
        this.homeUserPresenceTimestamp = newHomeUserPresenceTimeStamp;
    }
    public void setHomeUserPresence(boolean newHomeUserPresence){
        this.homeUserPresence = newHomeUserPresence;
    }
    */
}