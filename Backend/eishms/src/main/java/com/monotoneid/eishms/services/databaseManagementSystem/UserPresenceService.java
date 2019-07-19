package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.models.HomeUserPresence;
import com.monotoneid.eishms.datapersistence.repositories.UserPresences;
import com.monotoneid.eishms.datapersistence.repositories.Users;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

import net.minidev.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service()

public class UserPresenceService {

    @Autowired
    private Users usersRepository;
    @Autowired
    private UserPresences userPresenceRespository;

    /**
     * adds User Presence to Repository.
     * @param referenceUserId
     * @param newHomeUserPresence
     * @param newHomeUserPresenceTimestamp
     */
    public void addUserPresence(long referenceUserId, boolean newHomeUserPresence, 
        Timestamp newHomeUserPresenceTimestamp) {
        try {            
            HomeUser foundHomeUser = usersRepository.findById(referenceUserId)
                    .orElseThrow(() -> new ResourceNotFoundException("User does not exist"));
            HomeUserPresence newhomeUserToAdd = new HomeUserPresence(newHomeUserPresence, foundHomeUser, newHomeUserPresenceTimestamp);
            userPresenceRespository.save(newhomeUserToAdd);        
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
        }
    }

    public List<HomeUserPresence> retrieveAllHomeUserPresenceCases(long userId, String startTimeStamp, String endTimeStamp) {
        try {
            usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("user does not exist!"));
            
            String removeQuotesStartTimeStamp = startTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedStartTimestamp = Timestamp.valueOf(removeQuotesStartTimeStamp);

            String removeQuotesEndTimeStamp = endTimeStamp.replaceAll("^\"|\"$","");
            Timestamp convertedEndTimestamp = Timestamp.valueOf(removeQuotesEndTimeStamp);

            List<HomeUserPresence> foundHomeUserPresenceList = 
                userPresenceRespository.findByHomeUserPresenceIdAndHomeUserPresenceeTimestampBetween(userId,convertedStartTimestamp,convertedEndTimestamp)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
            return foundHomeUserPresenceList;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }

      
    public List<HomeUserPresence> retrieveBetweenInterval(long userId,String interval) {
        try {
            
            usersRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("user does not exist!"));
            if (interval.matches("last10minutes")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findHomeUserPresenceLastTenMinutes(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;

            } else if (interval.matches("lasthour")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findHomeUserPresenceLastHour(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;

            } else if (interval.matches("lastday")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findHomeUserPresenceLastOneDay(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;

            } else if (interval.matches("lastweek")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findHomeUserPresenceLastOneWeek(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("lastmonth")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findHomeUserPresenceLastOneMonth(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("lastyear")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findHomeUserPresenceLastOneYear(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("thishour")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findByHomeUserPresenceForThisHour(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("thisday")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findByHomeUserPresenceForThisDay(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("thisweek")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findByHomeUserPresenceForThisWeek(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("thismonth")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findByHomeUserPresenceForThisMonth(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else if (interval.matches("thisyear")) {
                List<HomeUserPresence> foundHomeUserPresenceList =  userPresenceRespository.findByHomeUserPresenceForThisYear(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("List does not exist!"));
                return foundHomeUserPresenceList;
            } else {
                throw null;
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }
    }

    public ResponseEntity<Object> getCurrentUserPresence(long userId) {
        try {
            usersRepository.findById(userId)
                        .orElseThrow(() -> new ResourceNotFoundException("user does not exist!"));
           
            HomeUserPresence foundHomeUserPresence = 
                userPresenceRespository.findCurrentHomeUserPresence(userId)
                        .orElseThrow(() 
                            -> new ResourceNotFoundException("presence does not exist!"));
            boolean isPresent = foundHomeUserPresence.getHomeUserPresence();
            JSONObject responseObject = new JSONObject();
            //check if the statement works in java
            if (isPresent) {
                responseObject.put("homeUserPresence",  "User is home!");
                return new ResponseEntity<>(responseObject,HttpStatus.OK);
            } else {
                
                responseObject.put("message","User is not at home!");
                return new ResponseEntity<>(responseObject,HttpStatus.OK);
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage() + "!");
            throw e;
        }

    }

}