package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.services.filemanagement.HomeDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class HomeDetailsEndPointController {

    @Autowired
    HomeDetailsService homeDetailsService;

    @GetMapping(value = "/homedetails")
    public ResponseEntity<Object> retrieveHomeDetails() {
        return homeDetailsService.retrieveHomeDetails();
    }
}