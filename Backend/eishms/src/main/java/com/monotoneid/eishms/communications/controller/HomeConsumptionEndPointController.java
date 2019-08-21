package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.HomeConsumption;
import com.monotoneid.eishms.services.databasemanagementsystem.HomeConsumptionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS HOME CONSUMPTION ENDPOINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeConsumptionEndPointController {

    @Autowired
    private HomeConsumptionService homeConsumptionService;

    @GetMapping(value = "/consumption", params = {"startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeConsumption> retrieveHomeConsumptionCases(
        @RequestParam(value = "startTimeStamp", required = true) String startTimeStamp,
        @RequestParam(value = "endTimeStamp", required = true) String endTimeStamp) {
        return homeConsumptionService.retrieveAllHomeConsumptionCases(startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/consumption", params = {"interval"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeConsumption> retrieveHomeConsumptionBetweenInterval(
        @RequestParam(value = "interval", required = true) String interval) {
        return homeConsumptionService.retrieveBetweenInterval(interval);
    }

    @GetMapping(value = "/consumption/week")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> retrieveTotalHomeConsumptionWeek() {
        return homeConsumptionService.retrieveWeekHomeConsumption();
    }

    @GetMapping(value = "/consumption/day")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> retrieveTotalHomeConsumptionDay() {
        return homeConsumptionService.retrieveDayHomeConsumption();
    }

    @GetMapping(value = "/consumption/month")
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Object> retrieveTotalHomeConsumptionMonth() {
        return homeConsumptionService.retrieveMonthHomeConsumption();
    }

}