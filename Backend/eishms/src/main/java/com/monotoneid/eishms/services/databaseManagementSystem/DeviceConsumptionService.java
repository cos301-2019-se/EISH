package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;
import com.monotoneid.eishms.dataPersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceConsumptionService{
    @Autowired
    private Devices deviceRepository;

    @Autowired
    private DeviceConsumptions deviceConsumptionRepository;

    @Autowired
    private Devices devicesRepository;

    public void addDeviceConsumption(long referenceDeviceId,  Timestamp newDeviceConsumptionTimestamp, String newDeviceConsumptionState, float newDeviceConsumption){
        try{
            Device foundDevice = devicesRepository.findById(referenceDeviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Device does not exist"));
            DeviceConsumption newDeviceToAdd = new DeviceConsumption(newDeviceConsumption, foundDevice, newDeviceConsumptionTimestamp, newDeviceConsumptionState);
            deviceConsumptionRepository.save(newDeviceToAdd);        
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
        }
   }
        
    public List<DeviceConsumption> retrieveAllConsumptions(){
        return deviceConsumptionRepository.findAll();
    }
public List<DeviceConsumption> retrieveDeviceConsumptionById(long deviceId){
        try {
            Device foundDevice = deviceRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
            return foundDevice.getDeviceConsumption();
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return null;
        }
    }
}
