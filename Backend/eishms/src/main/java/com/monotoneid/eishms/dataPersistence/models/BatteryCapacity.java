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

@Entity(name = "batterycapacity")
@Table(name = "batterycapacity")
@EntityListeners(AuditingEntityListener.class)
public class BatteryCapacity{

    
    @EmbeddedId
    private BatteryCapacityId batteryCapacityId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="batteryid", insertable = false ,updatable = false, nullable = false)
    private Battery battery;

    @Column(name = "batterycapacitytimestamp", columnDefinition = "TIMESTAMP", insertable = false , updatable = false, nullable = false)
    private Timestamp batteryCapacityTimestamp;

    @Size(min = 1, message = "generator states must be one or more characters")
    @Column(name = "batterycapacitystate", columnDefinition = "text", updatable = true, nullable = false)
    private String batteryCapacityState;

    @Column(name = "batterycurrentcapacity", columnDefinition = "float", updatable = true, nullable = true)
    private Float batteryCurrentCapacity;

    public BatteryCapacity() {}

    public BatteryCapacity(float newBatteryCurrentCapacity, Battery newBattery, 
    Timestamp newBatteryCapacityTimestamp, String newBatteryCapacityState) {
        setBatteryCurrentCapacity(newBatteryCurrentCapacity);
        setBattery(newBattery);
        setBatteryCapacityTimestamp(newBatteryCapacityTimestamp);
        setBatteryCapacityState(newBatteryCapacityState);
        setBatteryCapacityId();

    }
    //getters
    @JsonIgnore
    public BatteryCapacityId getBatteryCapacityId(){
        return batteryCapacityId;
    }

    public Battery getBattery() {
       return battery;
    }

    public Float getBatteryCurrentCapacity() {
        return batteryCurrentCapacity;
    }

    public Timestamp getBatteryCapacityTimestamp() {
        return batteryCapacityTimestamp;
    }
    @JsonIgnore
    public String getBatteryCapacityState() {
        return batteryCapacityState;
    }

    //setters
    public void setBatteryCapacityId() {
        this.batteryCapacityId = new BatteryCapacityId(getBattery().getBatteryId(),getBatteryCapacityTimestamp());
    }

    public void setBattery(Battery newBattery) {
        this.battery = newBattery;
    }

    public void setBatteryCurrentCapacity(Float newBatteryCurrentCapacity) {
        this.batteryCurrentCapacity = newBatteryCurrentCapacity;
    }

    public void setBatteryCapacityTimestamp(Timestamp newBatteryCapacityTimestamp) {
        this.batteryCapacityTimestamp = newBatteryCapacityTimestamp;
    }

    public void setBatteryCapacityState(String newBatteryCapacityState) {
        this.batteryCapacityState = newBatteryCapacityState;
    }

}