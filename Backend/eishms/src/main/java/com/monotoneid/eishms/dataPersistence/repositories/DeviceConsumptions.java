package com.monotoneid.eishms.dataPersistence.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;
import com.monotoneid.eishms.dataPersistence.models.DeviceConsumptionId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface DeviceConsumptions extends JpaRepository<DeviceConsumption,DeviceConsumptionId> {

    
    //@Query(name="select * from deviceconsumption where deviceid=?1 and deviceconsumptiontimestamp>=?2 and deviceconsumptiontimestamp<=?3",nativeQuery =true)
    //public Optional<List<DeviceConsumption>> findFilteredDeviceConsumption(long deviceid,Timestamp startTimeStamp,Timestamp endTimeStamp);

    //public List<DeviceConsumption> findAllByIdBetween(DeviceConsumptionId startDeviceConsumptionId,DeviceConsumptionId endDeviceConsumptionId);
   // public Optional<List<DeviceConsumption>> findAllByDeviceId(long deviceId);
   
   @Query(name="select * from deviceconsumption where deviceid= ?1 and (deviceconsumptiontimestamp between ?2 and ?3)",nativeQuery =true)
    public List<DeviceConsumption> findByDeviceConsumptionBetween(long deviceid,Timestamp startTimeStamp,Timestamp endTimeStamp);
}