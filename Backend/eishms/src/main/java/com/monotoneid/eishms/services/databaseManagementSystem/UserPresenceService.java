package com.monotoneid.eishms.services.databaseManagementSystem;

import com.monotoneid.eishms.dataPersistence.models.HomeUser;
import com.monotoneid.eishms.dataPersistence.models.HomeUserPresence;
import com.monotoneid.eishms.dataPersistence.repositories.UserPresences;
import com.monotoneid.eishms.dataPersistence.repositories.Users;
import com.monotoneid.eishms.exceptions.ResourceNotFoundException;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
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

}