package com.monotoneid.eishms.dataPersistence.repositories;



import com.monotoneid.eishms.dataPersistence.models.HomeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface Users extends JpaRepository<HomeUser,Float>{
    
}