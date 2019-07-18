package com.monotoneid.eishms.datapersistence.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.monotoneid.eishms.datapersistence.models.DeviceConsumption;
import com.monotoneid.eishms.datapersistence.models.DeviceConsumptionId;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface DeviceConsumptions extends JpaRepository<DeviceConsumption,DeviceConsumptionId> {

    //custom query
    @Query(value="select * from deviceconsumption  where deviceid= ?1 and deviceconsumptiontimestamp between ?2 and ?3", nativeQuery = true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdAndDeviceConsumptionTimestampBetween(long deviceId, Timestamp startTimeStamp, Timestamp endTimeStamp);
  
    //quick ranges
    //last 10 minutes
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between now() - interval '10 minutes' and now() order by deviceconsumptiontimestamp asc", nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionLastTenMinutes(long deviceId);

    //last 1 hours
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between now() - interval '1 hours' and now() order by deviceconsumptiontimestamp asc", nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionLastHour(long deviceId);

    //last  1 day
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between now() - interval '1 days' and now() order by deviceconsumptiontimestamp asc", nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionLastOneDay(long deviceId);

    //last 1 week
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between now() - interval '1 weeks' and now() order by deviceconsumptiontimestamp asc", nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionLastOneWeek(long deviceId);

    //last 1 month
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between now() - interval '1 months' and now() order by deviceconsumptiontimestamp asc", nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionLastOneMonth(long deviceId);

    //last 1 year
    @Query(value="select * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between now() - interval '1 years' and now() order by deviceconsumptiontimestamp asc", nativeQuery=true)
    public Optional<List<DeviceConsumption>> findDeviceConsumptionLastOneYear(long deviceId);

    //current till fixed end
    //this hour 
    @Query(value = "select  * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between date_trunc('hour',now()) and now() order by deviceconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdForThisHour(long deviceId);
    
    //this day
    @Query(value = "select  * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between date_trunc('day',now()) and now() order by deviceconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdForThisDay(long deviceId);
   
    //this week
    @Query(value = "select * from deviceconsumption  where deviceid= ?1 and deviceconsumptiontimestamp between date_trunc('week',now()) and now() order by deviceconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdForThisWeek(long deviceId);
   
    //this month
    @Query(value = "select  * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between date_trunc('month',now()) and now() order by deviceconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdForThisMonth(long deviceId);

    //this year
    @Query(value = "select  * from deviceconsumption where deviceid= ?1 and deviceconsumptiontimestamp between date_trunc('year',now()) and now()  order by deviceconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<DeviceConsumption>> findByDeviceConsumptionIdForThisYear(long deviceId);

    //average of device consumptions every minute
    @Query(value="select AVG(deviceconsumption) from deviceconsumption where deviceconsumptiontimestamp  between now()- interval '1 minutes' and now() group by deviceid", nativeQuery = true)
    //public Optional<List<Float>> findAverageEveryOneMinutes();
    public List<Float> findAverageEveryOneMinutes();
}
