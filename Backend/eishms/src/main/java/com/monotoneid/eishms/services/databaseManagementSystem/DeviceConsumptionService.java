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
     
    public List<DeviceConsumption> retrieveAllDeviceCases(long deviceId, String startTimeStamp, String endTimeStamp){
        try{
            devicesRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("device does not exist!"));
              
            String removeQuotesStartTimeStamp =startTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedStartTimestamp = Timestamp.valueOf(removeQuotesStartTimeStamp);

            String removeQuotesEndTimeStamp =endTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedEndTimestamp = Timestamp.valueOf(removeQuotesEndTimeStamp);

            List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionIdAndDeviceConsumptionTimestampBetween(deviceId,convertedStartTimestamp,convertedEndTimestamp)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }  
    
    public List<DeviceConsumption> retrieveBetweenInterval(long deviceId,String interval){
        try{
            devicesRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("device does not exist!"));
              System.out.println(deviceId);
              System.out.println(interval);
                //String convertedInterval  =interval.replaceAll("^\"|\"$","");
                String convertedInterval = "'" + interval +"'"; 
                System.out.println(convertedInterval);
            List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionBetweenInterval(deviceId, convertedInterval)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }
}
