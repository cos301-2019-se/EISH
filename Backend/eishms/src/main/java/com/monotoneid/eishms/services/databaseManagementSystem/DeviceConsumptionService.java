package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.models.DeviceConsumption;
import com.monotoneid.eishms.dataPersistence.models.DeviceConsumptionId;
import com.monotoneid.eishms.dataPersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.dataPersistence.repositories.Devices;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceConsumptionService{
   
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
                Device foundDevice = devicesRepository.findById(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
                return foundDevice.getDeviceConsumption();
            } catch(Exception e) {
                System.out.println("Error: " + e.getMessage() + "!");
                return null;
            }
    }
   /* 
    public List<DeviceConsumption> retriveConsumptionByDeviceId(long deviceId){
        try{
            List<DeviceConsumption> foundDeviceConsumptionList = deviceConsumptionRepository.findAllByDeviceId(deviceId)
                    .orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
                return foundDeviceConsumptionList;
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return null;
        }
    }
    */ 
    public List<DeviceConsumption> retrieveFilteredDeviceConsumptionByDeviceIdAndTimeStamps(long deviceId,Timestamp startTimeStamp, Timestamp endTimeStamp){
        // try{
        //     List<DeviceConsumption> foundDeviceConsumptionList = deviceConsumptionRepository.findFilteredDeviceConsumption(deviceId, startTimeStamp, endTimeStamp)
        //             .orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
        //     return foundDeviceConsumptionList;   

        // } catch(Exception e) {
        //     System.out.println("Error: " + e.getMessage() + "!");
             return null;
        // }
        
        
    }

    public List<DeviceConsumption> retrieveAllDeviceCases(long deviceId, String startTimeStamp, String endTimeStamp){
        Timestamp convertedStartTimestamp = null;
        Timestamp convertedEndTimestamp = null;
        try{
            if(startTimeStamp.isEmpty()==false){
                convertedStartTimestamp = Timestamp.valueOf(startTimeStamp);
            }
            if(endTimeStamp.isEmpty()==false){
                convertedEndTimestamp = Timestamp.valueOf(endTimeStamp);
            }
            if(endTimeStamp.isEmpty()==true){
                convertedEndTimestamp = new Timestamp(System.currentTimeMillis());
            }
            
        List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionBetween(deviceId,convertedStartTimestamp,convertedEndTimestamp);
                  //  .orElseThrow(() -> new ResourceNotFoundException("Device does not exist!"));
            return foundDeviceConsumptionList;
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            return null;
        }
    }
    
    
}
