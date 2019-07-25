package com.monotoneid.eishms.datapersistence.models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "generator")
@Table(name = "generator")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    ),
    @TypeDef(
        name = "pgsql_enum",
        typeClass = PostgreSQLEnumType.class
)})
public class Generator {
         
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "generatorid", 
            columnDefinition = "serial", 
            updatable = false, 
            nullable = false)
    private long generatorId;
    
    @Size(min = 4, 
            max = 30, 
            message = "generator Name must be between 4 and 30 characters")
    @Column(name = "generatorname", 
            columnDefinition = "text", 
            updatable = true, 
            unique = true, 
            nullable = false)
    private String generatorName;

    @Size(min = 4, 
            max = 30, 
            message = "generator url must be between 4 and 30 characters")
    @Column(name = "generatorurl", 
            columnDefinition = "text", 
            updatable = true, 
            unique = true, 
            nullable = false)
    private String generatorUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "generatorpriority", 
            columnDefinition = "generatorPriorityType", 
            updatable = true, 
            nullable = false)
    @Type(type = "pgsql_enum")
    private GeneratorPriorityType generatorPriority;

    //@Size(min = 1, message = "number of device states must be one or more")
    @Type(type = "string-array")
    @Column(name = "generatorstates", 
            columnDefinition = "text[]", 
            updatable = true, 
            nullable = false)
    private String[] generatorStates;

    
    @JsonManagedReference
    @OneToMany(mappedBy = "generator")
    private List<GeneratorGeneration> generatorGeneration = new ArrayList<GeneratorGeneration>();

    public Generator(){}

    public Generator(@JsonProperty("generatorName") String newGeneratorName,
        @JsonProperty("generatorUrl") String newGeneratorUrl,
        @JsonProperty("generatorPriorityType") String newGeneratorPriorityType,
        @JsonProperty("generatorStates") String[] newGeneratorStates) {
        setGeneratorName(newGeneratorName);
        setGeneratorUrl(newGeneratorUrl);
        setGeneratorPriorityType(generatorPriority.valueOf(newGeneratorPriorityType));
        String[] newStates = new String[newGeneratorStates.length];
        for (int i = 0; i < newGeneratorStates.length; i++) {
            newStates[i] = new String(newGeneratorStates[i]);
        }
        setGeneratorStates(newStates);
        
    }
  
    //getter
    public long getGeneratorId() {
        return generatorId;
    }

    public String getGeneratorName() {
        return generatorName;
    }

    public String getGeneratorUrl() {
        return generatorUrl;
    }

    public String[] getGeneratorStates() {
        return generatorStates;
    }

    public GeneratorPriorityType getGeneratorPriority() {
        return generatorPriority;
    }
    
    public List<GeneratorGeneration> getGeneratorGeneration() {
        return generatorGeneration;
    }
    
    
    //setter
    public void setGeneratorName(String newGeneratorName) {
        this.generatorName = newGeneratorName;
    }

    public void setGeneratorUrl(String newGeneratorUrl) {
        this.generatorUrl = newGeneratorUrl;
    }

    public void setGeneratorStates(String[] newGeneratorStates) {
        this.generatorStates = newGeneratorStates;
    }

    public void setGeneratorPriorityType(GeneratorPriorityType newGeneratorPriorityType) {
        this.generatorPriority = newGeneratorPriorityType;
    }

    public void setGeneratorGeneration(List<GeneratorGeneration> newGeneratorGeneration) {
        this.generatorGeneration = newGeneratorGeneration;
    }
}