package com.monotoneid.eishms.services.databaseManagementSystem;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
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

    public List<Device> retrieveAllDevices(){
        return devicesRepository.findAll();
    }
    public ResponseEntity<Device> retrieveDevice(long deviceId){
        try {
            Device foundDevice = devicesRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            return new ResponseEntity<>(foundDevice,HttpStatus.OK);
        } catch(Exception e){
            System.out.println("Error: " + e.getMessage()+"!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
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