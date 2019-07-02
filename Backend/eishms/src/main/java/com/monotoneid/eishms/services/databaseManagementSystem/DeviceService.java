package com.monotoneid.eishms.services.databaseManagementSystem;

import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_ALWAYSON;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_MUSTHAVE;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_NEUTRAL;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_NICETOHAVE;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.models.DevicePriorityType;
import com.monotoneid.eishms.dataPersistence.models.DeviceType;
import com.monotoneid.eishms.dataPersistence.repositories.DeviceTypes;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class DeviceService {

    @Autowired
    private Devices devicesRepository;

    @Autowired
    private DeviceTypes deviceTypesRepository;

    public List<Device> retrieveAllDevices() {
        return devicesRepository.findAll();
    }

    public ResponseEntity<Device> retrieveDevice(long deviceId) {
        try {
            Device foundDevice = devicesRepository.findById(deviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            return new ResponseEntity<>(foundDevice, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> removeDevice(Device device) {
        try {
            if (device == null)
                throw null;
            devicesRepository.findById(device.getDeviceId())
                    .orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            devicesRepository.deleteById(device.getDeviceId());
            JSONObject responseObject = new JSONObject();
            responseObject.put("message", "Success: Device has been deleted!");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            if (e.getMessage() == null)
                return new ResponseEntity<>("Error: Failed to delete device!", HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to delete device!", HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<Object> addDevice(long referenceDeviceTypeId ,Device device) {
        try {
            if (device == null) {
                throw null;
            }
            if (device.getDeviceName().isEmpty() || device.getDeviceTopic().isEmpty()
                    || device.getDevicePriority() == null) {
                throw null;
            }
            if (device.getDevicePriority() != PRIORITY_ALWAYSON && device.getDevicePriority() != PRIORITY_MUSTHAVE
                        && device.getDevicePriority() != PRIORITY_NEUTRAL
                        && device.getDevicePriority() != PRIORITY_NICETOHAVE) {
                    throw null;
                }
            DeviceType foundDeviceType = deviceTypesRepository.findById(referenceDeviceTypeId)
                    .orElseThrow(() -> new ResourceNotFoundException("DeviceType does not exist"));
            
            device.setDeviceType(foundDeviceType);
            devicesRepository.save(device);
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","Device added!");
            return new ResponseEntity<>(responseObject,HttpStatus.OK);

        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to add device details!",HttpStatus.PRECONDITION_FAILED);
        }
     }
        /*
        try {
            
            
            if (device.getDeviceName() != null && device.getDeviceTopic() != null
                    && device.getDevicePriority() != null) {
               // DevicePriorityType newDevicePriorityType = DevicePriorityType.PRIORITY_ALWAYSON;
                
                
                
            } else{
                throw null;
            }
        
    }
    */
}