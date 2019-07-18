package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.datapersistence.models.BatteryCapacity;
import com.monotoneid.eishms.services.databaseManagementSystem.BatteryCapacityService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
/**
 *CLASS BATTERY CAPACITY ENDPOINT CONTROLLER. 
 */
 
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/battery")
public class BatteryCapacityEndPointController {

    @Autowired
    private BatteryCapacityService batteryCapacityService;

    @GetMapping(value = "/capacity", params = {"startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<BatteryCapacity> retrieveBatteryCapacityCases(
        @RequestParam(value = "startTimeStamp", required = true) String startTimeStamp,
        @RequestParam(value = "endTimeStamp", required = true) String endTimeStamp) {
        return batteryCapacityService.retrieveAllBatteryCapacityCases(startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/capacity", params = {"interval"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<BatteryCapacity> retrieveBatteryCapacityBetweenInterval(
        @RequestParam(value = "interval", required = true) String interval) {
        return batteryCapacityService.retrieveBetweenInterval(interval);
    }


}