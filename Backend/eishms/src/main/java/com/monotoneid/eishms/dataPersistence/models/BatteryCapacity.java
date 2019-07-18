package com.monotoneid.eishms.datapersistence.models;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.Type;
import org.hibernate.annotations.TypeDef;
import org.hibernate.annotations.TypeDefs;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity(name = "batterycapacity")
@Table(name = "batterycapacity")
@EntityListeners(AuditingEntityListener.class)
@TypeDefs({
    @TypeDef(
    name = "pgsql_enum",
    typeClass = PostgreSQLEnumType.class
)})
public class BatteryCapacity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "batterycapacityid", 
        columnDefinition = "serial", 
        updatable = false, 
        nullable = false)
    private long batteryCapacityId;
    @Column(name = "batterycapacitystorage", 
        columnDefinition = "int", 
        updatable = true, 
        nullable = false)
    private int batteryCapacityStorage;

    @Column(name = "batterycapacitycurrentpower", 
        columnDefinition = "int", 
        updatable = true, 
        nullable = false)
    private int batteryCapacityCurrentPower;
     
    @Enumerated(EnumType.STRING)
    @Column(name = "batterycapacitypowerstate", 
        columnDefinition = "powerStateType", 
        updatable = true, 
        nullable = false)
    @Type(type = "pgsql_enum")
    private PowerStateType batteryCapacityPowerState;

    @Enumerated(EnumType.STRING)
    @Column(name = "batterycapacitychargingstate", 
        columnDefinition = "chargingState", 
        updatable = true, 
        nullable = false)
    @Type(type = "pgsql_enum")
    private ChargingStateType batteryCapacityChargingState;
  
    @Column(name = "batterycapacitytimestamp", 
        columnDefinition = "TIMESTAMP", 
        insertable = false, 
        updatable = false, 
        nullable = false)
    private Timestamp batteryCapacityTimestamp;

    @Column(name = "batterycapacitypowerpercentage", 
        columnDefinition = "int", 
        updatable = true, 
        nullable = false)
    private int batteryCapacityPowerPercentage;

    public BatteryCapacity() {}

    public BatteryCapacity(int newBatteryCapacityStorage,
        int newBatteryCapacityCurrentPower,
        String newBatteryCapacityPowerState,
        String newBatteryCapacityChargingState,
        Timestamp newBatteryCapacityTimeStamp,
        int newBatteryCapacityPowerPercentage) {
        setBatteryCapacityStorage(newBatteryCapacityStorage);
        setBatteryCapacityCurrentPower(newBatteryCapacityCurrentPower);
        setBatteryCapacityPowerState(batteryCapacityPowerState
            .valueOf(newBatteryCapacityPowerState));
        setBatteryCapacityChargingState(batteryCapacityChargingState
            .valueOf(newBatteryCapacityChargingState));
        setBatteryCapacityTimeStamp(newBatteryCapacityTimeStamp);
        setBatteryCapacityPowerPercentage(newBatteryCapacityPowerPercentage);
    }

    public long getBatteryId() {
        return batteryCapacityId;
    }

    public int getBatteryCapacityStorage() {
        return batteryCapacityStorage;
    }

    public int getBatteryCapacityCurrentPower() {
        return batteryCapacityCurrentPower;
    }

    public PowerStateType getBatteryCapacityPowerState() {
        return batteryCapacityPowerState;
    }

    public ChargingStateType getbatteryCapacityChargingState() {
        return batteryCapacityChargingState;
    }

    public Timestamp getBatteryCapacityTimestamp() {
        return batteryCapacityTimestamp;
    }

    public int getBatteryCapacityPowerPercentage() {
        return batteryCapacityPowerPercentage;
    }

    public void setBatteryCapacityStorage(int newBatteryCapacityStorage) {
        this.batteryCapacityStorage = newBatteryCapacityStorage;
    }

    public void setBatteryCapacityCurrentPower(int newBatteryCapacityCurrentPower) {
        this.batteryCapacityCurrentPower = newBatteryCapacityCurrentPower;
    }

    public void setBatteryCapacityPowerState(PowerStateType newBatteryCapacityPowerState) {
        this.batteryCapacityPowerState = newBatteryCapacityPowerState;
    }

    public void setBatteryCapacityChargingState(ChargingStateType newBatteryCapacityChargingState) {
        this.batteryCapacityChargingState = newBatteryCapacityChargingState;
    }

    public void setBatteryCapacityTimeStamp(Timestamp newBatteryCapacityTimeStamp) {
        this.batteryCapacityTimestamp = newBatteryCapacityTimeStamp;
    }

    public void setBatteryCapacityPowerPercentage(int newBatteryCapacityPowerPercentage) {
        this.batteryCapacityPowerPercentage = newBatteryCapacityPowerPercentage;
    }
   


}