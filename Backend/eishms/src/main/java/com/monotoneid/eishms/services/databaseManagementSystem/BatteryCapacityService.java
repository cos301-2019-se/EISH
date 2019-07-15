package com.monotoneid.eishms.services.databaseManagementSystem;

import java.sql.Timestamp;
import java.util.List;

import com.monotoneid.eishms.dataPersistence.models.BatteryCapacity;
import com.monotoneid.eishms.dataPersistence.repositories.BatteryCapacities;


import com.monotoneid.eishms.exceptions.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class  BatteryCapacityService{

    @Autowired
    private BatteryCapacities batteryCapacitiesRepository;

    


public List<BatteryCapacity> retrieveAllBatteryCapacityCases(String startTimeStamp, String endTimeStamp){
    try{
              
        String removeQuotesStartTimeStamp =startTimeStamp.replaceAll("^\"|\"$","");
        Timestamp convertedStartTimestamp = Timestamp.valueOf(removeQuotesStartTimeStamp);

        String removeQuotesEndTimeStamp =endTimeStamp.replaceAll("^\"|\"$","");
        Timestamp convertedEndTimestamp = Timestamp.valueOf(removeQuotesEndTimeStamp);

        List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findByBatteryCapacityTimestampBetween(convertedStartTimestamp,convertedEndTimestamp)
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
    } catch(Exception e) {
        System.out.println("Error: " + e.getMessage() + "!");
        throw e;
    }
} 

public List<BatteryCapacity> retrieveBetweenInterval(String interval){
    try{
         if(interval.matches("last10minutes")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityLastTenMinutes()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;

          }else if(interval.matches("lasthour")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityLastHour()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;

          }else if(interval.matches("lastday")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityLastOneDay()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;

          }else if(interval.matches("lastweek")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityLastOneWeek()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }else if(interval.matches("lastmonth")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityLastOneMonth()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }else if(interval.matches("lastyear")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityLastOneYear()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }else if(interval.matches("thishour")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityForThisHour()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }
          else if(interval.matches("thisday")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityForThisDay()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }else if(interval.matches("thisweek")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityForThisWeek()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }else if(interval.matches("thismonth")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityForThisHour()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          } else if(interval.matches("thisyear")){
            List<BatteryCapacity> foundBatteryCapacityList =  batteryCapacitiesRepository.findBatteryCapacityForThisYear()
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundBatteryCapacityList;
          }else{
              throw null;
          }
    } catch(Exception e) {
        System.out.println("Error: " + e.getMessage() + "!");
        throw e;
    }
}  
}