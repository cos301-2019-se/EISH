package com.monotoneid.eishms.communications.controller;

<<<<<<< HEAD
import javax.validation.Valid;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.beans.factory.annotation.Autowired;
=======
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
>>>>>>> 0a8abdace14cbf252c1553eef8d194b2265a55ba
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
<<<<<<< HEAD
=======
import org.springframework.web.bind.annotation.RequestMapping;
>>>>>>> 0a8abdace14cbf252c1553eef8d194b2265a55ba
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EndPointController{
   @Autowired
   private UserService userService;

   @GetMapping("/users")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> retriveAllUsers(){
      return new ResponseEntity<>(userService.retrieveAllUsers(),HttpStatus.OK);
   }

   @GetMapping("/user/{userId}")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<HomeUser> retriveUser(@PathVariable(value = "userId") Long userId){
      return new ResponseEntity<>(userService.retrieveUser(userId),HttpStatus.OK);
   }

   @DeleteMapping("/user/{userId}")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> removeUser(@PathVariable(value = "userId") Long userId){
      String result = userService.removeUser(userId);
      return new ResponseEntity<>(result,HttpStatus.OK);      
   }

   @Autowired
   private UserService userService;

   public EndPointController() {}

   @GetMapping("/user/presence")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String getUserPresence(){
      return "all can get presence";
   }
    
   


   /**
    * POST METHOD
    * Implements the addUser endpoint, that calls the addUser service
    * @return the status message
    */
   @PostMapping("/user")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
<<<<<<< HEAD
   public ResponseEntity<Object> addUser(@Valid @RequestBody HomeUser newHomeUser){
      String result =userService.addUser(newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }


  

   @PatchMapping("/user/{userId}/useremail")
=======
   public ResponseEntity<Object> addUser(@RequestBody HomeUser newHomeUser){
      String result = userService.addUser(newHomeUser);
      return new ResponseEntity<>(result,HtttpStatus.OK);
   }
   
   @DeleteMapping("/user")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN')")
   public String removeUser(){
      return "only admin can remove user";
   }

   /**
    * GET METHOD
    * Implements the retrieveAllUsers endpoint, that call the retrieveAllUsers service
    * @return a JSON array of with all users
    */
   @GetMapping("/users")
   @CrossOrigin(origins = "http://localhost:4200")
>>>>>>> 0a8abdace14cbf252c1553eef8d194b2265a55ba
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> updateUserEmail(@PathVariable(value = "userId") Long userId,@Valid @RequestBody HomeUser newHomeUser){
      String result =userService.updateUserEmail(userId,newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }
   @PatchMapping("/user/{userId}/userpassword")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> updateUserPasword(@PathVariable(value = "userId") Long userId,@Valid @RequestBody HomeUser newHomeUser){
      String result =userService.updateUserPassword(userId,newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }
   @PatchMapping("/user/{userId}/userlocationtopic")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> updateUserLocationTopic(@PathVariable(value = "userId") Long userId,@Valid @RequestBody HomeUser newHomeUser){
      String result =userService.updateUserLocationTopic(userId,newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }
   @PatchMapping("/user/{userId}/usertype")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> updateUserType(@PathVariable(value = "userId") Long userId,@Valid @RequestBody HomeUser newHomeUser){
      String result =userService.updateUserType(userId,newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }

<<<<<<< HEAD
   @PutMapping("/user/{userId}")
=======
   @PutMapping("/user")
   @CrossOrigin(origins = "http://localhost:4200")
>>>>>>> 0a8abdace14cbf252c1553eef8d194b2265a55ba
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> updateUser(@PathVariable(value = "userId") Long userId,@Valid @RequestBody HomeUser newHomeUser){
      String result =userService.updateUser(userId,newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }
   
  
   /**
    * update user expirydate
    * @return
    */

<<<<<<< HEAD
   @PatchMapping("/user/{userId}/expiration")
=======
   @PatchMapping("/user/expiration")
   @CrossOrigin(origins = "http://localhost:4200")
>>>>>>> 0a8abdace14cbf252c1553eef8d194b2265a55ba
   @PreAuthorize("hasRole('ADMIN')")
   public String renewUser(){
      return "only admin can change expiration date";
   }
}