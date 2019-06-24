package com.monotoneid.eishms.communications.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class EndPointController{

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
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String retriveAllUsers(){
      return "all can get all users";
   }

   @PutMapping("/user")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String updateUser(){
      return "all can update themselves";
   }

   @PatchMapping("/user/expiration")
   @CrossOrigin(origins = "http://localhost:4200")
   @PreAuthorize("hasRole('ADMIN')")
   public String renewUser(){
      return "only admin can change expiration date";
   }
}