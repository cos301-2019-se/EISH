package com.monotoneid.eishms.services.databasemanagementsystem;

import com.monotoneid.eishms.datapersistence.models.HomeConsumption;
import com.monotoneid.eishms.datapersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.datapersistence.repositories.HomeConsumptions;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.scheduling.annotation.Scheduled;

import org.springframework.stereotype.Service;
import org.springframework.messaging.simp.*;

import net.minidev.json.JSONObject;

/**
 *CLASS HOMECONSUMPTION SERVVICE. 
 */
@Service
public class HomeConsumptionService {

    @Autowired
    private DeviceConsumptions deviceConsumptionsRepository;
    @Autowired
    private HomeConsumptions homeConsumptionsRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Once per minute everyday.
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void aggregateDeviceConsumption() {
        try {
                
            List<Float> foundAggergatedDeviceConsumption = 
                deviceConsumptionsRepository.findAverageEveryOneMinutes();
                            
            HomeConsumption newHomeConsumptionToAdd = null;
            if (foundAggergatedDeviceConsumption != null) {
                float totalHouseComsumption = 0;
                for (int i = 0; i < foundAggergatedDeviceConsumption.size(); i++) {
                    totalHouseComsumption += foundAggergatedDeviceConsumption.get(i);
                } 
                newHomeConsumptionToAdd = 
                    new HomeConsumption(new Timestamp(System.currentTimeMillis()), 
                        totalHouseComsumption);
                homeConsumptionsRepository.save(newHomeConsumptionToAdd);
            } else {
                newHomeConsumptionToAdd = 
                    new HomeConsumption(new Timestamp(System.currentTimeMillis()), null);
                homeConsumptionsRepository.save(newHomeConsumptionToAdd);   
            }
            Map<String, String> jsonConsumption = new HashMap<>();
            jsonConsumption.put("homeConsumptionTimeStamp", 
                newHomeConsumptionToAdd.getHomeConsumptionTimeStamp().toString());
            jsonConsumption.put("homeConsumption", 
                Float.toString(newHomeConsumptionToAdd.getHomeConsumption()));
            simpMessagingTemplate.convertAndSend("/home/consumption", jsonConsumption);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }

    /**. */
    public List<HomeConsumption> retrieveAllHomeConsumptionCases(
        String startTimeStamp, 
        String endTimeStamp) {
        try {
                  
            String removeQuotesStartTimeStamp = startTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedStartTimestamp = Timestamp.valueOf(removeQuotesStartTimeStamp);

            String removeQuotesEndTimeStamp = endTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedEndTimestamp = Timestamp.valueOf(removeQuotesEndTimeStamp);

            List<HomeConsumption> foundHomeConsumptionList =  
                homeConsumptionsRepository.findByHomeConsumptionTimestampBetween(
                        convertedStartTimestamp,convertedEndTimestamp)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundHomeConsumptionList;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }

    /**
     * .
     * 
     * 
     */
    public List<HomeConsumption> retrieveBetweenInterval(String interval) {
        try {
            if (interval.matches("last10minutes")) {
                List<HomeConsumption> foundHomeConsumptionList =  
                        homeConsumptionsRepository.findHomeConsumptionLastTenMinutes()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;

            } else if (interval.matches("lasthour")) {
                List<HomeConsumption> foundHomeConsumptionList =
                        homeConsumptionsRepository.findHomeConsumptionLastHour()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;

            } else if (interval.matches("lastday")) {
                List<HomeConsumption> foundHomeConsumptionList =
                        homeConsumptionsRepository.findHomeConsumptionLastOneDay()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;

            } else if (interval.matches("lastweek")) {
                List<HomeConsumption> foundHomeConsumptionList =
                        homeConsumptionsRepository.findHomeConsumptionLastOneWeek()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("lastmonth")) {
                List<HomeConsumption> foundHomeConsumptionList =  
                        homeConsumptionsRepository.findHomeConsumptionLastOneMonth()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("lastyear")) {
                List<HomeConsumption> foundHomeConsumptionList =
                        homeConsumptionsRepository.findHomeConsumptionLastOneYear()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("thishour")) {
                List<HomeConsumption> foundHomeConsumptionList = 
                         homeConsumptionsRepository.findHomeConsumptionForThisHour()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("thisday")) {
                List<HomeConsumption> foundHomeConsumptionList =
                      homeConsumptionsRepository.findHomeConsumptionForThisDay()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("thisweek")) {
                List<HomeConsumption> foundHomeConsumptionList =  
                        homeConsumptionsRepository.findHomeConsumptionForThisWeek()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("thismonth")) {
                List<HomeConsumption> foundHomeConsumptionList =  
                        homeConsumptionsRepository.findHomeConsumptionForThisMonth()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else if (interval.matches("thisyear")) {
                List<HomeConsumption> foundHomeConsumptionList =  
                        homeConsumptionsRepository.findHomeConsumptionForThisYear()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeConsumptionList;
            } else {
                throw null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }  

    /**. */
    public ResponseEntity<Object> retrieveWeekHomeConsumption() {
        JSONObject jsonConsumption = new JSONObject();
        jsonConsumption.put("totalWeekHomeConsumption", 
            homeConsumptionsRepository.findTotalHomeConsumptionWeek());
        return new ResponseEntity<>(jsonConsumption, HttpStatus.OK);
    }  

    /**. */
    public ResponseEntity<Object> retrieveDayHomeConsumption() {
        JSONObject jsonConsumption = new JSONObject();
        jsonConsumption.put("totalDayHomeConsumption", 
            homeConsumptionsRepository.findTotalHomeConsumptionDay());
        return new ResponseEntity<>(jsonConsumption, HttpStatus.OK);
    }

    /**. */
    public ResponseEntity<Object> retrieveMonthHomeConsumption() {
        JSONObject jsonConsumption = new JSONObject();
        jsonConsumption.put("totalMonthHomeConsumption", 
            homeConsumptionsRepository.findTotalHomeConsumptionMonth());
        return new ResponseEntity<>(jsonConsumption, HttpStatus.OK);
    }
}