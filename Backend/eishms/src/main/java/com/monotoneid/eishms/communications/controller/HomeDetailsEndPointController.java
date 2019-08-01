package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.HomeDetails;
import com.monotoneid.eishms.services.filemanagement.HomeDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class HomeDetailsEndPointController {

    @Autowired
    HomeDetailsService homeDetailsService;

    @PostMapping(value= "/homedetails")
    public ResponseEntity<Object> addHomeDetails(@RequestBody HomeDetails homeDetails) {
        return homeDetailsService.addHomeDetails(homeDetails);
    }

    @PutMapping(value = "/homedetails")
    public ResponseEntity<Object> updateHomeDetails(@RequestBody HomeDetails homeDetails) {
        return homeDetailsService.updateHomeDetails(homeDetails);
    }

    @GetMapping(value = "/homedetails")
    public ResponseEntity<Object> retrieveHomeDetails() {
        return homeDetailsService.retrieveHomeDetails();
    }
}