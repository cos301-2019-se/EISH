package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.datapersistence.models.Device;
import com.monotoneid.eishms.datapersistence.models.DeviceConsumption;
import com.monotoneid.eishms.datapersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.datapersistence.repositories.Devices;
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
    
    public List<DeviceConsumption> retrieveBetweenInterval(long deviceId,String interval) {
        try{
            
            devicesRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("device does not exist!"));
              if(interval.matches("last10minutes")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionLastTenMinutes(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;

              }else if(interval.matches("lasthour")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionLastHour(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;

              }else if(interval.matches("lastday")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionLastOneDay(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;

              }else if(interval.matches("lastweek")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionLastOneWeek(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else if(interval.matches("lastmonth")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionLastOneMonth(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else if(interval.matches("lastyear")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findDeviceConsumptionLastOneYear(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else if(interval.matches("thishour")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionIdForThisHour(deviceId)
                .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else if(interval.matches("thisday")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionIdForThisDay(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else if(interval.matches("thisweek")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionIdForThisWeek(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else if(interval.matches("thismonth")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionIdForThisMonth(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              } else if(interval.matches("thisyear")){
                List<DeviceConsumption> foundDeviceConsumptionList =  deviceConsumptionRepository.findByDeviceConsumptionIdForThisYear(deviceId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundDeviceConsumptionList;
              }else{
                  throw null;
              }
        } catch(Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }
}
