package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.HomeGeneration;
import com.monotoneid.eishms.services.databaseManagementSystem.HomeGenerationService;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS HOME GENERATION ENDPOINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeGenerationEndPointController {

    @Autowired
    private HomeGenerationService HomeGenerationService;

    @GetMapping(value = "/generation", params = {"startTimeStamp","endTimeStamp"})
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeGeneration> retrieveHomeGenerationCases(
        @RequestParam(value = "startTimeStamp", required = true) String startTimeStamp,
        @RequestParam(value = "endTimeStamp", required = true) String endTimeStamp) {
        return HomeGenerationService.retrieveAllHomeGenerationCases(startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/generation", params = {"interval"})
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeGeneration> retrieveHomeGenerationBetweenInterval(
        @RequestParam(value = "interval", required = true) String interval) {
        return HomeGenerationService.retrieveBetweenInterval(interval);
    }

    @GetMapping(value = "/generation/week")
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> retrieveTotalHomeGenerationWeek() {
        return HomeGenerationService.retrieveWeekHomeGeneration();
    }

    @GetMapping(value = "/generation/day")
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> retrieveTotalHomeGenerationDay() {
        return HomeGenerationService.retrieveDayHomeGeneration();
    }

    @GetMapping(value = "/generation/month")
    //@PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> retrieveTotalHomeGenerationMonth() {
        return HomeGenerationService.retrieveMonthHomeGeneration();
    }

}