package com.monotoneid.eishms.communications.controller;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api")
public class DevicesEndPointController{

    @Autowired
    private DeviceService deviceService;

     /**
   * GET METHOD
   * Implements retrieveAllDevices endpoint, that calls the retrieveAllDevices service
   * @return an object with all devices 
   */
   @GetMapping("/devices")
   @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
   public List<Device> retrieveAllDevices(){
      return deviceService.retrieveAllDevices();
   }

}