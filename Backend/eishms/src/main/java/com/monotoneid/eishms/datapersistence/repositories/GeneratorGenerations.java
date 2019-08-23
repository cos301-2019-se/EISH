package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.GeneratorGeneration;
import com.monotoneid.eishms.datapersistence.models.GeneratorGenerationId;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface GeneratorGenerations extends JpaRepository<GeneratorGeneration,GeneratorGenerationId> {

    //custom query
    @Query(value = "select * from generatorgeneration  where generatorid= ?1 and generatorgenerationtimestamp between ?2 and ?3", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findByGeneratorGenerationIdAndGeneratorGenerationTimestampBetween(long generatorId,Timestamp startTimeStamp,Timestamp endTimeStamp);
  
    //quick ranges
    //last 10 minutes
    @Query(value = "select * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between now() - interval '10 minutes' and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findGeneratorGenerationLastTenMinutes(long generatorId);

    //last 1 hours
    @Query(value = "select * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between now() - interval '1 hours' and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findGeneratorGenerationLastHour(long generatorId);

    //last  1 day
    @Query(value = "select * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between now() - interval '1 days' and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findGeneratorGenerationLastOneDay(long generatorId);

    //last 1 week
    @Query(value = "select * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between now() - interval '1 weeks' and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findGeneratorGenerationLastOneWeek(long generatorId);

    //last 1 month
    @Query(value = "select * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between now() - interval '1 months' and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findGeneratorGenerationLastOneMonth(long generatorId);

    //last 1 year
    @Query(value = "select * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between now() - interval '1 years' and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findGeneratorGenerationLastOneYear(long generatorId);

    //current till fixed end
    //this hour 
    //@SupressWarning()
    @Query(value = "select  * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between date_trunc('hour',now()) and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findByGeneratorGenerationIdForThisHour(long generatorId);
    
    //this day
    @Query(value = "select  * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between date_trunc('day',now()) and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findByGeneratorGenerationIdForThisDay(long generatorId);
   
    //this week
    @Query(value = "select * from generatorgeneration  where generatorid= ?1 and generatorgenerationtimestamp between date_trunc('week',now()) and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findByGeneratorGenerationIdForThisWeek(long generatorId);
   
    //this month
    @Query(value = "select  * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between date_trunc('month',now()) and now() order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findByGeneratorGenerationIdForThisMonth(long generatorId);

    //this year
    @Query(value = "select  * from generatorgeneration where generatorid= ?1 and generatorgenerationtimestamp between date_trunc('year',now()) and now()  order by generatorgenerationtimestamp asc", nativeQuery = true)
    public Optional<List<GeneratorGeneration>> findByGeneratorGenerationIdForThisYear(long generatorId);

    //average of device consumptions every minute
    @Query(value = "select AVG(generatorgeneration) from generatorgeneration where generatorgenerationtimestamp  between now()- interval '1 minutes' and now() group by generatorid", nativeQuery = true)
    public List<Float> findAverageEveryOneMinutes();

}