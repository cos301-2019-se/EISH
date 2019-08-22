package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

/**
 *CLASS GENERATORGENERATION. 
 */
@Entity(name = "generatorgeneration")
@Table(name = "generatorgeneration")
@EntityListeners(AuditingEntityListener.class)
public class GeneratorGeneration {

    @EmbeddedId
    private GeneratorGenerationId generatorGenerationId;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "generatorid", 
                insertable = false, 
                updatable = false, 
                nullable = false)
    private Generator generator;

    @Column(name = "generatorgeneration", 
            columnDefinition = "float", 
            updatable = true, 
            nullable = true)
    private Float generatorGeneration;

    @Column(name = "generatorgenerationtimestamp", 
            columnDefinition = "TIMESTAMP", 
            insertable = false, 
            updatable = false, 
            nullable = false)
    private Timestamp generatorGenerationTimestamp;

    @Size(min = 1, message = "generator states must be one or more characters")
    @Column(name = "generatorgenerationstate", 
            columnDefinition = "text", 
            updatable = true, 
            nullable = false)
    private String generatorGenerationState;

    public GeneratorGeneration() {}

    /**. */
    public GeneratorGeneration(float newGeneratorGeneration, 
        Generator newGenerator, 
        Timestamp newGeneratorGenerationTimestamp, 
        String newGeneratorGenerationState) {
        setGeneratorGeneration(newGeneratorGeneration);
        setGenerator(newGenerator);
        setGeneratorGenerationTimestamp(newGeneratorGenerationTimestamp);
        setGeneratorGenerationState(newGeneratorGenerationState);
        setGeneratorGenerationId();

    }

    //getters
    @JsonIgnore
    public GeneratorGenerationId getGeneratorGenerationId() {
        return generatorGenerationId;
    }

    public Generator getGenerator() {
        return generator;
    }

    public Float getGeneratorGeneration() {
        return generatorGeneration;
    }

    public Timestamp getGeneratorGenerationTimestamp() {
        return generatorGenerationTimestamp;
    }

    @JsonIgnore
    public String getGeneratorGenerationState() {
        return generatorGenerationState;
    }

    //setters
    public void setGeneratorGenerationId() {
        this.generatorGenerationId = new GeneratorGenerationId(getGenerator().getGeneratorId(),
                getGeneratorGenerationTimestamp());
    }

    public void setGenerator(Generator newGenerator) {
        this.generator = newGenerator;
    }

    public void setGeneratorGeneration(Float newGeneratorGeneration) {
        this.generatorGeneration = newGeneratorGeneration;
    }

    public void setGeneratorGenerationTimestamp(Timestamp newGeneratorGenerationTimestamp) {
        this.generatorGenerationTimestamp = newGeneratorGenerationTimestamp;
    }

    public void setGeneratorGenerationState(String newGeneratorGenerationState) {
        this.generatorGenerationState = newGeneratorGenerationState;
    }
    
}