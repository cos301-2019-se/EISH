package com.monotoneid.eishms.repository;
import com.monotoneid.eishms.model.Generators;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GeneratorsRepository extends JpaRepository<Generators,Float> {
}