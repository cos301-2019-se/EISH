package com.monotoneid.eishms.services.databaseManagementSystem;

import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import com.google.common.hash.Hashing;
import com.monotoneid.eishms.dataPersistence.models.User;
import com.monotoneid.eishms.dataPersistence.repositories.Users;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;

public class UserService{
    @Value("${eishms.salt}")
    private String salt;
    /**
     * section Users
     */
    public void getUserPresence(){}
    /**
     * This function will receive user credentials and store them to the database
     * it will generate a defualt expiry date, Hash a usersPassword
     * @param 
     */
    public String addUser(User user){
        String password=HashAndSaltPassword(user.getUserPassword());
        user.setUserPassword(password);
        return "respose";
        
    }
    /**
     * hash and salt password
     * @param passwordToHash
     * @return String 
     */
    private String HashAndSaltPassword(String passwordToHash){
        String sha256hex = Hashing.sha256().hashString(passwordToHash, StandardCharsets.UTF_8).toString();
        String newPassword= sha256hex+salt;
        String finalPassword = Hashing.sha256().hashString(newPassword, StandardCharsets.UTF_8).toString();
        return finalPassword;
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