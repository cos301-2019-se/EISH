package com.monotoneid.eishms.datapersistence.models;

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

    @Column(name = "homeconsumption", columnDefinition = "float", updatable = true, nullable = true)
    private Float homeConsumption;

    public HomeConsumption(){}
    public HomeConsumption(Timestamp newHomeConsumptionTimeStamp, Float newHomeConsumption){
        setHomeConsumptiontimeStamp(newHomeConsumptionTimeStamp);
        setHomeConsumption(newHomeConsumption);
    }
    //getter
    public Timestamp getHomeConsumptionTimeStamp(){
        return homeConsumptionTimeStamp;
    }
    public Float getHomeConsumption(){
        return homeConsumption;
    }
    //setter
    public void setHomeConsumptiontimeStamp(Timestamp newHomeConsumptionTimeStamp){
        this.homeConsumptionTimeStamp = newHomeConsumptionTimeStamp;
    }
    public void setHomeConsumption(Float newHomeConsumption){
        this.homeConsumption = newHomeConsumption;
    }


}