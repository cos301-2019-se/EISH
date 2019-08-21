package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.datapersistence.models.HomeGeneration;
import com.monotoneid.eishms.datapersistence.repositories.GeneratorGenerations;
import com.monotoneid.eishms.datapersistence.repositories.HomeGenerations;
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
 *CLASS HOMEGENERATION SERVICE. 
 */
@Service
public class HomeGenerationService {

    @Autowired
    private GeneratorGenerations GeneratorGenerationsRepository;
    @Autowired
    private HomeGenerations HomeGenerationsRepository;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Once per minute everyday.
     */
    @Scheduled(fixedRate = 60000, initialDelay = 60000)
    public void aggregateGeneratorGeneration() {
        try {
                
            List<Float> foundAggergatedGeneratorGeneration = 
                GeneratorGenerationsRepository.findAverageEveryOneMinutes();
                            
            HomeGeneration newHomeGenerationToAdd = null;
            if (foundAggergatedGeneratorGeneration != null) {
                float totalHouseGeneration = 0;
                for (int i = 0; i < foundAggergatedGeneratorGeneration.size(); i++) {
                    totalHouseGeneration += foundAggergatedGeneratorGeneration.get(i);
                } 
                newHomeGenerationToAdd = 
                    new HomeGeneration(new Timestamp(System.currentTimeMillis()), 
                    totalHouseGeneration);
                HomeGenerationsRepository.save(newHomeGenerationToAdd);
            } else {
                newHomeGenerationToAdd = 
                    new HomeGeneration(new Timestamp(System.currentTimeMillis()), null);
                HomeGenerationsRepository.save(newHomeGenerationToAdd);   
            }
            Map<String, String> jsonGeneration = new HashMap<>();
            jsonGeneration.put("HomeGenerationTimeStamp", 
                newHomeGenerationToAdd.getHomeGenerationTimeStamp().toString());
                jsonGeneration.put("HomeGeneration", 
                Float.toString(newHomeGenerationToAdd.getHomeGeneration()));
            simpMessagingTemplate.convertAndSend("/home/consumption", jsonGeneration);

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }

    /**. */
    public List<HomeGeneration> retrieveAllHomeGenerationCases(
        String startTimeStamp, 
        String endTimeStamp) {
        try {
                  
            String removeQuotesStartTimeStamp = startTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedStartTimestamp = Timestamp.valueOf(removeQuotesStartTimeStamp);

            String removeQuotesEndTimeStamp = endTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedEndTimestamp = Timestamp.valueOf(removeQuotesEndTimeStamp);

            List<HomeGeneration> foundHomeGenerationList =  
                HomeGenerationsRepository.findByHomeGenerationTimestampBetween(
                        convertedStartTimestamp,convertedEndTimestamp)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundHomeGenerationList;
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
    public List<HomeGeneration> retrieveBetweenInterval(String interval) {
        try {
            if (interval.matches("last10minutes")) {
                List<HomeGeneration> foundHomeGenerationList =  
                        HomeGenerationsRepository.findHomeGenerationLastTenMinutes()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;

            } else if (interval.matches("lasthour")) {
                List<HomeGeneration> foundHomeGenerationList =
                        HomeGenerationsRepository.findHomeGenerationLastHour()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;

            } else if (interval.matches("lastday")) {
                List<HomeGeneration> foundHomeGenerationList =
                        HomeGenerationsRepository.findHomeGenerationLastOneDay()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;

            } else if (interval.matches("lastweek")) {
                List<HomeGeneration> foundHomeGenerationList =
                        HomeGenerationsRepository.findHomeGenerationLastOneWeek()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("lastmonth")) {
                List<HomeGeneration> foundHomeGenerationList =  
                        HomeGenerationsRepository.findHomeGenerationLastOneMonth()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("lastyear")) {
                List<HomeGeneration> foundHomeGenerationList =
                        HomeGenerationsRepository.findHomeGenerationLastOneYear()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("thishour")) {
                List<HomeGeneration> foundHomeGenerationList = 
                         HomeGenerationsRepository.findHomeGenerationForThisHour()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("thisday")) {
                List<HomeGeneration> foundHomeGenerationList =
                      HomeGenerationsRepository.findHomeGenerationForThisDay()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("thisweek")) {
                List<HomeGeneration> foundHomeGenerationList =  
                        HomeGenerationsRepository.findHomeGenerationForThisWeek()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("thismonth")) {
                List<HomeGeneration> foundHomeGenerationList =  
                        HomeGenerationsRepository.findHomeGenerationForThisMonth()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else if (interval.matches("thisyear")) {
                List<HomeGeneration> foundHomeGenerationList =  
                        HomeGenerationsRepository.findHomeGenerationForThisYear()
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeGenerationList;
            } else {
                throw null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }  

    /**. */
    public ResponseEntity<Object> retrieveWeekHomeGeneration() {
        JSONObject jsonConsumption = new JSONObject();
        jsonConsumption.put("totalWeekHomeGeneration", 
            HomeGenerationsRepository.findTotalHomeGenerationWeek());
        return new ResponseEntity<>(jsonConsumption, HttpStatus.OK);
    }  

    /**. */
    public ResponseEntity<Object> retrieveDayHomeGeneration() {
        JSONObject jsonConsumption = new JSONObject();
        jsonConsumption.put("totalDayHomeGeneration", 
            HomeGenerationsRepository.findTotalHomeGenerationDay());
        return new ResponseEntity<>(jsonConsumption, HttpStatus.OK);
    }

    /**. */
    public ResponseEntity<Object> retrieveMonthHomeGeneration() {
        JSONObject jsonConsumption = new JSONObject();
        jsonConsumption.put("totalMonthHomeGeneration", 
            HomeGenerationsRepository.findTotalHomeGenerationMonth());
        return new ResponseEntity<>(jsonConsumption, HttpStatus.OK);
    }
}