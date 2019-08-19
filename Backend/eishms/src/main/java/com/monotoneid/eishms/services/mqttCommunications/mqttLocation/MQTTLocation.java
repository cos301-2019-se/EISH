package com.monotoneid.eishms.services.mqttcommunications.mqttlocation;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.monotoneid.eishms.datapersistence.models.HomeDetails;
import com.monotoneid.eishms.datapersistence.models.HomeUser;
import com.monotoneid.eishms.services.mqttcommunications.mqttlocation.MqttLocationManager;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;


public class MqttLocation {
    private String serverUrl = "tcp://127.0.0.1:1883";

    private String mqttUserName = "eishms";
    private String mqttPassword = "eishms";
    private HomeUser homeUser;
    private MqttLocationManager locationManager;
    private String asyncClientId;
    private IMqttAsyncClient asyncClient;
    private List<HomeDetails> newHomeDetails; 
    private int[] qoss;
    private String[] subscriptionTopics;
    private CountDownLatch locationCountdown;
    private boolean isPresent;
    private CountDownLatch waypointCountdown;

    public MqttLocation(HomeUser user, MqttLocationManager locationManager) {
        this.homeUser = user;
        this.locationManager = locationManager;
        this.asyncClientId = UUID.randomUUID().toString();
        this.newHomeDetails = null;
        this.isPresent = false;
        this.locationCountdown = null;
        this.waypointCountdown = null;
        //this.countDownLatch = new CountDownLatch(1);

        try {
            this.asyncClient = new MqttAsyncClient(serverUrl, asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();
            options.setUserName(mqttUserName);
            options.setPassword(mqttPassword.toCharArray());
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);
            this.asyncClient.connect(options).waitForCompletion();

            initializeTopics();
            this.asyncClient.subscribe(subscriptionTopics, qoss).waitForCompletion();
            this.asyncClient.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String strMessage;
                    if (topic.matches("owntracks/eishms/" + homeUser.getUserLocationTopic() + "/event")) {
                        strMessage = new String(message.getPayload());
                        handleLocationEvent(strMessage);
                    }
                    if (topic.matches("owntracks/eishms/" + homeUser.getUserLocationTopic())) {
                        strMessage = new String(message.getPayload());
                        handleLocationMessage(strMessage);
                        if (locationCountdown != null && locationCountdown.getCount() > 0)
                            locationCountdown.countDown();
                    }
                    if (topic.matches("owntracks/eishms/" + homeUser.getUserLocationTopic() + "/waypoint")) {
                        strMessage = new String(message.getPayload());
                        handleWaypointMessage(strMessage);
                        if (waypointCountdown != null && waypointCountdown.getCount() > 0) {
                            waypointCountdown.countDown();
                        }
                            
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
            System.out.println( "location connection error");
            me.printStackTrace();
        }
    }

    private void initializeTopics() {
        // topics to subscribe to...
        String[] topics = new String[3];
        topics[0] = new String("owntracks/eishms/" + homeUser.getUserLocationTopic() + "/event");
        topics[1] = new String("owntracks/eishms/" + homeUser.getUserLocationTopic());
        topics[2] = new String("owntracks/eishms/" + homeUser.getUserLocationTopic() + "/waypoint");
        subscriptionTopics = topics;

        int[] qos = new int[3];
        qos[0] = 0;
        qos[1] = 0;
        qos[2] = 0;

        qoss = qos;
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

        if (jsonContent.has("tst")) {
            timeOfEvent = jsonContent.get("tst").getAsLong();
        }

        try {
            HomeDetails homeDetails = locationManager.homeDetailsService.readFromFile();
            homeName = homeDetails.getHomeName();
            homeLatitude = homeDetails.getHomeLatitude();
            homeLongitude = homeDetails.getHomeLongitude();
            radius = homeDetails.getHomeRadius();
        } catch(Exception e) {

        }

        Timestamp presencTimestamp;
        if ((longitude != 0) && (latitude != 0) 
            && (homeName.matches(deviceHomeName))) {
            distanceFromHome = distance(homeLongitude, homeLatitude, longitude, latitude);
            if (distanceFromHome <= radius) {
                presencTimestamp = new Timestamp(timeOfEvent);
                this.isPresent = true;
                locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
            }
        }

        if (regionName.matches("") && jsonContent.has("tst")) {
            presencTimestamp = new Timestamp(timeOfEvent);
            this.isPresent = false;
            locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
        }

    }

    private void handleWaypointMessage(String message) {
        JsonObject jsonContent = new JsonParser().parse(message)
                                                        .getAsJsonObject();
        
        JsonArray waypoints = null;  
        newHomeDetails = new ArrayList<HomeDetails>();                                                      
        if (jsonContent.has("waypoints")) {
            JsonObject currWaypoint;
            HomeDetails currHomeDetails;
            waypoints = jsonContent.getAsJsonArray("waypoints");
            for (int i=0; i < waypoints.size(); i++) {
                currWaypoint = waypoints.get(i).getAsJsonObject();
                currHomeDetails = new HomeDetails();
                if (currWaypoint.has("desc")) { //If it has description it has all of them
                    currHomeDetails.setHomeName(currWaypoint.get("desc").getAsString());
                    currHomeDetails.setHomeLatitude(currWaypoint.get("lat").getAsDouble());
                    currHomeDetails.setHomeLongitude(currWaypoint.get("lon").getAsDouble());
                    currHomeDetails.setHomeRadius(currWaypoint.get("rad").getAsInt());
                    currHomeDetails.setHomeAltitude(124);
                    currHomeDetails.setHomeLocation("Pretoria");
                    newHomeDetails.add(currHomeDetails);
                    System.out.println("Location Added it to the list");
                }
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

        System.out.println("handleLocationMessage() is called ....????....(()))____+++ ");

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
            HomeDetails homeDetails = locationManager.homeDetailsService.readFromFile();
            homeName = homeDetails.getHomeName();
            System.out.println("Our House is named " + homeName + "__++++___++_)(***(");
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
            && (homeName.matches(regionName)) ) {

            System.out.println("$$$$$$$$$$$$$$ First if statement is Passed $$$$$$$$$$$$$$$");    
            distanceFromHome = distance(homeLongitude, homeLatitude, longitude, latitude);
            if (distanceFromHome <= radius) {
                presencTimestamp = new Timestamp(timeOfEvent);
                this.isPresent = true;
                locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
                System.out.println("****()_+_)*in the house Saved to DATABASE******&^%$%^&&***");
            } else {
                System.out.println("%%%%%%%%% Not the same radius %%%%%%%%%%%%%%");
            }
        } else {
            System.out.println("^^^^^^^^^^^^ First if statement is Failed ^^^^^^^^^^^^^^^^^^");    
        }

        if (regionName.matches("") && jsonContent.has("tst")) {
            System.out.println("{{{{{{{{{{............. Second if statement is Passed ..............}}}}}}}}");    
            presencTimestamp = new Timestamp(timeOfEvent);
            this.isPresent = false;
            locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
            System.out.println("****()_+_)*NOt in the house Saved to DATABASE******&^%$%^&&***");
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
            this.locationCountdown = new CountDownLatch(1);
            this.asyncClient.publish("owntracks/eishms/" 
                                    + homeUser.getUserLocationTopic() 
                                    + "/cmd", "{\"_type\":\"cmd\",\"action\":\"reportLocation\"}".getBytes()
                                    , 2, false).waitForCompletion();  
            //this.locationCountdown.await();
            this.locationCountdown.await(10, TimeUnit.SECONDS);
        } catch (Exception e) {}

        return this.isPresent;
    }

    public HomeDetails getHomeDetails(String homeName) {
        //if no deatils were found return null
        try {
            this.waypointCountdown = new CountDownLatch(1);
            System.out.println("Requesting waypoints ...");
            this.asyncClient.publish("owntracks/eishms/" 
                                    + homeUser.getUserLocationTopic() + "/cmd"
                                    , "{\"_type\":\"cmd\",\"action\":\"waypoints\"}".getBytes()
                                    , 2, false).waitForCompletion();  
            // this.waypointCountdown.await();
            this.waypointCountdown.await(10, TimeUnit.SECONDS);
            //if it timesout then the user is considered not to be present.
            //this.countDownLatch.await(timeout, unit);     
            if (newHomeDetails != null) {
                for (int i=0; i < newHomeDetails.size(); i++) {
                    if (newHomeDetails.get(i).getHomeName().matches(homeName)) {
                        return newHomeDetails.get(i);
                    }
                }
            }
        } catch (Exception e) {}

        return null;
    }

    public String getUsername() {
        return homeUser.getUserName();
    }
} 