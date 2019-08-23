package com.monotoneid.eishms.datapersistence.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *CLASS DEVICECONSUMPTIONID. 
 */
@Embeddable
public class DeviceConsumptionId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "deviceid")
    private long deviceId;

    @Column(name = "deviceconsumptiontimestamp")
    private Timestamp deviceConsumptionTimestamp;

    public DeviceConsumptionId(){

    }

    public DeviceConsumptionId(long newDeviceId, Timestamp newDeviceConsumptionTimestamp) {
        this.deviceId = newDeviceId;
        this.deviceConsumptionTimestamp = newDeviceConsumptionTimestamp;
    }

    //getters
    public long getDeviceId() {
        return deviceId;
    }

    public Timestamp getDeviceConsumptionTimeStamp() {
        return deviceConsumptionTimestamp;
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } 
        if (!(o instanceof DeviceConsumptionId)) {
            return false;
        } 
        DeviceConsumptionId that = (DeviceConsumptionId) o;
        return Objects.equals(getDeviceId(), that.getDeviceId()) 
            && Objects.equals(getDeviceConsumptionTimeStamp(),
            that.getDeviceConsumptionTimeStamp());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getDeviceId(), getDeviceConsumptionTimeStamp());
    }
}