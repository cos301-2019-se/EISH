package com.monotoneid.eishms.datapersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *CLASS HOMEGENERATION MODEL. 
 */
@Entity(name = "homegeneration")
@Table(name = "homegeneration")
@EntityListeners(AuditingEntityListener.class)
public class HomeGeneration {

    @Id
    @Column(name = "homegenerationtimestamp", 
            columnDefinition = "TIMESTAMP", 
            updatable = true, 
            nullable = false)
    private Timestamp homeGenerationTimeStamp;

    @Column(name = "homegeneration", 
            columnDefinition = "float", 
            updatable = true, 
            nullable = true)
    private Float homeGeneration;

    public HomeGeneration(){}

    /**. */
    public HomeGeneration(Timestamp newHomeGenerationTimeStamp, Float newHomeGeneration) {
        setHomeGenerationtimeStamp(newHomeGenerationTimeStamp);
        setHomeGeneration(newHomeGeneration);
    }

    //getter
    public Timestamp getHomeGenerationTimeStamp() {
        return homeGenerationTimeStamp;
    }

    public Float getHomeGeneration() {
        return homeGeneration;
    }

    //setter
    public void setHomeGenerationtimeStamp(Timestamp newHomeGenerationTimeStamp) {
        this.homeGenerationTimeStamp = newHomeGenerationTimeStamp;
    }

    public void setHomeGeneration(Float newHomeGeneration) {
        this.homeGeneration = newHomeGeneration;
    }

}