package com.monotoneid.eishms.communications.controller;

import java.util.List;

import javax.validation.Valid;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.HomeKeys;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.crypto.password.PasswordEncoder;


//@CrossOrigin(origins = "http://localhost:4200") 
@RestController
public class EndPointController{
   @Autowired
   PasswordEncoder encoder;

   @Autowired
   private UserService userService;

   @Autowired
   HomeKeys myHouseKeys;

   @GetMapping("keys")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN')") 
   public String getKeys() {
      return myHouseKeys.findByKeyName("general").getKeyName() + " : " + myHouseKeys.findByKeyName("general").getUnencryptedKey();
   }

   /**
    * POST METHOD
    * Implements the addUser endpoint, that calls the addUser service
    * @param newHomeUser
    * @return the status message
    */
    @PostMapping("/user")
    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("hasRole('ADMIN') or hasRole('GENERAL')")
    public ResponseEntity<Object> addUser(@Valid @RequestBody HomeUser newHomeUser){
       return userService.addUser(newHomeUser);
    }

   /**
    * GET METHOD
    * Implements retrieveUser endpoint, that calls the retrieveUser service
    * @return a the valid homeUser
    */
   @GetMapping("/user/{userId}")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<HomeUser> retriveUser(@PathVariable(value = "userId") Long userId){
      return userService.retrieveUser(userId);
   }

   /**
   * GET METHOD
   * Implements retrieveAllUsers endpoint, that calls the retrieveAllUsers service
   * @return an object with all users 
   */
   @GetMapping("/users")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN')")
   public List<HomeUser> retriveAllUsers(){
      return userService.retrieveAllUsers();
   }

   @PutMapping("/user")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> updateUser(@Valid @RequestBody HomeUser newHomeUser){
      return userService.updateUser(newHomeUser);
   }

   /**
    * DELETE METHOD
    * Implements removeUser endpoint, that calls the removeUser service
    * @return an object with all the remaining users
    */
   @DeleteMapping("/user")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> removeUser(@Valid @RequestBody HomeUser homeUser){
      return userService.removeUser(homeUser);
   }

   /**
    * GET METHOD
    * Implements getUserPresence endpoint, that calls the getUserPresence service
    * @return an object with the presence of the user
    */
   @GetMapping("/user/presence/{userId}")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> getUserPresence(@PathVariable(value = "userId") Long userId){
      return userService.getUserPresence(userId);
   }

   @PatchMapping("/user/usertype")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> updateUserType(@Valid @RequestBody HomeUser homeUser){
      return userService.updateUserType(homeUser);
   }
  
   /**
    * update user expirydate
    * @return
    */
   @PatchMapping("/user/expiration")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN') or hasRole('RENEWAL')")
   public ResponseEntity<Object> renewUser(@Valid @RequestBody HomeUser homeUser){
      return userService.renewUser(homeUser);
   }
}
