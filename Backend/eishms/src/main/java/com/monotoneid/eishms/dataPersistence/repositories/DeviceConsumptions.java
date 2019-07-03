package com.monotoneid.eishms.dataPersistence.repositories;


import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()

public interface DeviceConsumptions extends JpaRepository<DeviceConsumption,Long> {

}