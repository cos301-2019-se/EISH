package com.monotoneid.eishms.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name="generators")
@Table(name="generators")
@EntityListeners(AuditingEntityListener.class)
public class Generators{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generator_id", columnDefinition ="serial" ,updatable = false,nullable = false )
    private long generator_id;

    @Column(name="generator_name", columnDefinition = "text", updatable = true,nullable = false)
    private String generator_name;

    @Column(name="generator_type", columnDefinition = "text", updatable = true,nullable = false)
    private String generator_type;

    @Column(name="generator_min_capacity", columnDefinition = "int",updatable = true, nullable=false)
    private int generator_min_capacity;

    @Column(name="generator_max_capacity", columnDefinition = "int",updatable = true, nullable=false)
    private int generator_max_capacity;

    @Column(name="generator_topic",columnDefinition = "text",updatable = true, nullable = false)
    private String generator_topic;

    @OneToMany(mappedBy = "generator")
    private List<GeneratorGeneration> generatorgenerations= new ArrayList<GeneratorGeneration>();

    //getters
    public long getGeneratorId(){
        return this.generator_id;
    }
    public String getGeneratorName(){
        return this.generator_name;
    }
    public String getGeneratorType(){
        return this.generator_type;
    }
    public int getGeneratorMinCapacity(){
        return this.generator_min_capacity;
    }
    public int getGeneratorMaxCapactiy(){
        return this.generator_max_capacity;
    }
    public String getGeneratorTopic(){
        return this.generator_topic;
    }
    public List<GeneratorGeneration> getGeneratorGeneration(){
        return generatorgenerations;
    }
    //setters
    public void setGeneratorName(String n){
        this.generator_name=n;
    }
    public void setGeneratorType(String t){
        this.generator_type=t;
    }
    public void setGeneratorMinCapacity(int mc){
        this.generator_min_capacity=mc;
    }
    public void setGeneratorMaxCapacity(int mc){
        this.generator_max_capacity=mc;
    }
    public void setGeneratorTopic(String gt){
        this.generator_topic=gt;
    }
}