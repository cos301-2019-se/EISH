package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;


import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.models.UserType;
import com.monotoneid.eishms.datapersistence.repositories.Users;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class UserService{
       
    @Value("${eishms.defaultNumberOfDays:1}")
    private int defaultNumberOfDays=1;

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
     * @param homeUser
     * @return Object
     * @exception null
     */
    public ResponseEntity<Object> addUser(HomeUser homeUser){
        try {
            if(homeUser==null){
                throw null;
            }
            if(homeUser.getUserName().isEmpty() || homeUser.getUserEmail().isEmpty()
            || homeUser.getUserLocationTopic().isEmpty() 
            || homeUser.getUserPassword().isEmpty()){
                throw null;
            }
            Timestamp newUserExpiryDate = calculateExpiryDate(defaultNumberOfDays);
            homeUser.setUserExpiryDate(newUserExpiryDate); 
            if(homeUser.getUserPassword()!=null){
            homeUser.setUserPassword(encryptPassword(homeUser.getUserPassword()));
            }
            if(homeUser.getUserName()!= null && homeUser.getUserEmail()!= null 
            && homeUser.getUserPassword()!=null && homeUser.getUserLocationTopic()!= null 
            && homeUser.getUserType()!= null && homeUser.getUserExpiryDate()!= null){
                usersRepository.save(homeUser);
                JSONObject responseObject = new JSONObject();
                responseObject.put("message","User added!");
                return new ResponseEntity<>(responseObject,HttpStatus.OK);
            } else{
                throw null;
            }
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to add user!",HttpStatus.PRECONDITION_FAILED);
        }
    }

    /**
     * Retrives user with specified UserName.
     * @param homeUserName
     * @return HomeUser
     * @exception ResourceNotFound
     */
    public ResponseEntity<HomeUser> retrieveUser(String homeUserName) {
        try {
            HomeUser foundUser = usersRepository.findByHomeUserName(homeUserName).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            return new ResponseEntity<>(foundUser,HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage()+"!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Retrieves all the users in the database
     * @return List of HomeUsers
     */
    public List<HomeUser> retrieveAllUsers(){
        return usersRepository.findAll();
    }

    /**
     * Updates the attributes of specified user with parsed in data
     * @param newHomeUser
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
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
                HomeUser foundUser = usersRepository.findById(newHomeUser.getUserId()).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist!"));
                foundUser.setUserEmail(newHomeUser.getUserEmail());
                foundUser.setUserLocationTopic(newHomeUser.getUserLocationTopic());
                foundUser.setUserName(newHomeUser.getUserName());
                foundUser.setUserPassword(encryptPassword(newHomeUser.getUserPassword()));
                usersRepository.save(foundUser);
                JSONObject responseObject = new JSONObject();
                responseObject.put("message","Credentials Updated!");
                return new ResponseEntity<>(responseObject,HttpStatus.OK);
            }
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update user credentials!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update user credentials!",HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes the specified user from database.
     * @param homeUser
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
    public ResponseEntity<Object> removeUser(long userId) {
        try {
            usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            usersRepository.deleteById(userId);
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","Success: User has been deleted!");
            return new ResponseEntity<>(responseObject,HttpStatus.OK);
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
    public ResponseEntity<Object> getUserPresence(String homeUserName){
        try {
            HomeUser foundUser = usersRepository.findByHomeUserName(homeUserName).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            return new ResponseEntity<>(foundUser.getUserLocationTopic(),HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Error: Failed to get user presence!",HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> updateUserType(long userId, String role) {
        try {            
            if(UserType.valueOf(role) == UserType.ROLE_GUEST
            ||UserType.valueOf(role) == UserType.ROLE_RESIDENT
            ||UserType.valueOf(role) == null)
                throw null;
        
            HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            foundUser.setUserType(UserType.valueOf(role));
            usersRepository.save(foundUser);
            if(foundUser.getUserType() == UserType.ROLE_GUEST)
                renewUser(userId, 1);
            else if(foundUser.getUserType() == UserType.ROLE_RESIDENT)
                renewUser(userId, 365);
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","Users type updated!");
            return new ResponseEntity<>(responseObject,HttpStatus.OK);
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
    public ResponseEntity<Object> renewUser(long userId, int days) {
        try {
            HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
            foundUser.setUserExpiryDate(calculateExpiryDate(days));
            usersRepository.save(foundUser);
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","User renewed!");
            return new ResponseEntity<>(responseObject,HttpStatus.OK);
        } catch(Exception e){
            return new ResponseEntity<>("Error: Failed to update user type!",HttpStatus.NOT_FOUND);
        }
    }
	    
}