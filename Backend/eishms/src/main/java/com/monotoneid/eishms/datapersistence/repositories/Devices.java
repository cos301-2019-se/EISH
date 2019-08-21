package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.Device;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()

public interface Devices extends JpaRepository<Device,Long> {

}