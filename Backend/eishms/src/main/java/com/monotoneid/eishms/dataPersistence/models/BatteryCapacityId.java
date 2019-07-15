package com.monotoneid.eishms.dataPersistence.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class BatteryCapacityId implements Serializable{

    private static final long serialVersionUID = 1L;
    
    @Column(name = "batteryid")
    private long batteryId;

    @Column(name = "batterycapacitytimestamp")
    private Timestamp batteryCapacityTimestamp;

    public BatteryCapacityId(){

    }
    public BatteryCapacityId(long newBatteryId, Timestamp newBatteryCapacityTimestamp){
        this.batteryId = newBatteryId;
        this.batteryCapacityTimestamp = newBatteryCapacityTimestamp;
    }
    //getters
   public long getBatteryId(){
        return batteryId;
    }
    public Timestamp getBatteryCapacityTimeStamp(){
        return batteryCapacityTimestamp;
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BatteryCapacityId)) return false;
        BatteryCapacityId that = (BatteryCapacityId) o;
        return Objects.equals(getBatteryId(), that.getBatteryId()) &&
                Objects.equals(getBatteryCapacityTimeStamp(), that.getBatteryCapacityTimeStamp());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getBatteryId(), getBatteryCapacityTimeStamp());
    }
}