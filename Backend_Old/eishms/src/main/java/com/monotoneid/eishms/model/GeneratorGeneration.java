package com.monotoneid.eishms.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity(name="generatorgeneration")
@Table(name="generatorgeneration")
@EntityListeners(AuditingEntityListener.class)
public class GeneratorGeneration{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generation_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long generation_id;

    @ManyToOne
    @JoinColumn(name="generator_id")
    private Generators generator;

    @Column(name="capacity", columnDefinition = "float", updatable = true,nullable = false)
    private float capacity;

    @Column(name = "timeofgeneration", columnDefinition = "bigint", updatable = true, nullable = false)
    private long timeofgeneration;


    //getters
    public long getGenerationId(){
        return generation_id;
    }
    public Generators getGenerators(){
        return generator;
    }
    public float getCapacity(){
        return capacity;
    }
    public long getTimeofGeneration(){
        return timeofgeneration;
    }
    //setters

    public void setCapacity(float c){
        this.capacity=c;
    }
    public void setTimeofGeneration(long t){
        this.timeofgeneration=t;
    }
}