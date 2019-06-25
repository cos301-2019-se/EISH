package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.models.UserType;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseEntity<Object> addUser(HomeUser homeuser){
        try {
            if(homeuser==null){
                throw null;
            }
            if(homeuser.getUserName().isEmpty() || homeuser.getUserEmail().isEmpty()
            || homeuser.getUserLocationTopic().isEmpty() 
            || homeuser.getUserPassword().isEmpty()){
                throw null;
            }
            Timestamp newUserExpiryDate = calculateExpiryDate(defaultNumberOfDays);
            homeuser.setUserExpiryDate(newUserExpiryDate); 
            if(homeuser.getUserPassword()!=null){
            homeuser.setUserPassword(encryptPassword(homeuser.getUserPassword()));
            }
            if(homeuser.getUserName()!= null && homeuser.getUserEmail()!= null 
            && homeuser.getUserPassword()!=null && homeuser.getUserLocationTopic()!= null 
            && homeuser.getUserType()!= null && homeuser.getUserExpiryDate()!= null){
                usersRepository.save(homeuser);
                return new ResponseEntity<>("User added!",HttpStatus.OK);
            } else{
                throw null;
            }
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to add user details!",HttpStatus.PRECONDITION_FAILED);
        }
    }
    /**
     * hash and salt password
     * @param password
     * @return String 
     */
    private String encryptPassword(String password) throws Exception {
        return encoder.encode(password);        
    }
    private Timestamp  calculateExpiryDate(int numberOfDays) {
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
    public ResponseEntity<Object> removeUser(HomeUser homeUser) {
        try {
            usersRepository.findById(homeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            usersRepository.deleteById(homeUser.getUserId());
            return new ResponseEntity<>("Success: User has been deleted!",HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to delete home user!",HttpStatus.PRECONDITION_FAILED);
        }
    }
    /**
     * section Users
     */
    public ResponseEntity<HomeUser> retrieveUser(long userId) {
        try {
            HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            return new ResponseEntity<>(foundUser,HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage()+"!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        
    }
    
    public List<HomeUser> retrieveAllUsers(){
       return usersRepository.findAll();
    }

    /**
     * section Users
     */
    public String updateUserLocationTopic(HomeUser newHomeUserLocationTopic)  throws ResourceNotFoundException {
        if(newHomeUserLocationTopic==null){
           return "Failed to update user location topic";
        }else{
            if(newHomeUserLocationTopic.getUserLocationTopic().isEmpty()==true){
                return "failed to update user location topic";
            }
            HomeUser foundUser = usersRepository.findById(newHomeUserLocationTopic.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            foundUser.setUserLocationTopic(newHomeUserLocationTopic.getUserLocationTopic());
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" Location Topic Updated";
       }
    }

    public String updateUserType(HomeUser newHomeUserType)  throws ResourceNotFoundException {
        if(newHomeUserType==null){
           return "Failed to update user type";
        }else{
            if(newHomeUserType.getUserType()==UserType.ROLE_GUEST
            ||newHomeUserType.getUserType()==UserType.ROLE_RESIDENT
            ||newHomeUserType.getUserType()==null){
                return "failed to update user type";
            }
            HomeUser foundUser = usersRepository.findById(newHomeUserType.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            foundUser.setUserType(newHomeUserType.getUserType());
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" User Type Updated";
       }
    }

    public ResponseEntity<Object> updateUser(HomeUser newHomeUser) {
        try {
            if(newHomeUser==null){
                throw null;
            }else{
                if(newHomeUser.getUserLocationTopic().isEmpty()==true
                || newHomeUser.getUserName().isEmpty()==true
                || newHomeUser.getUserPassword().isEmpty()==true
                || newHomeUser.getUserEmail().isEmpty()==true){
                    throw null;
                }
                HomeUser foundUser = usersRepository.findById(newHomeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
                foundUser.setUserEmail(newHomeUser.getUserEmail());
                foundUser.setUserLocationTopic(newHomeUser.getUserLocationTopic());
                foundUser.setUserName(newHomeUser.getUserName());
                foundUser.setUserPassword(encryptPassword(newHomeUser.getUserPassword()));
                usersRepository.save(foundUser);
                return new ResponseEntity<>("Credentials Updated!",HttpStatus.OK);
            }
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to update user details!",HttpStatus.PRECONDITION_FAILED);
        }
    }
 
    /**
    * Changing users expiry date 
    */   
    public String renewUser(Long userId,HomeUser newHomeUserExpiry)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUserExpiry==null){
            return "Failed to update user expiry";
        }else{
            if(newHomeUserExpiry.getUserExpiryDate()==null){
                return "failed to update user expiry date";
            }
            foundUser.setUserExpiryDate(calculateExpiryDate(defaultNumberOfDays));
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" User Updated";
        }
    }
	    
}