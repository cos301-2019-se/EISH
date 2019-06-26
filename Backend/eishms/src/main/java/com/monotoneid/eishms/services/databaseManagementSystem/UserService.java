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
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.NOT_FOUND);
        }
    }

    /**
     * section Users
     */
    public ResponseEntity<Object> removeUser(HomeUser homeUser) {
        try {
            if(homeUser == null)
                throw null;
            usersRepository.findById(homeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            usersRepository.deleteById(homeUser.getUserId());
            return new ResponseEntity<>("Success: User has been deleted!",HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: Input is " + e.getMessage() + "!");
            if(e.getMessage() == null)
                return new ResponseEntity<>("Error: Failed to delete home user!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to delete home user!",HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * section Users
     */
    public ResponseEntity<Object> getUserPresence(Long userId){
        try {
            HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            return new ResponseEntity<>(foundUser.getUserLocationTopic(),HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Error: Failed to get user presence!",HttpStatus.NOT_FOUND);
        }
    }

    /**
     * section Users
     */
    public ResponseEntity<Object> updateUserLocationTopic(HomeUser homeUser) {
        try {
            if(homeUser == null){
                throw null;
            }else{
                if(homeUser.getUserLocationTopic().isEmpty() == true){
                    throw null;
                }
                HomeUser foundUser = usersRepository.findById(homeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
                foundUser.setUserLocationTopic(homeUser.getUserLocationTopic());
                usersRepository.save(foundUser);
                return new ResponseEntity<>("Location Topic Updated",HttpStatus.OK);
            }
        } catch(Exception e) {
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateUserType(HomeUser homeUser) {
        try {
            if(homeUser ==null){
                throw null;
            }else{
                if(homeUser.getUserType()==UserType.ROLE_GUEST
                ||homeUser.getUserType()==UserType.ROLE_RESIDENT
                ||homeUser.getUserType()==null){
                    throw null;
                }
                HomeUser foundUser = usersRepository.findById(homeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
                foundUser.setUserType(homeUser.getUserType());
                usersRepository.save(foundUser);
                return new ResponseEntity<>("Users type updated!",HttpStatus.OK);
            }
        } catch(Exception e){
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.NOT_FOUND);
        }
    }
 
    /**
    * Changing users expiry date 
    */   
    public ResponseEntity<Object> renewUser(HomeUser homeUser) {
        try {
            if(homeUser == null)
                throw null;
            else if(homeUser.getUserExpiryDate()==null)
                    throw null;
            else{
                HomeUser foundUser = usersRepository.findById(homeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
                foundUser.setUserExpiryDate(calculateExpiryDate(defaultNumberOfDays));
                usersRepository.save(foundUser);
                return new ResponseEntity<>("User renewed!",HttpStatus.OK);
            }
        } catch(Exception e){
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.NOT_FOUND);
        }
    }
	    
}