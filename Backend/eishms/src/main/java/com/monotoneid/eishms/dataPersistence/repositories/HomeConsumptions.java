 package com.monotoneid.eishms.dataPersistence.repositories;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import com.monotoneid.eishms.dataPersistence.models.HomeConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface HomeConsumptions extends JpaRepository<HomeConsumption,Long> {

    //custom query
    @Query(value="select * from homeconsumption  where homeconsumptiontimestamp between ?1 and ?2",nativeQuery =true)
    public Optional<List<HomeConsumption>> findByHomeConsumptionTimestampBetween(Timestamp startTimeStamp,Timestamp endTimeStamp);
  
     //quick ranges
    //last 10 minutes
    @Query(value="select * from homeconsumption where homeconsumptiontimestamp between now() - interval '10 minutes' and now() order by homeconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<HomeConsumption>> findHomeConsumptionLastTenMinutes();

    //last 1 hours
    @Query(value="select * from homeconsumption where homeconsumptiontimestamp between now() - interval '1 hours' and now() order by homeconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<HomeConsumption>> findHomeConsumptionLastHour();

    //last  1 day
    @Query(value="select * from homeconsumption where homeconsumptiontimestamp between now() - interval '1 days' and now() order by homeconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<HomeConsumption>> findHomeConsumptionLastOneDay();

    //last 1 week
    @Query(value="select * from homeconsumption where homeconsumptiontimestamp between now() - interval '1 weeks' and now() order by homeconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<HomeConsumption>> findHomeConsumptionLastOneWeek();

    //last 1 month
    @Query(value="select * from homeconsumption where homeconsumptiontimestamp between now() - interval '1 months' and now() order by homeconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<HomeConsumption>> findHomeConsumptionLastOneMonth();

    //last 1 year
    @Query(value="select * from homeconsumption where homeconsumptiontimestamp between now() - interval '1 years' and now() order by homeconsumptiontimestamp desc",nativeQuery=true)
    public Optional<List<HomeConsumption>> findHomeConsumptionLastOneYear();

    //current till fixed end
    //this hour
    @Query(value = "select  * from homeconsumption where homeconsumptiontimestamp between date_trunc('hour',now()) and now() order by homeconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<HomeConsumption>> findHomeConsumptionForThisHour();

    //this day
    @Query(value = "select  * from homeconsumption where homeconsumptiontimestamp between date_trunc('day',now()) and now() order by homeconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<HomeConsumption>> findHomeConsumptionForThisDay();
   
    //this week
    @Query(value = "select * from homeconsumption  where homeconsumptiontimestamp between date_trunc('week',now()) and now() order by homeconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<HomeConsumption>> findHomeConsumptionForThisWeek();
   
    //this month
    @Query(value = "select  * from homeconsumption where homeconsumptiontimestamp between date_trunc('month',now()) and now() order by homeconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<HomeConsumption>> findHomeConsumptionForThisMonth();

    //this year
    @Query(value = "select  * from homeconsumption where homeconsumptiontimestamp between date_trunc('year',now()) and now()  order by homeconsumptiontimestamp asc", nativeQuery = true)
    public Optional<List<HomeConsumption>> findHomeConsumptionForThisYear();

}