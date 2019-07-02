package com.monotoneid.eishms.communications.controller;

import java.util.List;

import javax.validation.Valid;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

   /**
    * GET METHOD
    * Implements retrieveDevice endpoint, that calls the retrieveDevice service
    * @param deviceId
    * @return a the valid Device
    */
    @GetMapping(value = "/device",params = {"deviceId"})
    @PreAuthorize("hasRole('RESIDENT') or hasRole('ADMIN') or hasRole('GUEST')")
    public ResponseEntity<Device> retriveDevice(@Valid @RequestParam(value = "deviceId") long deviceId){
       return deviceService.retrieveDevice(deviceId);
    }

    /**
     * PUT METHOD
     * Implements updateDevice endpoint, that calls the updateDevice service
     * @param newDevice
     * @return object message
     */
    @PutMapping("/device")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Object> updateDevice(@Valid @RequestBody Device newDevice){
       return deviceService.updateDevice(newDevice);
    }

    /**
    * DELETE METHOD
    * Implements removeDevice endpoint, that calls the removeDevice service
    * @param deviceToDelete
    * @return an object with deleted device
    */
   @DeleteMapping("/device")
   @PreAuthorize("hasRole('ADMIN')")
   public ResponseEntity<Object> removeDevice(@Valid @RequestBody Device deviceToDelete){
      return deviceService.removeDevice(deviceToDelete);
   }
   


}