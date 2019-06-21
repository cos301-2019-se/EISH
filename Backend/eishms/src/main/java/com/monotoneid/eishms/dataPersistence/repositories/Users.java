package com.monotoneid.eishms.dataPersistence.repositories;

import java.util.Optional;

import com.monotoneid.eishms.dataPersistence.models.User;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository()
public interface Users extends JpaRepository<User,Float>{
    Optional<User> findByUserName(String userName);
}