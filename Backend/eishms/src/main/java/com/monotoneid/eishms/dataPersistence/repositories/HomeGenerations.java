package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.HomeGeneration;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *CLASS HOME GENERATIONS. 
 */
@Repository()
public interface HomeGenerations extends JpaRepository<HomeGeneration,Long> {

    //custom query
    @Query(value = "select * from homegeneration  where homegenerationtimestamp between ?1 and ?2", nativeQuery = true)
    public Optional<List<HomeGeneration>> findByHomeGenerationTimestampBetween(Timestamp startTimeStamp,Timestamp endTimeStamp);
  
     //quick ranges
    //last 10 minutes
    @Query(value="select * from homegeneration where homegenerationtimestamp between now() - interval '10 minutes' and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationLastTenMinutes();

    //last 1 hours
    @Query(value="select * from homegeneration where homegenerationtimestamp between now() - interval '1 hours' and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationLastHour();

    //last  1 day
    @Query(value="select * from homegeneration where homegenerationtimestamp between now() - interval '1 days' and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationLastOneDay();

    //last 1 week
    @Query(value="select * from homegeneration where homegenerationtimestamp between now() - interval '1 weeks' and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationLastOneWeek();

    //last 1 month
    @Query(value="select * from homegeneration where homegenerationtimestamp between now() - interval '1 months' and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationLastOneMonth();

    //last 1 year
    @Query(value="select * from homegeneration where homegenerationtimestamp between now() - interval '1 years' and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationLastOneYear();

    //current till fixed end
    //this hour
    @Query(value = "select  * from homegeneration where homegenerationtimestamp between date_trunc('hour',now()) and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationForThisHour();

    //this day
    @Query(value = "select  * from homegeneration where homegenerationtimestamp between date_trunc('day',now()) and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationForThisDay();
   
    //this week
    @Query(value = "select * from homegeneration  where homegenerationtimestamp between date_trunc('week',now()) and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationForThisWeek();
   
    //this month
    @Query(value = "select  * from homegeneration where homegenerationtimestamp between date_trunc('month',now()) and now() order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationForThisMonth();

    //this year
    @Query(value = "select  * from homegeneration where homegenerationtimestamp between date_trunc('year',now()) and now()  order by homegenerationtimestamp asc", nativeQuery = true)
    public Optional<List<HomeGeneration>> findHomeGenerationForThisYear();

    //total month
    @Query(value = "select sum(homegeneration) from homegeneration where homegenerationtimestamp between now() - interval '1 months' and now()", nativeQuery = true)
    public Float findTotalHomeGenerationMonth();

    //total day
    @Query(value = "select sum(homegeneration) from homegeneration where homegenerationtimestamp between now() - interval '1 days' and now()", nativeQuery = true)
    public Float findTotalHomeGenerationDay();

    //total week
    @Query(value = "select sum(homegeneration) from homegeneration where homegenerationtimestamp between now() - interval '1 weeks' and now()", nativeQuery = true)
    public Float findTotalHomeGenerationWeek();

}