package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.models.UserType;
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
    
    public String updateUserName(long userId, HomeUser newHomeUserName)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUserName==null){
           return "Failed to update user name";
       }else{
            if(newHomeUserName.getUserName().isEmpty()==true){
                return "failed to update user name";
            }
            foundUser.setUserName(newHomeUserName.getUserName());
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" Name Updated";
       }
     }
    /**
     * section Users
     */
    
    public String updateUserEmail(Long userId,HomeUser newHomeUserEmail)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUserEmail==null){
           return "Failed to update user email";
       }else{
            if(newHomeUserEmail.getUserEmail().isEmpty()==true){
                return "failed to update user email";
            }
            foundUser.setUserEmail(newHomeUserEmail.getUserEmail());
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" Email Updated";
       }     
    }
    /**
     * section Users
     */
    
    public String updateUserPassword(Long userId,HomeUser newHomeUserPassword)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUserPassword==null){
            return "failed to update user password";
        }else{
            if(newHomeUserPassword.getUserPassword().isEmpty()==true){
                return "failed to update user password";
            }
            foundUser.setUserPassword(encryptPassword(newHomeUserPassword.getUserPassword()));
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" Password Updated";
        }
    }
    /**
     * section Users
     */
    
    public String updateUserLocationTopic(Long userId,HomeUser newHomeUserLocationTopic)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUserLocationTopic==null){
           return "Failed to update user location topic";
       }else{
            if(newHomeUserLocationTopic.getUserLocationTopic().isEmpty()==true){
                return "failed to update user location topic";
            }
            foundUser.setUserLocationTopic(newHomeUserLocationTopic.getUserLocationTopic());
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" Location Topic Updated";
       }
    }

    public String updateUserType(Long userId,HomeUser newHomeUserType)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUserType==null){
           return "Failed to update user type";
       }else{
            if(newHomeUserType.getUserType()==UserType.ROLE_GUEST
            ||newHomeUserType.getUserType()==UserType.ROLE_RESIDENT
            ||newHomeUserType.getUserType()==null){
                return "failed to update user type";
            }
            foundUser.setUserType(newHomeUserType.getUserType());
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" User Type Updated";
       }
    }

    public String updateUser(Long userId,HomeUser newHomeUser)  throws ResourceNotFoundException {
        HomeUser foundUser = usersRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("HomeUser does not exist"));
        if(newHomeUser==null){
            return "Failed to update user details";
        }else{
             if(newHomeUser.getUserLocationTopic().isEmpty()==true
             || newHomeUser.getUserName().isEmpty()==true
             || newHomeUser.getUserPassword().isEmpty()==true
             || newHomeUser.getUserEmail().isEmpty()==true){
                 return "failed to update user details";
             }
            foundUser.setUserEmail(newHomeUser.getUserEmail());
            foundUser.setUserLocationTopic(newHomeUser.getUserLocationTopic());
            foundUser.setUserName(newHomeUser.getUserName());
            foundUser.setUserPassword(encryptPassword(newHomeUser.getUserPassword()));
            final HomeUser updatedHomeUser= usersRepository.save(foundUser);
            return updatedHomeUser.getUserName()+" User Updated";
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