// package com.monotoneid.eishms.services.mqttcommunications.mqttlocation;

import java.util.ArrayList;
import java.util.List;

import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.repositories.Users;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

//@Service
public class MqttLocationManager {
    
    // @Autowired
    // protected Users users;
    
    // private ArrayList<MqttLocation> userLocations;

    // public MqttLocationManager() {
    //     List<HomeUser> homeUsers = users.findAll();
    //     userLocations = new ArrayList<MqttLocation>();
    //     for (HomeUser user : homeUsers) {
    //         addUser(user);
    //     }

    // }

    // public void addUser(HomeUser newUser) {
    //     try {
    //         MqttLocation newMqttLocation = new MqttLocation(newUser, this);
    //         if (newMqttLocation != null) {
    //             userLocations.add(newMqttLocation);
    //         }
    //     } catch (Exception e) {
    //         System.out.println("Error adding user");
    //         throw e;
    //     }
    // }
}
