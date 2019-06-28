package com.monotoneid.eishms.dataPersistence.repositories;

import java.util.Optional;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository()
public interface Users extends JpaRepository<HomeUser,Long>{
   // @Query("select new homeuser(username,useremail,userpassword,userlocation) from homeuser")
   // @Query(value="select userid,username,useremail,userpassword,userlocationtopic,usertype from homeuser",nativeQuery = true)
    //List<HomeUser> findAllHomeUsers();
    @Query(value="select * from homeuser where username=?1",nativeQuery = true)
    Optional<HomeUser> findByHomeUserName(String HomeUserName);
    
}