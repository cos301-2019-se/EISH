package com.monotoneid.eishms.datapersistence.repositories;

import com.monotoneid.eishms.datapersistence.models.HomeUser;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 *CLASS USERS. 
 */
@Repository()
public interface Users extends JpaRepository<HomeUser,Long> {
    @Query(value = "select * from homeuser where username=?1", 
            nativeQuery = true)
    Optional<HomeUser> findByHomeUserName(String newHomeUserName);
    
}