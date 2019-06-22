package com.monotoneid.eishms.services.databaseManagementSystem;

import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;

import com.google.common.hash.Hashing;
import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;



public class UserService{
    @Value("${eishms.salt}")
    private String salt;
    
    @Value("${eishms.defaultNumberOfDays}")
    private int defaultNumberOfDays;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private Users usersRepository;
    /**
     * section Users
     */
    public void getUserPresence(){}
    /**
     * This function will receive user credentials and store them to the database
     * it will generate a defualt expiry date, Hash a usersPassword
     * @param 
     */
    public String addUser(HomeUser homeuser){

        String password = encryptPassword(homeuser.getUserPassword());
        Timestamp newUserExpiryDate = calculateExpiryDate(defaultNumberOfDays);
        homeuser.setUserPassword(password);
        homeuser.setUserExpiryDate(newUserExpiryDate);
        usersRepository.save(homeuser);
        long numberOfUser =usersRepository.count();

        if (usersRepository.count()> numberOfUser+1)
            return "Created HomeUser successful";
        else    
            return "Create HomeUser unsuccessful";
        
    }
    /**
     * hash and salt password
     * @param passwordToHash
     * @return String 
     */
    private String encryptPassword(String password){
        return encoder.encode(password);
    }
    private Timestamp  calculateExpiryDate(int numberOfDays){
        return new Timestamp(System.currentTimeMillis()+convertDaysToMillSeconds(numberOfDays));
    }
    private long convertDaysToMillSeconds(int numberOfDays){
        if(numberOfDays > 0)
         return numberOfDays*24*60*60*1000;
        else
         return 1*24*60*60*1000;
    }
    /**
     * section Users
     */
    
    public void removeUser(){
 
    }
    /**
     * section Users
     */
    public void retriveUsers(){

    }
    
    public void retriveAllUsers(){
 
    }
    /**
     * section Users
     */
    
    public void updateUser(){
 
    }
 
    
    public void renewUser(){
 
    }
    
}