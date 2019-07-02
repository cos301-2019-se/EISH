package com.monotoneid.eishms.dataPersistence.repositories;

import com.monotoneid.eishms.dataPersistence.models.DeviceType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository()
public interface DeviceTypes extends JpaRepository<DeviceType,Long> {

}