package com.monotoneid.eishms.repository;
import com.monotoneid.eishms.model.GeneratorGeneration;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GeneratorGenerationRepository extends JpaRepository<GeneratorGeneration,Float> {
}