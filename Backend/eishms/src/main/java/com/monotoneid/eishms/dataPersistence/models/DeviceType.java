package com.monotoneid.eishms.dataPersistence.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "devicetype")
@Table(name = "devicetype")
@EntityListeners(AuditingEntityListener.class)
public class DeviceType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "devicetypeid", columnDefinition = "serial", updatable = false, nullable = false)
    private long deviceTypeId;

    @Size(min = 1, message = "length of device type name must be greater than a character")
    @Column(name = "devicetypename", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String deviceTypeName;
    
    @Size(min = 1, message = "number of device states must be one or more")
    @Column(name = "devicetypestates", columnDefinition = "text[]", updatable = true, nullable = false)
    private String[] deviceTypeStates;

    
    @OneToMany(mappedBy = "devicetype")
    private List<Device> devicesOfThisType= new ArrayList<Device>();

    public DeviceType(){}

    public DeviceType(@JsonProperty("deviceTypeName") String newDeviceTypeName,
    @JsonProperty("deviceTypeStates") String[] newDeviceTypeStates){
        setDeviceTypeName(newDeviceTypeName);
        setDeviceTypeStates(newDeviceTypeStates);
    }
    
    //getters
    public long getDeviceTypeId(){
        return deviceTypeId;
    }
    public String getDeviceTypeName(){
        return deviceTypeName;
    }
    public String[] getDeviceTypeStates(){
        return deviceTypeStates;
    }
    public List<Device> getDevicesOfThisType(){
        return devicesOfThisType;
    }
    //setters
    public void setDeviceTypeName(String newDeviceTypeName){
        this.deviceTypeName = newDeviceTypeName;
    }
    public void setDeviceTypeStates(String[] newDeviceTypeStates){
        this.deviceTypeStates = newDeviceTypeStates;
    }
    public void setDevice(List<Device> newDevicesOfThisType){
        this.devicesOfThisType = newDevicesOfThisType;
    }
}