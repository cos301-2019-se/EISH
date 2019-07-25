package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.HomeUserPresence;
import com.monotoneid.eishms.datapersistence.models.HomeUserPresenceId;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()

public interface UserPresences extends JpaRepository<HomeUserPresence,HomeUserPresenceId> {

    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '2 minutes' and now() order by homeuserpresencetimestamp desc limit 1", nativeQuery = true)
    public Optional<HomeUserPresence> findCurrentHomeUserPresence(long userId);

    //custom query
    @Query(value = "select * from homeuserpresence  where userid= ?1 and homeuserpresencetimestamp between ?2 and ?3", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findByHomeUserPresenceIdAndHomeUserPresenceeTimestampBetween(long userId, Timestamp startTimeStamp, Timestamp endTimeStamp);
  
    //quick ranges
    //last 10 minutes
    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '10 minutes' and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findHomeUserPresenceLastTenMinutes(long userId);

    //last 1 hours
    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '1 hours' and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findHomeUserPresenceLastHour(long userId);

    //last  1 day
    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '1 days' and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findHomeUserPresenceLastOneDay(long userId);

    //last 1 week
    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '1 weeks' and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findHomeUserPresenceLastOneWeek(long userId);

    //last 1 month
    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '1 months' and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findHomeUserPresenceLastOneMonth(long userId);

    //last 1 year
    @Query(value = "select * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between now() - interval '1 years' and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findHomeUserPresenceLastOneYear(long userId);

    //current till fixed end
    //this hour 
    @Query(value = "select  * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between date_trunc('hour',now()) and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findByHomeUserPresenceForThisHour(long userId);
    
    //this day
    @Query(value = "select  * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between date_trunc('day',now()) and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findByHomeUserPresenceForThisDay(long userId);
   
    //this week
    @Query(value = "select * from homeuserpresence  where userid= ?1 and homeuserpresencetimestamp between date_trunc('week',now()) and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findByHomeUserPresenceForThisWeek(long userId);
   
    //this month
    @Query(value = "select  * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between date_trunc('month',now()) and now() order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findByHomeUserPresenceForThisMonth(long userId);

    //this year
    @Query(value = "select  * from homeuserpresence where userid= ?1 and homeuserpresencetimestamp between date_trunc('year',now()) and now()  order by homeuserpresencetimestamp asc", nativeQuery = true)
    public Optional<List<HomeUserPresence>> findByHomeUserPresenceForThisYear(long userId);

}