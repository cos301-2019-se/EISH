package com.monotoneid.eishms.communications.controller;

import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceConsumptionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 *CLASS DEVICE CONSUMPTION ENDPOINT CONTROLLER. 
 */
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/device")
public class DeviceConsumptionEndPointController {

    @Autowired
    private DeviceConsumptionService deviceConsumptionService;

    /**
    * GET METHOD.
    * Implements retrieveDeviceConsumption endpoint, it calls retrieveDeviceConsumption service
    * @param deviceId represents the devices id
    * @return a the valid DeviceConsumptionList
    */
     
    @GetMapping(value = "/consumption", params = {"deviceId","startTimeStamp","endTimeStamp"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<DeviceConsumption> retrieveDeviceConsumptionCases(
        @RequestParam(value = "deviceId", required = true) long deviceId,
        @RequestParam(value = "startTimeStamp", required = true) String startTimeStamp,
        @RequestParam(value = "endTimeStamp", required = true) String endTimeStamp) {
        return deviceConsumptionService
            .retrieveAllDeviceCases(deviceId, startTimeStamp, endTimeStamp);
    }

    @GetMapping(value = "/consumption", params = {"deviceId","interval"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public List<DeviceConsumption> retrieveDeviceConsumptionBetweenInterval(
        @RequestParam(value = "deviceId", required = true) long deviceId,
        @RequestParam(value = "interval", required = true) String interval) {
        return deviceConsumptionService.retrieveBetweenInterval(deviceId, interval);
    }

}