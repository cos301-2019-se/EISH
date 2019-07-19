package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.Generator;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()

public interface Generators extends JpaRepository<Generator,Long> {

}