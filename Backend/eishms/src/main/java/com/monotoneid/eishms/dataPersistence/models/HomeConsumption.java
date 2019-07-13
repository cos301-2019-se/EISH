package com.monotoneid.eishms.dataPersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

 import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "homeconsumption")
@Table(name = "homeconsumption")
@EntityListeners(AuditingEntityListener.class)
public class HomeConsumption {

    @Id
    @Column(name = "homeconsumptiontimestamp", columnDefinition = "TIMESTAMP", updatable = true, nullable = false)
    private Timestamp homeConsumptionTimeStamp;

    @Column(name = "homeconsumption", columnDefinition = "float", updatable = true, nullable = false)
    private float homeConsumption;

    //getter
    public Timestamp getHomeConsumptionTimeStamp(){
        return homeConsumptionTimeStamp;
    }
    public float getHomeConsumption(){
        return homeConsumption;
    }
    //setter
    public void setHomeConsumptiontimeStamp(Timestamp newHomeConsumptionTimeStamp){
        this.homeConsumptionTimeStamp = newHomeConsumptionTimeStamp;
    }
    public void setHomeConsumption(float newHomeConsumption){
        this.homeConsumption = newHomeConsumption;
    }


}