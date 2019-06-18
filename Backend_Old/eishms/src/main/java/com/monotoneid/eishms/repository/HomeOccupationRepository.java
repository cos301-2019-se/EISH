package com.monotoneid.eishms.repository;
import com.monotoneid.eishms.model.HomeOccupation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface HomeOccupationRepository extends JpaRepository<HomeOccupation,Float> {
}