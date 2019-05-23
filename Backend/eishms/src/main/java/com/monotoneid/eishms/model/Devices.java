package com.monotoneid.eishms.model;

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
import com.monotoneid.eishms.model.DeviceRequestBody;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name="devices")
@Table(name="devices")
@EntityListeners(AuditingEntityListener.class)
public class Devices{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "device_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long device_id;

    @Column(name="device_name", columnDefinition = "text", updatable = true,nullable = false)
    private String device_name;

    @Column(name="device_topic", columnDefinition = "text", updatable = true,nullable = false)
    private String device_topic;

    @Column(name="device_min_watt", columnDefinition = "int", updatable = true,nullable = false)
    private int device_min_watt;

    @Column(name="device_max_watt", columnDefinition = "int", updatable = true,nullable = false)
    private int device_max_watt;

    @Column(name="device_type", columnDefinition = "text", updatable = true,nullable = false)
    private String device_type;

    @Column(name="device_state", columnDefinition = "boolean", updatable = true,nullable = false)
    private boolean device_state;

    @Column(name="device_autostart", columnDefinition = "boolean",updatable = true,nullable = false)
    private boolean device_autostart;

    @OneToMany(mappedBy = "device")
    private List<DeviceConsumption> deviceconsumptions= new ArrayList<DeviceConsumption>();

   
    @Column(name="device_priority",columnDefinition ="int",updatable = true, nullable = false)
   //@Enumerated(EnumType.STRING)
    //@Column(name="device_priority")
    private int device_priority;
    public Devices(){}
    public Devices(String devicename,String deviceTopic,int minwat,int maxwat,String devicetype, int priority, boolean das,boolean ds){
        this.setDeviceName(devicename);
        this.setDeviceTopic(deviceTopic);
        this.setDeviceAutoStart(das);
        this.setDeviceMaxWatt(maxwat);
        this.setDeviceMinWatt(minwat);
        this.setDeviceType(devicetype);
        this.setDevicePriority(priority);
        this.setDeviceState(ds);
    }

    //getters
    public long getDeviceId(){
        return device_id;
    }
    public String getDeviceName(){
        return device_name;
    }
   
    public String getDeviceTopic(){
        return device_topic;
    }
    public int getDeviceMinWatt(){
        return device_min_watt;
    } 
    public int getDeviceMaxWatt(){
        return device_max_watt;
    }    
    public String getDeviceType(){
        return device_type;
    }
    public boolean getDeviceState(){
        return device_state;
    }
    public boolean getDeviceAutoStart(){
        return device_autostart;
    }
    public List<DeviceConsumption> getDeviceConsumption(){
        return deviceconsumptions;
    }
    public int getDevicePriority(){
        return device_priority;
    }

    //setter
    public void setDeviceName(String dn){
        this.device_name=dn;
    }
    public void setDeviceTopic(String dt){
        this.device_topic=dt;
    }
    public void setDeviceMinWatt(int mw){
        this.device_min_watt=mw;
    }
    public void setDeviceMaxWatt(int mw){
        this.device_max_watt=mw;
    }
    public void setDeviceType(String dt){
        this.device_type=dt;
    }
    public void setDeviceState(boolean ds){
        this.device_state=ds;
    }
    public void setDeviceAutoStart(boolean das){
        this.device_autostart=das;
    }
    public void setDevicePriority(int priority){
        this.device_priority=priority;
    }
    public void setDeviceConsumption(List<DeviceConsumption> dc){
        this.deviceconsumptions=dc;
    }

}