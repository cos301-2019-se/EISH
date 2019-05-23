package com.monotoneid.eishms.repository;

import java.util.List;

import com.monotoneid.eishms.model.DeviceConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository()
public interface DeviceConsumptionRepository extends JpaRepository<DeviceConsumption,Long> {
   
}


