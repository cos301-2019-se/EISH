package com.monotoneid.eishms.dataPersistence.repositories;

import com.monotoneid.eishms.dataPersistence.models.Weather;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * .
 */
@Repository()
public interface Weathers extends JpaRepository<Weather,Long> {

    @Query(value = "select * from weather order by weatherid desc limit 1", nativeQuery = true)
    public Weather findLastWeather();
}