package com.monotoneid.eishms.dataPersistence.repositories;

import com.monotoneid.eishms.dataPersistence.models.Generator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()

public interface Generators extends JpaRepository<Generator,Long> {

}