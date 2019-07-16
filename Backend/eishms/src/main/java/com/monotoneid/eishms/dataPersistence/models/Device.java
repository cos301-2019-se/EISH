package com.monotoneid.eishms.dataPersistence.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

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
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@JsonIgnoreProperties({"deviceConsumption"})
@Entity(name = "device")
@Table(name = "device")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    ),
    @TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)})
public class Device {

        
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "deviceid", columnDefinition = "serial", updatable = false, nullable = false)
    private long deviceId;
    
    @Size(min = 4, max = 30, message = "Device Name must be between 4 and 30 characters")
    @Column(name = "devicename", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String deviceName;

    @Size(min = 4, max = 30, message = "Device Topic must be between 4 and 30 characters")
    @Column(name = "devicetopic", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String deviceTopic;

    @Enumerated(EnumType.STRING)
    @Column(name = "devicepriority", columnDefinition = "devicePriorityType", updatable = true, nullable = false)
    @Type(type = "pgsql_enum")
    private DevicePriorityType devicePriority;

    //@Size(min = 1, message = "number of device states must be one or more")
    @Type(type = "string-array")
    @Column(name = "devicestates", columnDefinition = "text[]", updatable = true, nullable = false)
    private String[] deviceStates;
    
   
    @JsonManagedReference
    @OneToMany(mappedBy = "device")
    private List<DeviceConsumption> deviceConsumption = new ArrayList<DeviceConsumption>();

    public Device(){}

    public Device(
        @JsonProperty("deviceName") String newDeviceName,
        @JsonProperty("deviceTopic") String newDeviceTopic,
        @JsonProperty("devicePriorityType") String newDevicePriorityType,
        @JsonProperty("deviceStates") String[] newDeviceStates) {
        setDeviceName(newDeviceName);
        setDeviceTopic(newDeviceTopic);
        setDevicePriorityType(devicePriority.valueOf(newDevicePriorityType));
        String[] newStates = new String[newDeviceStates.length];
        for (int i = 0; i < newDeviceStates.length; i++) {
            newStates[i] = new String(newDeviceStates[i]);
        }
        setDeviceStates(newStates);
        
    }
  
    //getter
    public long getDeviceId() {
        return deviceId;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceTopic() {
        return deviceTopic;
    }

    public String[] getDeviceStates() {
        return deviceStates;
    }

    public DevicePriorityType getDevicePriority() {
        return devicePriority;
    }
    
    public List<DeviceConsumption> getDeviceConsumption() {
        return deviceConsumption;
    }
    
    
    //setter
    public void setDeviceName(String newDeviceName) {
        this.deviceName = newDeviceName;
    }

    public void setDeviceTopic(String newDeviceTopic) {
        this.deviceTopic = newDeviceTopic;
    }

    public void setDeviceStates(String[] newDeviceStates) {
        this.deviceStates = newDeviceStates;
    }

    public void setDevicePriorityType(DevicePriorityType newDevicePriorityType) {
        this.devicePriority = newDevicePriorityType;
    }

    public void setDeviceConsumption(List<DeviceConsumption> newDeviceConsumption) {
        this.deviceConsumption = newDeviceConsumption;
    }
    
}