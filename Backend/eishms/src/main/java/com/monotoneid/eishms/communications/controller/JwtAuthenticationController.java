package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.configuration.JwtTokenUtil;
import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.models.UserType;
import com.monotoneid.eishms.datapersistence.repositories.HomeKeys;
import com.monotoneid.eishms.datapersistence.repositories.Users;
import com.monotoneid.eishms.messages.JwtRequest;
import com.monotoneid.eishms.messages.JwtResponse;

import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS JWT AUTHENTICATION CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
    @Autowired
    private AuthenticationManager authenticationManager;
 
    @Autowired
    private Users userRepository;

    @Autowired
    private HomeKeys myHouseKeys;
  
    @Autowired
    private JwtTokenUtil jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtRequest loginRequest) {
        myHouseKeys.updateKeys();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword())
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
        if (!loginRequest.getUsername().matches("general") && !loginRequest.getUsername().matches("renewal")) {
            HomeUser loginUser = null;
            loginUser = userRepository.findByHomeUserName(loginRequest.getUsername()).get();
    
            //compare current date with expiry date
            if (loginUser.getUserType() == UserType.ROLE_GUEST && loginUser.getUserExpiryDate().before(new Date())) {
                return ResponseEntity.status(HttpStatus.valueOf(410)).body("Your credentials have expired.");
            }
        }
       
        String jwt = jwtProvider.generateJwtToken(authentication);
        System.out.println("User is authorized!");
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}