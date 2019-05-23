package com.monotoneid.eishms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name="deviceconsumption")
@Table(name="deviceconsumption")
@EntityListeners(AuditingEntityListener.class)
public class DeviceConsumption{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "consumption_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long consumption_id;

    @ManyToOne
    @JoinColumn(name="device_id")
    private Devices device;

    @Column(name="consumption", columnDefinition = "float", updatable = true,nullable = false)
    private float consumption;

    @Column(name = "timeofconsumption", columnDefinition = "bigint", updatable = true, nullable = false)
    private long timeofconsumption;

   // private long newDeviceId;

    public DeviceConsumption(){}
    public DeviceConsumption(float c,long t){
        this.setConsumption(c);
        this.setConsumptionTime(t);
    }

    //getters
    public long getConsumptionId(){
        return consumption_id;
    }
    public Devices getDevice(){
        return device;
    }
    public float getConsumption(){
        return consumption;
    }
    public long getTimeOfConsumption(){
        return timeofconsumption;
    }
    
    //setters
    public void setConsumption(float c){
        this.consumption=c;
    }
    public void setConsumptionTime(long t){
        this.timeofconsumption=t;
    }
  //  public void setDeviceConsumptionID(long d){
  //      this.newDeviceId=d;
   // }


    


}