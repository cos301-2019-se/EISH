package com.monotoneid.eishms.repository;
import com.monotoneid.eishms.model.Residents;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface ResidentsRepository extends JpaRepository<Residents,Float> {
}