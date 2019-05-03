package com.monotoneid.eishms.repository;

import com.monotoneid.eishms.model.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface DevicesRepository extends JpaRepository<Device,Float> {
}


