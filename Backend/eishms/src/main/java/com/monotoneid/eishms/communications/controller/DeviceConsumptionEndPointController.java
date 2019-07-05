package com.monotoneid.eishms.communications.controller;

import java.sql.Timestamp;
import java.util.List;

import javax.validation.Valid;

import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceConsumptionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/device")
public class DeviceConsumptionEndPointController{

    @Autowired
   private DeviceConsumptionService deviceConsumptionService;

    /**
    * GET METHOD
    * Implements retrieveDeviceConsumption endpoint, that calls the retrieveDeviceConsumption service
    * @param deviceId
    * @return a the valid Device
    */
    
    @GetMapping(value = "/consumption",params = {"deviceId"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<DeviceConsumption> retrieveDeviceConsumption(@Valid @RequestParam(value = "deviceId") long deviceId){
           return deviceConsumptionService.retrieveDeviceConsumptionById(deviceId);
    }
    
    
    @GetMapping(value ="/consumption", params={"deviceId","startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<DeviceConsumption> retrieveDeviceConsumptionCases(@RequestParam(value ="deviceId", required = true) long deviceId
    ,@RequestParam(value ="startTimeStamp", required = true) String startTimeStamp,
    @RequestParam(value ="endTimeStamp", required = true) String endTimeStamp){
        
        return deviceConsumptionService.retrieveAllDeviceCases(deviceId, startTimeStamp, endTimeStamp);
    }
    


}