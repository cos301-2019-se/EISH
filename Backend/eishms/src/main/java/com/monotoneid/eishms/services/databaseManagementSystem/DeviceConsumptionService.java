package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;
import com.monotoneid.eishms.dataPersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceConsumptionService{
    @Autowired
    private DeviceConsumptions deviceConsumptionRepository;

    public List<DeviceConsumption> retrieveAllConsumptions(){
        return deviceConsumptionRepository.findAll();
    }
    public List<DeviceConsumption> retrieveDeviceConsumptionById(long DeviceToFind){
        List<DeviceConsumption> foundDeviceConsumptionList = null;
        try{
             foundDeviceConsumptionList = deviceConsumptionRepository.getDeviceConsumptionofId(DeviceToFind)
            .orElseThrow(() -> new ResourceNotFoundException("Device Consumption does not exist"));
            return foundDeviceConsumptionList;
        }catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return foundDeviceConsumptionList;
        }
    }

public void  addDeviceConsumptionOfDeviceId(long referenceDeviceId,  Timestamp newDeviceConsumptionTimestamp, String newDeviceConsumptionSate, float newdeviceConsumption){
    deviceConsumptionRepository.insertIntoDeviceConsumption(referenceDeviceId, newDeviceConsumptionTimestamp, newDeviceConsumptionSate, newdeviceConsumption);;
}
}