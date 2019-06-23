package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.services.databaseManagementSystem.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndPointController{
   
   private UserService userService;

   @GetMapping("/user/presence")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String getUserPresence(){
      return "all can get presence";
   } 

   @PostMapping("/user")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> addUser(@RequestBody HomeUser newHomeUser){
      String result =userService.addUser(newHomeUser);
      return new ResponseEntity<>(result,HttpStatus.OK);
   }
   
   @DeleteMapping("/user")
   @PreAuthorize("hasRole('ADMIN')")
   public String removeUser(){
      return "only admin can remove user";
   }

   @GetMapping("/users")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public ResponseEntity<Object> retriveAllUsers(){
      return new ResponseEntity<>(userService.retriveAllUsers(),HttpStatus.OK);
   }

   @PutMapping("/user")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String updateUser(){
      return "all can update themselves";
   }

   @PatchMapping("/user/expiration")
   @PreAuthorize("hasRole('ADMIN')")
   public String renewUser(){
      return "only admin can change expiration date";
   }
}