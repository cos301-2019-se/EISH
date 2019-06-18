package com.monotoneid.eishms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name="homeoccupation")
@Table(name="homeoccupation")
@EntityListeners(AuditingEntityListener.class)
public class HomeOccupation{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "homeoccupation_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long  homeoccupation_id;

    @ManyToOne
    @JoinColumn(name="resident_id")
    private Residents resident;

    @Column(name="present", columnDefinition = "boolean",updatable = true,nullable = false)
    private boolean present;

    @Column(name="timeofoccupation",columnDefinition = "bigint",updatable = true,nullable = false)
    private long timeofoccupation;

    //getters
    public long getHomeOccupationId(){
        return this.homeoccupation_id;
    }
    public Residents getResident(){
        return this.resident;
    }
    public boolean getPresence(){
        return this.present;
    }
    public long getTimeOfOccupation(){

        return this.timeofoccupation;
    }
    //setters
    public void setPresence(boolean p){
        this.present=p;
    }
    public void setTimeOfConsumption(long toc){
        this.timeofoccupation=toc;
    }
}