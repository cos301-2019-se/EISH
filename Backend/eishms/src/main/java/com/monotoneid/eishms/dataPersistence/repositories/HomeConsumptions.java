package com.monotoneid.eishms.dataPersistence.repositories;

import com.monotoneid.eishms.dataPersistence.models.HomeConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()

public interface HomeConsumptions extends JpaRepository<HomeConsumption,Long> {

}