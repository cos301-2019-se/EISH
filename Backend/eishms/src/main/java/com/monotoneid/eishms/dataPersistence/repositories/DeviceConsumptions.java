package com.monotoneid.eishms.dataPersistence.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface DeviceConsumptions extends JpaRepository<DeviceConsumption,Long> {
@Query(value="select * from deviceconsumption where deviceid=?1",nativeQuery = true)
public Optional<List<DeviceConsumption>> getDeviceConsumptionofId(long deviceId);

@Query(value="insert into deviceconsumption(\"deviceid\",\"deviceconsumptiontimestamp\",\"deviceconsumptionstate\",\"deviceconsumption\") values(?1,?2,?3,?4);",nativeQuery = true)
public void insertIntoDeviceConsumption( long referenceDeviceId,  Timestamp newDeviceConsumptionTimestamp, String newDeviceConsumptionSate, float newdeviceConsumption);
}