package com.monotoneid.eishms.datamodel;

//import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
//import javax.persistence.JoinColumn;
//import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="Devices")
@EntityListeners(AuditingEntityListener.class)
public class Devices{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "device_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long device_id;

    @Column(name="device_name", columnDefinition = "text", updatable = true,nullable = false)
    private String device_name;

    @Column(name="publish_topic", columnDefinition = "text", updatable = true,nullable = false)
    private String publish_topic;

    @Column(name="subscribe_topic", columnDefinition = "text", updatable = true,nullable = false)
    private String subscribe_topic;

    @Column(name="min_watt", columnDefinition = "int", updatable = true,nullable = false)
    private int min_watt;

    @Column(name="max_watt", columnDefinition = "int", updatable = true,nullable = false)
    private int max_watt;

    @Column(name="device_type", columnDefinition = "text", updatable = true,nullable = false)
    private String device_type;

    @Column(name="device_state", columnDefinition = "boolean", updatable = true,nullable = false)
    private boolean device_state;

  //  @OneToOne(cascade = CascadeType.ALL)
  //  @JoinColumn(name = "fkdevice_id", referencedColumnName = "device_id")
  //  private DeviceConsumption deviceconsumption;

    //getters
    public long getDeviceId(){
        return device_id;
    }
    public String getDeviceName(){
        return device_name;
    }
    public String getPublishTopic(){
        return publish_topic;
    }
    public String getSubscribeTopic(){
        return subscribe_topic;
    }
    public int getMinWatt(){
        return min_watt;
    } 
    public int getMaxWatt(){
        return max_watt;
    }    
    public String getDeviceType(){
        return device_type;
    }
    public boolean getDeviceState(){
        return device_state;
    }

    //setter
    public void setDeviceName(String dn){
        this.device_name=dn;
    }
    public void setPublishTopic(String pt){
        this.publish_topic=pt;
    }
    public void setSubscribeTopic(String st){
        this.subscribe_topic=st;
    }
    public void setMinWatt(int mw){
        this.min_watt=mw;
    }
    public void setMaxWatt(int mw){
        this.max_watt=mw;
    }
    public void setDeviceType(String dt){
        this.device_type=dt;
    }
    public void setDeviceState(boolean ds){
        this.device_state=ds;
    }

}