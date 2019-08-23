package com.monotoneid.eishms.services.mqttcommunications.mqttlocation;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.datapersistence.models.HomeDetails;
import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.models.NotificationPriorityType;
import com.monotoneid.eishms.datapersistence.models.UserType;
import com.monotoneid.eishms.datapersistence.repositories.Blacklist;
import com.monotoneid.eishms.datapersistence.repositories.Users;
import com.monotoneid.eishms.services.databasemanagementsystem.NotificationService;
import com.monotoneid.eishms.services.databasemanagementsystem.UserPresenceService;
import com.monotoneid.eishms.services.filemanagement.HomeDetailsService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import net.minidev.json.JSONObject;

@Service
public class MqttLocationManager {
    
    @Autowired
    protected SimpMessagingTemplate simpMessagingTemplate;

    @Autowired 
    protected UserPresenceService userPresenceService; 

    @Autowired 
    protected NotificationService notificationService;

    @Autowired
    protected HomeDetailsService homeDetailsService;

    @Autowired
    protected Blacklist blacklist;
    
    private ArrayList<MqttLocation> userLocations;

    List<HomeUser> homeUsers;

    public MqttLocationManager(Users users) {
        homeUsers = null;
        homeUsers = users.findAll();
        userLocations = new ArrayList<MqttLocation>();
        System.out.println("Adding users ........");
        for (int i=0; i < homeUsers.size(); i++) {
            addUser(homeUsers.get(i));
        }
    }

    public void addUser(HomeUser newUser) {
        try {
            MqttLocation newMqttLocation = new MqttLocation(newUser, this);
            if (newMqttLocation != null) {
                userLocations.add(newMqttLocation);
            }
        } catch (Exception e) {
            System.out.println("Error adding user");
            //throw e;
        }
    }

    public boolean discoverLocation(String homeName) {
        HomeUser admin = null;
        HomeDetails homeDetails = null;
        //Look for admin
        for (int i=0; i < homeUsers.size(); i++) {
            if (homeUsers.get(i).getUserType() == UserType.ROLE_ADMIN) {
                admin = homeUsers.get(i);
                break;
            }
        }

        if (admin == null) {
            return false;
        }

        //Look for his/her mqttLocation object
        //query mqttLocation for home location details
        for (int i=0; i < userLocations.size(); i++) {
            if (userLocations.get(i).getUsername().matches(admin.getUserName())) {
                homeDetails = userLocations.get(i).getHomeDetails(homeName);
                break;
            }
        }
        
        if (homeDetails == null) {
            return false;
        }

        //update home location file with new details
        try {
            homeDetailsService.writeToFile(homeDetails);
            JSONObject notificationObject = new JSONObject();
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            notificationObject.put("priority", NotificationPriorityType.PRIORITY_MINOR.toString());
            notificationObject.put("message", "Updated new home details.");
            notificationService.addNotification("Updated new home details.", NotificationPriorityType.PRIORITY_MINOR.toString(), currentTimestamp);
            if (simpMessagingTemplate != null) {
                simpMessagingTemplate.convertAndSend("/notification", notificationObject);
            } 
    
        } catch(IOException ioe) {
            JSONObject notificationObject = new JSONObject();
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            notificationObject.put("priority", NotificationPriorityType.PRIORITY_CRITICAL.toString());
            notificationObject.put("message", "Could not update new home details.");
            notificationService.addNotification("Could not update new home details.", NotificationPriorityType.PRIORITY_CRITICAL.toString(), currentTimestamp);
            if (simpMessagingTemplate != null) {
                simpMessagingTemplate.convertAndSend("/notification/", notificationObject);
            }
        }
        
        //return true if successful
        return true;
    }

    public boolean checkUserPresence(String username) {
        for (int i=0; i < userLocations.size(); i++) {
            if (userLocations.get(i).getUsername().matches(username)) {
                return userLocations.get(i).getCurrentPresence();
            }
        }

        return false;
    }
}
