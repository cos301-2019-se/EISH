package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;




public class UserService{
       
    @Value("${eishms.defaultNumberOfDays:3}")
    private int defaultNumberOfDays=3;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private Users usersRepository;


    
    public void setDefaulNumberOfDays(int newDefualtNumberofDays){
        this.defaultNumberOfDays = newDefualtNumberofDays;
    }
    public int getDefaultNumberOfDays(){
        return defaultNumberOfDays;
    }
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
        if(homeuser!= null){
            Timestamp newUserExpiryDate = calculateExpiryDate(defaultNumberOfDays);
            
            System.out.println(homeuser.getUserPassword());
            String password = encryptPassword(homeuser.getUserPassword());
            System.out.println(password);
            
            homeuser.setUserPassword(password);
            homeuser.setUserExpiryDate(newUserExpiryDate);
            long numberOfUser =usersRepository.count();
            usersRepository.save(homeuser);
            if (usersRepository.count()> numberOfUser+1)
                return "Create HomeUser successful";
            else    
                return "Create HomeUser unsuccessful";
            }
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
    public void retriveUser(){
        //user

    }
    
    public List<HomeUser> retriveAllUsers(){
       return usersRepository.findAll();
    }
    /**
     * section Users
     */
    
    public void updateUser(){
 
    }
 
    
    public void renewUser(){
 
    }
    
}