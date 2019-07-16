package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.configuration.JwtTokenUtil;
import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.models.UserType;
import com.monotoneid.eishms.dataPersistence.repositories.HomeKeys;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
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
import org.springframework.security.crypto.password.PasswordEncoder;
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
 
    //@Autowired
    //private PasswordEncoder encoder;
 
    @Autowired
    private JwtTokenUtil jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtRequest loginRequest) {
        System.out.println(loginRequest.getUsername()+ " +++***+++ "+loginRequest.getPassword());
        //System.out.println("Encoded password" + encoder.encode(loginRequest.getPassword()));
        myHouseKeys.updateKeys();
        //System.out.println(myHouseKeys.findByKeyName("general").getKeyName() + " : " + myHouseKeys.findByKeyName("general").getUnencryptedKey());
        //System.out.println(myHouseKeys.findByKeyName("general").getKeyName() + " : " + myHouseKeys.findByKeyName("general").getUserkey() + "[encrypted]");

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword() // you might need to encrypt this password for it to match the one in the database
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
//      if guest has expired don't generate token tell the user
        //List<HomeUser> allUsers = userRepository.findAll();
        if (!loginRequest.getUsername().matches("general") && !loginRequest.getUsername().matches("renewal")) {
            HomeUser loginUser = null;
            // for (int i=0; i < allUsers.size(); i++) {
            //     if (allUsers.get(i).getUserName().matches(loginRequest.getUsername())) {
            //         loginUser = allUsers.get(i);
            //         break;
            //     }
            // }

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