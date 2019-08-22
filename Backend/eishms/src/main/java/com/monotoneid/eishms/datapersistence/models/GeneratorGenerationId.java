package com.monotoneid.eishms.datapersistence.models;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Objects;

import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *CLASS GENERATORGENERATIONID. 
 */
@Embeddable
public class GeneratorGenerationId implements Serializable {

    private static final long serialVersionUID = 1L;
    
    @Column(name = "generatorid")
    private long generatorId;

    @Column(name = "generatorgenerationtimestamp")
    private Timestamp generatorGenerationTimestamp;

    public GeneratorGenerationId(){

    }

    public GeneratorGenerationId(long newGeneratorId,
         Timestamp newGeneratorGenerationTimestamp) {
        this.generatorId = newGeneratorId;
        this.generatorGenerationTimestamp = newGeneratorGenerationTimestamp;
    }

    //getters
    public long getGeneratorId() {
        return generatorId;
    }

    public Timestamp getGeneratorGenerationTimeStamp() {
        return generatorGenerationTimestamp;
    }
        
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        } 
        if (!(o instanceof GeneratorGenerationId)) {
            return false;
        } 
        GeneratorGenerationId that = (GeneratorGenerationId) o;
        return Objects.equals(getGeneratorId(), that.getGeneratorId())
            && Objects.equals(getGeneratorGenerationTimeStamp(),
                 that.getGeneratorGenerationTimeStamp());
    }
 
    @Override
    public int hashCode() {
        return Objects.hash(getGeneratorId(), getGeneratorGenerationTimeStamp());
    }
}