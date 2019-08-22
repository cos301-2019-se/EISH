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
        boolean inRegion = false;
        String homeName = "";  
        
        System.out.println("------------Location Event-------------");

        System.out.println("| The device topic: " + homeUser.getUserLocationTopic() + "               |");
        if (jsonContent.has("desc")) {
            deviceHomeName = jsonContent.get("desc").getAsString();
            System.out.println("| The device region: " + deviceHomeName + "               |");
        }

        try {
            HomeDetails homeDetails = locationManager.homeDetailsService.readFromFile();
            homeName = homeDetails.getHomeName();
            System.out.println("| The house region: " + homeName + "               |");
            // homeLatitude = homeDetails.getHomeLatitude();
            // homeLongitude = homeDetails.getHomeLongitude();
            // radius = homeDetails.getHomeRadius();
        } catch(Exception e) {

        }
        
        if (jsonContent.has("event")) {
            String locationEvent = "";
            locationEvent = jsonContent.get("event").getAsString();
            inRegion = locationEvent.matches("enter");
            System.out.println("| The device inside: " + inRegion + "             |");
        }

        Timestamp presenceTimestamp;
        if (homeName.matches(deviceHomeName)) {
            presenceTimestamp = new Timestamp(System.currentTimeMillis());
            this.isPresent = inRegion;
            System.out.println("| Inserted into database: " + this.isPresent + "           |");
            System.out.println("| Time:       " + presenceTimestamp + "           |");
            locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presenceTimestamp);
        } else  {
            presenceTimestamp = new Timestamp(System.currentTimeMillis());
            this.isPresent = false;
            System.out.println("| Inserted into database: " + this.isPresent + "           |");
            System.out.println("| Time:       " + presenceTimestamp + "           |");
            locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presenceTimestamp);
        }

        // if (regionName.matches("")) {
        //     presencTimestamp = new Timestamp(System.currentTimeMillis());
        //     this.isPresent = false;
        //     System.out.println("| Inserted into database: " + this.isPresent + "           |");
        //     System.out.println("| Time:       " + presencTimestamp + "           |");
        //     locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presencTimestamp);
        // }

        System.out.println("---------------------------------------");
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
                    newHomeDetails.add(currHomeDetails);
                }
            }
        }                                                        
    }  

    private void handleLocationMessage(String message) {
        JsonObject jsonContent = new JsonParser().parse(message)
                                                        .getAsJsonObject();
        JsonArray regions = null;
        String regionName = "";
        String homeName = "";

        String messageType = "";
        
        if (jsonContent.has("_type")) {
            messageType = jsonContent.get("_type").getAsString();
            System.out.println("_type = " + messageType);
        }

        System.out.println("------------Location Message-------------");

        try {
            HomeDetails homeDetails = locationManager.homeDetailsService.readFromFile();
            homeName = homeDetails.getHomeName();
            System.out.println("| The device topic: " + homeUser.getUserLocationTopic() + "               |");
            System.out.println("| The house region: " + homeName + "               |");
        } catch(Exception e) {

        }

        if (messageType.matches("location")) {
            if (jsonContent.has("inregions") ) {
                regions = jsonContent.getAsJsonArray("inregions");
            }

            //System.out.println("region array size = " + regions.size());
        
            String tmpRegionName = "";
            if (regions != null) {
                for (int i = 0; i < regions.size(); i++) {
                    tmpRegionName = regions.get(i).getAsString();
                    if (tmpRegionName.matches(homeName)) {
                        regionName = tmpRegionName;
                    }
                }    
            }
            System.out.println("| The device region: " + regionName + "               |");

            Timestamp presenceTimestamp;
            if (homeName.matches(regionName)) {
                presenceTimestamp = new Timestamp(System.currentTimeMillis());
                this.isPresent = true;
                System.out.println("| Inserted into database: " + this.isPresent + "           |");
                System.out.println("| Time:       " + presenceTimestamp + "           |");
                locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presenceTimestamp);
            }
    
            if (regionName.matches("")) {
                presenceTimestamp = new Timestamp(System.currentTimeMillis());
                this.isPresent = false;
                System.out.println("| Inserted into database: " + this.isPresent + "           |");
                System.out.println("| Time:       " + presenceTimestamp + "           |");
                locationManager.userPresenceService.addUserPresence(homeUser.getUserId(), this.isPresent, presenceTimestamp);
            }
        }

        System.out.println("-----------------------------------------");

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