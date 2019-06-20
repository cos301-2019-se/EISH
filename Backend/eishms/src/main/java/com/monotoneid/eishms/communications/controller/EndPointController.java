package com.monotoneid.eishms.communications.controller;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

public class EndPointController{

   @GetMapping("/user/presence")
   public void getUserPresence(){

   } 

   @PostMapping("/user")
   public void addUser(){

   }
   
   @DeleteMapping("/user")
   public void removeUser(){

   }

   @GetMapping("/users")
   public void retriveAllUsers(){

   }

   @PutMapping("/user")
   public void updateUser(){

   }

   @PatchMapping("/user/expiration")
   public void renewUser(){

   }
}