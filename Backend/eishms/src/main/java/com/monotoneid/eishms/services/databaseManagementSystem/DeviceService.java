package com.monotoneid.eishms.services.databaseManagementSystem;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.models.DevicePriorityType;
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

    /**
     * Retrieves all the devices in the database
     * @return List of all devices
     */
    public List<Device> retrieveAllDevices(){
        return devicesRepository.findAll();
    }

    /**
     * Retrieves the device with the specified id
     * @param deviceId
     * @return the device
     * @exception ResourceNotFound
     */
    public ResponseEntity<Device> retrieveDevice(long deviceId){
        try {
            Device foundDevice = devicesRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            return new ResponseEntity<>(foundDevice,HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage()+"!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Updates the data of the specified with parsed in data
     * @param newDevice
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
    public ResponseEntity<Object> updateDevice(Device newDevice) {
        try {
            if(newDevice == null)
                throw null;
            else {
                if(newDevice.getDeviceName().isEmpty() == true
                || newDevice.getDevicePriority() != DevicePriorityType.PRIORITY_ALWAYSON
                && newDevice.getDevicePriority() != DevicePriorityType.PRIORITY_MUSTHAVE
                && newDevice.getDevicePriority() != DevicePriorityType.PRIORITY_NEUTRAL
                && newDevice.getDevicePriority() != DevicePriorityType.PRIORITY_NICETOHAVE
                || newDevice.getDeviceTopic().isEmpty() == true
                || newDevice.getDeviceType() == null)
                    throw null;
                Device founDevice = devicesRepository.findById(newDevice.getDeviceId()).orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
                founDevice.setDeviceName(newDevice.getDeviceName());
                founDevice.setDevicePriorityType(newDevice.getDevicePriority());
                founDevice.setDeviceTopic(newDevice.getDeviceTopic());
                founDevice.setDeviceType(newDevice.getDeviceType());
                devicesRepository.save(founDevice);
                JSONObject responseObject = new JSONObject();
                responseObject.put("message","Devices Updated!");
                return new ResponseEntity<>(responseObject,HttpStatus.OK);
            }
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            if(e.getCause() == null)
                return new ResponseEntity<>("Error: Failed to update device!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to update device!",HttpStatus.NOT_FOUND);
        }
    }

    /**
     * Removes the specified device 
     * @param device
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
    public ResponseEntity<Object> removeDevice(Device device) {
        try {
            if(device == null)
                throw null;
            devicesRepository.findById(device.getDeviceId()).orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            devicesRepository.deleteById(device.getDeviceId());
            JSONObject responseObject = new JSONObject();
            responseObject.put("message","Success: Device has been deleted!");
            return new ResponseEntity<>(responseObject,HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: Input is " + e.getMessage() + "!");
            if(e.getMessage() == null)
                return new ResponseEntity<>("Error: Failed to delete device!",HttpStatus.PRECONDITION_FAILED);
            else
                return new ResponseEntity<>("Error: Failed to delete device!",HttpStatus.NOT_FOUND);
        }
    }
}