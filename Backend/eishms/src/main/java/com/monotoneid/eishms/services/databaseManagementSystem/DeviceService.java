package com.monotoneid.eishms.services.databaseManagementSystem;

import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_ALWAYSON;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_MUSTHAVE;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_NEUTRAL;
import static com.monotoneid.eishms.dataPersistence.models.DevicePriorityType.PRIORITY_NICETOHAVE;

import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;
import com.monotoneid.eishms.services.mqttCommunications.mqttDevices.MQTTDeviceManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import net.minidev.json.*;

@Service
public class DeviceService {

    @Autowired
    private Devices devicesRepository;

    @Autowired
    private MQTTDeviceManager deviceManager;

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
            Device foundDevice = devicesRepository.findById(deviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            return new ResponseEntity<>(foundDevice, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    
    /**
     * Adds a new device into the database with the specified data
     * @param newDevice
     * @return Object message
     * @exception null
     */
    public ResponseEntity<Object> addDevice(Device device) {
        try {
            if (device == null) {
                throw null;
            }
            if (device.getDeviceName().isEmpty() 
                    || device.getDeviceTopic().isEmpty()
                    || device.getDevicePriority() == null
                    || device.getDeviceStates()==null) {
                throw null;
            }
            if (device.getDevicePriority() != PRIORITY_ALWAYSON && device.getDevicePriority() != PRIORITY_MUSTHAVE
                    && device.getDevicePriority() != PRIORITY_NEUTRAL
                    && device.getDevicePriority() != PRIORITY_NICETOHAVE) {
                    throw null;
                }
                if (device.getDeviceName() != null && device.getDeviceTopic() != null
                && device.getDevicePriority() != null && device.getDeviceStates()!=null) {
           
                    devicesRepository.save(device);
                    deviceManager.addDevice(device); //To add the device to the MQTT list of devices
                    JSONObject responseObject = new JSONObject();
                    responseObject.put("message","Device added!");
                    return new ResponseEntity<>(responseObject,HttpStatus.OK);
                } else
                    throw null;    
        } catch(Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to add device details!",HttpStatus.PRECONDITION_FAILED);
        }
    }

    /**
     * Updates the data of the specified with parsed in data
     * @param newDevice
     * @return Object message
     * @exception null
     * @exception ResourceNotFound
     */
    public ResponseEntity<Object> updateDevice(Device device) {
        try {
            if (device == null) {
                throw null;
            }
            if (device.getDeviceName().isEmpty() 
                    || device.getDeviceTopic().isEmpty()
                    || device.getDevicePriority() == null
                    || device.getDeviceStates()==null) {
                throw null;
            }
            if (device.getDevicePriority() != PRIORITY_ALWAYSON && device.getDevicePriority() != PRIORITY_MUSTHAVE
                    && device.getDevicePriority() != PRIORITY_NEUTRAL
                    && device.getDevicePriority() != PRIORITY_NICETOHAVE) {
                    throw null;
                }
                if (device.getDeviceName() != null && device.getDeviceTopic() != null
                && device.getDevicePriority() != null && device.getDeviceStates()!=null) {
                    Device foundDevice = devicesRepository.findById(device.getDeviceId()).orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
                    foundDevice.setDeviceName(device.getDeviceName());
                    foundDevice.setDevicePriorityType(device.getDevicePriority());
                    foundDevice.setDeviceTopic(device.getDeviceTopic());
                    foundDevice.setDeviceStates(device.getDeviceStates());
                    devicesRepository.save(foundDevice);
                    JSONObject responseObject = new JSONObject();
                    responseObject.put("message","Devices Updated!");
                    return new ResponseEntity<>(responseObject,HttpStatus.OK);
            }else{
                throw null;
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
    public ResponseEntity<Object> removeDevice(long deviceId) {
        try {
            devicesRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            devicesRepository.deleteById(deviceId);
            JSONObject responseObject = new JSONObject();
            responseObject.put("message", "Success: Device has been deleted!");
            return new ResponseEntity<>(responseObject, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println("Error: Input is " + e.getMessage() + "!");
            return new ResponseEntity<>("Error: Failed to delete device!", HttpStatus.NOT_FOUND);
        }
    }       
}