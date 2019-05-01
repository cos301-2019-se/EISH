package com.monotoneid.eishms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name="HomeConsumption")
@EntityListeners(AuditingEntityListener.class)
public class HomeConsumption{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "home_consumption_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private int home_consumption_id;
    @Column(name="start_time", columnDefinition = "long", updatable = true,nullable = false)
    private long start_time;
    @Column(name="end_time", columnDefinition = "long", updatable = true,nullable = false)
    private long end_time;
    @Column(name="total_consumption", columnDefinition = "float", updatable = true,nullable = false)
    private float total_consumption;
    //setters
    public void setStartTime(long st){
        this.start_time=st;
    }
    public void setEndTime(long et){
        this.end_time=et;
    }
    public void setTotalConsumption(float tc){
        this.total_consumption=tc;
    }
    //getters
    public int getHomeConsumptionID(){
        return home_consumption_id;
    }
    public long getStartTime(){
        return start_time;
    }
    public long getEndTime(){
        return end_time;
    }
    public float getTotalConsumption(){
        return total_consumption;
    }
}