package com.monotoneid.eishms.communications.controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EndPointController{

   @GetMapping("/user/presence")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String getUserPresence(){
      return "all can get presence";
   } 

   @PostMapping("/user")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String addUser(){
      return "all can add user";
   }
   
   @DeleteMapping("/user")
   @PreAuthorize("hasRole('ADMIN')")
   public String removeUser(){
      return "only admin can remove user";
   }

   @GetMapping("/users")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public String retriveAllUsers(){
      return "all can get all users";
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