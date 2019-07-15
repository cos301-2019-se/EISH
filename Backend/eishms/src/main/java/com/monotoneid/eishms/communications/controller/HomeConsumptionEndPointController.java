package com.monotoneid.eishms.communications.controller;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.HomeConsumption;
import com.monotoneid.eishms.services.databaseManagementSystem.HomeConsumptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/home")
public class HomeConsumptionEndPointController{

    @Autowired
    private HomeConsumptionService homeConsumptionService;

    @GetMapping(value ="/consumption", params={"startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeConsumption> retrieveHomeConsumptionCases(@RequestParam(value ="startTimeStamp", required = true) String startTimeStamp,
    @RequestParam(value ="endTimeStamp", required = true) String endTimeStamp){
        return homeConsumptionService.retrieveAllHomeConsumptionCases(startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/consumption", params={"interval"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<HomeConsumption> retrieveHomeConsumptionBetweenInterval(@RequestParam(value ="interval", required = true) String interval){
        return homeConsumptionService.retrieveBetweenInterval(interval);
    }


}