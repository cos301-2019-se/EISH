package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
//import com.monotoneid.eishms.dataPersistence.models.UserType;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;



@Service
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
        if(homeuser==null){
            return "could not create user";
        }
        if(homeuser.getUserName().isEmpty() || homeuser.getUserEmail().isEmpty()
        || homeuser.getUserLocationTopic().isEmpty() 
        || homeuser.getUserPassword().isEmpty()){
            return "could not create user";
        }
        Timestamp newUserExpiryDate = calculateExpiryDate(defaultNumberOfDays);
        homeuser.setUserExpiryDate(newUserExpiryDate);
        HomeUser savedHomeUser=null; 
        if(homeuser.getUserPassword()!=null){
           homeuser.setUserPassword(encryptPassword(homeuser.getUserPassword()));
        }
        if(homeuser.getUserName()!= null && homeuser.getUserEmail()!= null 
        && homeuser.getUserPassword()!=null && homeuser.getUserLocationTopic()!= null 
        && homeuser.getUserType()!= null && homeuser.getUserExpiryDate()!= null){
            savedHomeUser = usersRepository.save(homeuser);
            return savedHomeUser.getUserName()+" creation successful";
        } else{
            return savedHomeUser.getUserName()+" creation failed";
        }
}
    /**
     * hash and salt password
     * @param password
     * @return String 
     */
    private String encryptPassword(String password){
        return encoder.encode(password);
        }       
        
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
    
    public String removeUser(Long userId) throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        usersRepository.deleteById(userId);
        return foundUser.getUserName()+" has been deleted";
    }
    /**
     * section Users
     */
    public HomeUser retrieveUser(long userId) throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        return foundUser;
    }
    
    public List<HomeUser> retrieveAllUsers(){
       return usersRepository.findAll();
    }
    /**
     * section Users
     */
    
    public String updateUserName(long userId, HomeUser newHomeUser)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        foundUser.setUserName(newHomeUser.getUserName());
        usersRepository.save(foundUser);
        return "UserName changed";
     }
    /**
     * section Users
     */
    
    public String updateUserEmail(Long userId,HomeUser newHomeUserEmail)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        foundUser.setUserEmail(newHomeUserEmail.getUserEmail());
        System.out.println(foundUser.getUserEmail());
        System.out.println(foundUser.getUserName());
        System.out.println(foundUser.getUserLocationTopic());
        System.out.println(foundUser.getUserId());
        System.out.println(foundUser.getUserPassword());
        System.out.println(foundUser.getUserExpiryDate());
        System.out.println(foundUser.getUserType());
       final HomeUser updatedHomeUser= usersRepository.save(foundUser);
        return updatedHomeUser.getUserName()+" Email Updated";
    }
    /**
     * section Users
     */
    
    public String updateUserPassword(Long userId,HomeUser newHomeUserLocationTopic)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        foundUser.setUserPassword(newHomeUserLocationTopic.getUserPassword());
        usersRepository.save(foundUser);
        return foundUser.getUserName()+ " password updated";
 
    }
    /**
     * section Users
     */
    
    public String updateUserLocationTopic(Long userId,HomeUser newHomeUserLocationTopic)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        foundUser.setUserLocationTopic(newHomeUserLocationTopic.getUserLocationTopic());
        usersRepository.save(foundUser);
        return foundUser.getUserName()+" Location Topic Updated";
    }

    public String updateUserType(Long userId,HomeUser newHomeUserType)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
      // if(newHomeUserType.getUserType()!=UserType.ROLE_ADMIN){
        foundUser.setUserType(newHomeUserType.getUserType());
       //}
        
        //usersRepository.save(foundUser);
        return foundUser.getUserName()+" Type Updated";
    }

    public String updateUser(Long userId,HomeUser newHomeUser)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        foundUser.setUserEmail(newHomeUser.getUserEmail());
        foundUser.setUserLocationTopic(newHomeUser.getUserLocationTopic());
        
        usersRepository.save(foundUser);
        return foundUser.getUserName()+" User Updated";
    }
 
    /**
    * Changing users expiry date 
    */   
    public void renewUser(){
 
    }
	    
}