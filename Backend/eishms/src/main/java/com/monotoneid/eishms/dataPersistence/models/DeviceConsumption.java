package com.monotoneid.eishms.dataPersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "deviceconsumption")
@Table(name = "deviceconsumption")
@EntityListeners(AuditingEntityListener.class)
public class DeviceConsumption {

    @EmbeddedId
    private DeviceConsumptionId deviceConsumptionId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="deviceid", insertable = false ,updatable = false, nullable = false)
    private Device device;

    @Column(name = "deviceconsumption", columnDefinition = "float", updatable = true, nullable = true)
    private Float deviceConsumption;

    //@Id
    @Column(name = "deviceconsumptiontimestamp", columnDefinition = "TIMESTAMP", insertable = false , updatable = false, nullable = false)
    private Timestamp deviceConsumptionTimestamp;

    @Size(min = 1, message = "device states must be one or more characters")
    @Column(name = "deviceconsumptionstate", columnDefinition = "text", updatable = true, nullable = false)
    private String deviceConsumptionState;

    public DeviceConsumption() {}

    public DeviceConsumption(float newDeviceConsumption, Device newDevice, 
    Timestamp newDeviceConsumptionTimestamp, String newDeviceConsumptionState) {
        setDeviceConsumption(newDeviceConsumption);
        setDevice(newDevice);
        setDeviceConsumptionTimestamp(newDeviceConsumptionTimestamp);
        setDeviceConsumptionState(newDeviceConsumptionState);
        setDeviceConsumptionId();

    }

    //getters
    @JsonIgnore
    public DeviceConsumptionId getDeviceConsumptionId(){
        return deviceConsumptionId;
    }

    public Device getDevice(){
       return device;
    }

    public Float getDeviceConsumption(){
        return deviceConsumption;
    }

    public Timestamp getDeviceConsumptionTimestamp(){
        return deviceConsumptionTimestamp;
    }
    @JsonIgnore
    public String getDeviceConsumptionState(){
        return deviceConsumptionState;
    }

    //setters
    public void setDeviceConsumptionId(){
        this.deviceConsumptionId = new DeviceConsumptionId(getDevice().getDeviceId(),getDeviceConsumptionTimestamp());
    }

    public void setDevice(Device newDevice){
        this.device = newDevice;
    }

    public void setDeviceConsumption(Float newDeviceConsumption){
        this.deviceConsumption = newDeviceConsumption;
    }

    public void setDeviceConsumptionTimestamp(Timestamp newDeviceConsumptionTimestamp){
        this.deviceConsumptionTimestamp = newDeviceConsumptionTimestamp;
    }

    public void setDeviceConsumptionState(String newDeviceConsumptionState){
        this.deviceConsumptionState = newDeviceConsumptionState;
    }
    
}