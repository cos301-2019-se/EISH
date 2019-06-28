package com.monotoneid.eishms.dataPersistence.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "device")
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
@TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceid", columnDefinition = "serial", updatable = false, nullable = false)
    private long deviceId;
    
    @Column(name = "devicename", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String deviceName;

    @Column(name = "devicetopic", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String deviceTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "deviceprioritytype", columnDefinition = "devicePriorityType", updatable = true, nullable = false)
    @Type( type = "pgsql_enum" )
    private DevicePriorityType devicePriority;

    @OneToOne(mappedBy = "device")
    private DeviceType deviceType;

    @OneToMany(mappedBy = "device")
    private List<DeviceConsumption> deviceconsumptions= new ArrayList<DeviceConsumption>();

    public Device(){}

    public Device(@JsonProperty("deviceName") String newDeviceName,
    @JsonProperty("deviceTopic") String newDeviceTopic,
    @JsonProperty("devicePriorityType") String newDevicePriorityType){
        setDeviceName(newDeviceName);
        setDeviceTopic(newDeviceTopic);
        setDevicePriorityType(devicePriority.valueOf(newDevicePriorityType));
        //setDeviceType(newDeviceType);
    }

    //getter
    public long getDeviceId(){
        return deviceId;
    }
    public String getDeviceName(){
        return deviceName;
    }
    public String getDeviceTopic(){
        return deviceTopic;
    }
    public DeviceType getDeviceType(){
        return deviceType;
    }
    public DevicePriorityType getDevicePriority(){
        return devicePriority;
    }
    public List<DeviceConsumption> getDeviceConsumption(){
        return deviceconsumptions;
    }

    //setter
    public void setDeviceName(String newDeviceName){
        this.deviceName = newDeviceName;
    }
    public void setDeviceTopic(String newDeviceTopic){
        this.deviceTopic = newDeviceTopic;
    }
    public void setDeviceType(DeviceType newDeviceType){
        this.deviceType = newDeviceType;
    }
    public void setDevicePriorityType(DevicePriorityType newDevicePriorityType){
        this.devicePriority = newDevicePriorityType;
    }
    public void setDeviceConsumption(List<DeviceConsumption> newDeviceConsumption){
        this.deviceconsumptions = newDeviceConsumption;
    }
}