package com.monotoneid.eishms.communications.controller;

import javax.validation.Valid;

import com.monotoneid.eishms.configuration.JwtTokenUtil;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.messages.JwtRequest;
import com.monotoneid.eishms.messages.JwtResponse;

import org.springframework.beans.factory.annotation.Autowired;
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

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class JwtAuthenticationController {
    @Autowired
    AuthenticationManager authenticationManager;
 
    @Autowired
    Users userRepository;
 
    @Autowired
    PasswordEncoder encoder;
 
    @Autowired
    JwtTokenUtil jwtProvider;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody JwtRequest loginRequest) {
 
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
                        loginRequest.getPassword() // you might need to encrypt this password for it to match the one in the database
                )
        );
 
        SecurityContextHolder.getContext().setAuthentication(authentication);
 
        String jwt = jwtProvider.generateJwtToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }
}