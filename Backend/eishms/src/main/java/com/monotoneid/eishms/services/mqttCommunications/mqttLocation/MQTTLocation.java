package com.monotoneid.eishms.services.mqttcommunications.mqttlocation;

import java.sql.Timestamp;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.HomeDetails;
import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.datapersistence.repositories.UserPresences;
import com.monotoneid.eishms.services.databasemanagementsystem.UserPresenceService;
import com.monotoneid.eishms.services.filemanagement.HomeDetailsService;
import com.monotoneid.eishms.services.mqttcommunications.mqttlocation.MqttLocationManager;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;


public class MqttLocation {
    private String serverUrl = "tcp://127.0.0.1:1883";
    
    @Autowired
    private HomeDetailsService homeDetailsService;

    private String mqttUserName = "eishms";
    private String mqttPassword = "eishms";
    private HomeUser homeUser;
    private MqttLocationManager locationManager;
    private String asyncClientId;
    private IMqttAsyncClient asyncClient;
    private boolean receivedLocation; 
    private int[] qoss;
    private String[] subscriptionTopics;
    private CountDownLatch countDownLatch;
    private boolean isPresent;

    @Autowired 
    private UserPresenceService userPresenceService; 

    public MqttLocation(HomeUser user, MqttLocationManager locationManager) {
        this.homeUser = user;
        this.locationManager = locationManager;
        this.asyncClientId = UUID.randomUUID().toString();
        this.receivedLocation = false;
        this.isPresent = false;
        //this.countDownLatch = new CountDownLatch(1);

        try {
            this.asyncClient = new MqttAsyncClient(serverUrl, asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttUserName);
            options.setPassword(mqttPassword.toCharArray());
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);
            this.asyncClient.connect(options).waitForCompletion();

            this.asyncClient.subscribe(subscriptionTopics, qoss).waitForCompletion();
            this.asyncClient.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String strMessage;
                    if (topic.matches("owntracks/eishms/" + homeUser.getUserLocationTopic() + "/event")) {
                        System.out.println("About to handle location event");
                        strMessage = new String(message.getPayload().toString());
                        System.out.println("Payload: " + strMessage);
                        handleLocationEvent(strMessage);
                    }
                    if (topic.matches("owntracks/eishms/" + homeUser.getUserLocationTopic())) {
                        System.out.println("About to handle location");
                        strMessage = new String(message.getPayload().toString());
                        System.out.println("Payload: " + strMessage);
                        handleLocationEvent(strMessage);
                        if (countDownLatch.getCount() > 0)
                            countDownLatch.countDown();
                    }
                }
            
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    
                }
            
                @Override
                public void connectionLost(Throwable cause) {
                    
                }
            });
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    private void initializeTopics() {
        // topics to subscribe to...
    }


    private void handleLocationEvent(String message) {
        JsonObject jsonContent = new JsonParser().parse(message)
                                                        .getAsJsonObject();
        String deviceHomeName = "";
        String regionName = "";
        boolean inRegion = false;
        String userName = "";
        long timeOfEvent = 0;
        double longitude = 0;
        double latitude = 0;
        String homeName = "";
        double homeLongitude = 0;
        double homeLatitude = 0;
        double radius = 0; //In kilometers
        double distanceFromHome = 0;                                                        

        if (jsonContent.has("desc")) {
            deviceHomeName = jsonContent.get("desc").getAsString();
        }
        
        if (jsonContent.has("event")) {
            String locationEvent = "";
            locationEvent = jsonContent.get("event").getAsString();
            inRegion = locationEvent.matches("enter");
        }

        if (jsonContent.has("lat")) {
            longitude = jsonContent.get("lat").getAsDouble();
        }

        if (jsonContent.has("lon")) {
            latitude = jsonContent.get("lon").getAsDouble();
        }

        // if (jsonContent.has("tid")) {
        //     userName = jsonContent.get("tid").getAsString();
        // }

        if (jsonContent.has("tst")) {
            timeOfEvent = jsonContent.get("tst").getAsLong();
        }

        try {
            HomeDetails homeDetails = homeDetailsService.readFromFile();
            homeName = homeDetails.getHomeName();
            homeLatitude = homeDetails.getHomeLatitude();
            homeLongitude = homeDetails.getHomeLongitude();
            radius = homeDetails.getHomeRadius();
        } catch(Exception e) {

        }

        Timestamp presencTimestamp;
        if ((longitude != 0) && (latitude != 0) 
            && (homeName.matches(deviceHomeName)) 
            && homeUser.getUserLocationTopic().matches(userName)) {
            distanceFromHome = distance(homeLongitude, homeLatitude, longitude, latitude);
            if (distanceFromHome <= radius) {
                presencTimestamp = new Timestamp(timeOfEvent);
                this.isPresent = true;
                userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
            }
        }

        if (regionName.matches("") && jsonContent.has("tst")) {
            if (userName.matches(homeUser.getUserLocationTopic())) {
                presencTimestamp = new Timestamp(timeOfEvent);
                this.isPresent = false;
                userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
            }
        }

    }

    private void handleLocationMessage(String message) {
        JsonObject jsonContent = new JsonParser().parse(message)
                                                        .getAsJsonObject();
        JsonArray regions = null;
        String regionName = "";
        boolean inRegion = false;
        String userName = "";
        long timeOfEvent = 0;
        double longitude = 0;
        double latitude = 0;
        String homeName = "";
        double homeLongitude = 0;
        double homeLatitude = 0;
        double radius = 0; //In kilometers
        double distanceFromHome = 0;

        if (jsonContent.has("inregions")) {
            regions = jsonContent.getAsJsonArray("inregions");
        }
        
        if (jsonContent.has("lat")) {
            longitude = jsonContent.get("lat").getAsDouble();
        }

        if (jsonContent.has("lon")) {
            latitude = jsonContent.get("lon").getAsDouble();
        }

        if (jsonContent.has("tid")) {
            userName = jsonContent.get("tid").getAsString();
        }

        if (jsonContent.has("tst")) {
            timeOfEvent = jsonContent.get("tst").getAsLong();
        }

        //Get home details!!!
        try {
            HomeDetails homeDetails = homeDetailsService.readFromFile();
            homeName = homeDetails.getHomeName();
            homeLatitude = homeDetails.getHomeLatitude();
            homeLongitude = homeDetails.getHomeLongitude();
            radius = homeDetails.getHomeRadius();
        } catch(Exception e) {

        }
        
        String tmpRegionName = "";
        for (int i = 0; i < regions.size(); i++) {
            tmpRegionName = regions.get(i).getAsString();
            if (tmpRegionName.matches(homeName)) {
                regionName = tmpRegionName;
                inRegion = true; 
            }
        }

        Timestamp presencTimestamp;
        if ((longitude != 0) && (latitude != 0) 
            && (homeName.matches(regionName)) 
            && homeUser.getUserLocationTopic().matches(userName)) {
            distanceFromHome = distance(homeLongitude, homeLatitude, longitude, latitude);
            if (distanceFromHome <= radius) {
                presencTimestamp = new Timestamp(timeOfEvent);
                this.isPresent = true;
                userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
            }
        }

        if (regionName.matches("") && jsonContent.has("tst")) {
            if (userName.matches(homeUser.getUserLocationTopic())) {
                presencTimestamp = new Timestamp(timeOfEvent);
                this.isPresent = false;
                userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
            }
        }
    }

    private double distance(double homeLongitude, double homeLatitude, 
                            double yourLongitude, double yourLatitude) {
        double theta = homeLongitude - yourLongitude;
        double dist = Math.sin(degToRad(homeLatitude)) 
                        * Math.sin(degToRad(yourLatitude)) 
                        + Math.cos(degToRad(homeLatitude)) 
                        * Math.cos(degToRad(yourLatitude)) 
                        * Math.cos(degToRad(theta));
        dist = Math.acos(dist);
        dist = radToDeg(dist);
        dist = dist * 60 * 1.1515;

        return dist * 1.609344; // Returns distances in kilometers.
    }

    private double radToDeg(double radians) {
        return (radians * 180.0 / Math.PI);
    }

    private double degToRad(double degrees) {
        return (degrees * Math.PI / 180.0);
    }

    public boolean getCurrentPresence() {
        try {
            this.countDownLatch = new CountDownLatch(1);
            this.asyncClient.publish("", "".getBytes(), 2, false).waitForCompletion();  
            this.countDownLatch.await();
            //this.countDownLatch.await(timeout, unit);     
        } catch (Exception e) {}

        return this.isPresent;
    }

} 