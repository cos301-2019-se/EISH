package com.monotoneid.eishms.repository;

import com.monotoneid.eishms.model.DeviceConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption,Long> {
    
}


