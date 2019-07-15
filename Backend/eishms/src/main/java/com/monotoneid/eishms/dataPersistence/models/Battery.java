package com.monotoneid.eishms.dataPersistence.models;

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

import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.vladmihalcea.hibernate.type.array.StringArrayType;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
@Entity(name = "battery")
@Table(name = "battery")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
    @TypeDef(
        name = "string-array",
        typeClass = StringArrayType.class
    )})
public class Battery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batteryid", columnDefinition = "serial", updatable = false, nullable = false)
    private long batteryId;
    
    @Size(min = 4, max = 30, message = "battery Name must be between 4 and 30 characters")
    @Column(name = "batteryname", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String batteryName;

    @Size(min = 4, max = 30, message = "battery url must be between 4 and 30 characters")
    @Column(name = "batteryurl", columnDefinition = "text", updatable = true, unique = true, nullable = false)
    private String batteryUrl;

    @Column(name = "batterytotalcapacity", columnDefinition = "float", updatable = true, nullable = false)
    private float batteryTotalCapacity;

    @Type( type = "string-array" )
    @Column(name = "batterystates", columnDefinition = "text[]", updatable = true, nullable = false)
    private String[] batteryStates;

    @JsonManagedReference
    @OneToMany(mappedBy = "battery")
    private List<BatteryCapacity> batteryCapacity = new ArrayList<BatteryCapacity>();

    public Battery(){}
    public Battery(@JsonProperty("batteryName") String newBatteryName,
    @JsonProperty("batteryUrl") String newBatteryUrl,
    @JsonProperty("batteryTotalCapacity") float newBatteryTotalCapacity,
    @JsonProperty("batteryStates") String[] newBatteryStates){
        setBatteryName(newBatteryName);
        setBatteryUrl(newBatteryUrl);
        setBatteryTotalCapacity(newBatteryTotalCapacity);
        String[] newStates = new String[newBatteryStates.length];
        for(int i=0;i<newBatteryStates.length;i++){
            newStates[i] = new String(newBatteryStates[i]);
        }
        setBatteryStates(newStates);
        
    }
    //getter
    public long getBatteryId() {
        return batteryId;
    }
    public String getBatteryName() {
        return batteryName;
    }
    public String getBatteryUrl() {
        return batteryUrl;
    }
    public String[] getBatteryStates() {
        return batteryStates;
    }
    public float getBatteryTotalCapacity() {
        return batteryTotalCapacity;
    }
    public List<BatteryCapacity> getBatteryCapacity() {
        return batteryCapacity;
    }

    //setter
    public void setBatteryName(String newBatteryName) {
        this.batteryName = newBatteryName;
    }
    public void setBatteryUrl(String newBatteryUrl) {
        this.batteryUrl = newBatteryUrl;
    }
    public void setBatteryStates(String[] newBatteryStates) {
        this.batteryStates = newBatteryStates;
    }
    public void setBatteryTotalCapacity(float newBatteryTotalCapacity) {
        this.batteryTotalCapacity=newBatteryTotalCapacity;
    }
}