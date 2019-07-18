package com.monotoneid.eishms.dataPersistence.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.monotoneid.eishms.dataPersistence.models.BatteryCapacity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface BatteryCapacities extends JpaRepository<BatteryCapacity,Long> {

    //default batteryLevel
    @Query(value = "select * from batterycapacity order by batterycapacityid desc limit 1")
    public BatteryCapacity findLastBatteryLevel();

    //custom query
    @Query(value = "select * from batterycapacity  where batterycapacitytimestamp between ?1 and ?2", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findByBatteryCapacityTimestampBetween(Timestamp startTimeStamp,Timestamp endTimeStamp);
  
     //quick ranges
    //last 10 minutes
    @Query(value = "select * from batterycapacity where batterycapacitytimestamp between now() - interval '10 minutes' and now() order by batterycapacitytimestamp desc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityLastTenMinutes();

    //last 1 hours
    @Query(value = "select * from batterycapacity where batterycapacitytimestamp between now() - interval '1 hours' and now() order by batterycapacitytimestamp desc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityLastHour();

    //last  1 day
    @Query(value = "select * from batterycapacity where batterycapacitytimestamp between now() - interval '1 days' and now() order by batterycapacitytimestamp desc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityLastOneDay();

    //last 1 week
    @Query(value = "select * from batterycapacity where batterycapacitytimestamp between now() - interval '1 weeks' and now() order by batterycapacitytimestamp desc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityLastOneWeek();

    //last 1 month
    @Query(value = "select * from batterycapacity where batterycapacitytimestamp between now() - interval '1 months' and now() order by batterycapacitytimestamp desc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityLastOneMonth();

    //last 1 year
    @Query(value = "select * from batterycapacity where batterycapacitytimestamp between now() - interval '1 years' and now() order by batterycapacitytimestamp desc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityLastOneYear();

    //current till fixed end
    //this hour
    @Query(value = "select  * from batterycapacity where batterycapacitytimestamp between date_trunc('hour',now()) and now() order by batterycapacitytimestamp asc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityForThisHour();

    //this day
    @Query(value = "select  * from batterycapacity where batterycapacitytimestamp between date_trunc('day',now()) and now() order by batterycapacitytimestamp asc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityForThisDay();
   
    //this week
    @Query(value = "select * from batterycapacity  where batterycapacitytimestamp between date_trunc('week',now()) and now() order by batterycapacitytimestamp asc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityForThisWeek();
   
    //this month
    @Query(value = "select  * from batterycapacity where batterycapacitytimestamp between date_trunc('month',now()) and now() order by batterycapacitytimestamp asc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityForThisMonth();

    //this year
    @Query(value = "select  * from batterycapacity where batterycapacitytimestamp between date_trunc('year',now()) and now()  order by batterycapacitytimestamp asc", nativeQuery = true)
    public Optional<List<BatteryCapacity>> findBatteryCapacityForThisYear();

}