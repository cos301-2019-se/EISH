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

public interface DeviceConsumptions extends JpaRepository<DeviceConsumption,DeviceConsumptionId>{

    @Query(value="select * from deviceconsumption  where deviceid= ?1 and deviceconsumptiontimestamp between ?2 and ?3",nativeQuery =true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdAndDeviceConsumptionTimestampBetween(long deviceid,Timestamp startTimeStamp,Timestamp endTimeStamp);
  
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp>=now() - interval ?2 order by deviceconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionBetweenInterval(long deviceId,String interval);
    
}
