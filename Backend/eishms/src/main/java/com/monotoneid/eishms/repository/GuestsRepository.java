package com.monotoneid.eishms.repository;
import com.monotoneid.eishms.model.Guests;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface GuestsRepository extends JpaRepository<Guests,Float> {
}