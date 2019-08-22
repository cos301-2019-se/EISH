package com.monotoneid.eishms.services.mqttcommunications.mqttdevices;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import com.monotoneid.eishms.datapersistence.models.Device;
import com.monotoneid.eishms.datapersistence.models.NotificationPriorityType;
import com.monotoneid.eishms.services.mqttcommunications.QueryReplyManager;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import net.minidev.json.JSONObject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

//@Service
public class MqttDevice {
    // String serverUrl = "tcp://eishms.ddns.net:1883";
    private String serverUrl = "tcp://127.0.0.1:1883";
    //private String serverUrl = "tcp://localhost:1883";
    /* This Requires no ssl */
    //String caFilePath = "/your_ssl/cacert.pem";
    //String clientCrtFilePath = "/your_ssl/client.pem";
    //String clientKeyFilePath = "/your_ssl/client.key";
    private String mqttUserName = "eishms";
    private String mqttPassword = "eishms";

    private String asyncClientId;
    private String[] subscribeTopics;
    private String[] publishTopics;
    private int[] deviceQos; 

    private String deviceState;

    private List<String> consumptionBacklog;

    private MqttDeviceManager deviceManager;

    private Device device;

    private IMqttAsyncClient asyncClient;

    private QueryReplyManager queryReplyManager;

    private boolean isAvailable;

    public MqttDevice(Device device, MqttDeviceManager deviceManager) {
        this.device = device;
        //this.asyncClientId = device.getDeviceName();
        this.asyncClientId = UUID.randomUUID().toString();
        System.out.println("uuid :" + this.asyncClientId);

        this.deviceManager = deviceManager;
        consumptionBacklog = new ArrayList<String>();
        deviceState = "";
        try {
            this.asyncClient = new MqttAsyncClient(serverUrl, asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(mqttUserName);
            options.setPassword(mqttPassword.toCharArray());
            options.setCleanSession(false);
            options.setAutomaticReconnect(true);

            this.asyncClient.connect(options).waitForCompletion();

            //System.out.println("is connected: "+this.asyncClient.isConnected());

            setSubscriptionTopics();
            setPublishTopics();
            //this.asyncClient.setManualAcks(false);
            this.asyncClient.subscribe(subscribeTopics, deviceQos);//.waitForCompletion();
            this.asyncClient.setCallback(new MqttCallback() {
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String jsonMessage = new String(message.getPayload());
                    queryReplyManager.setReply(topic, jsonMessage);
                    handleWill(topic, jsonMessage);
                    handleState(topic, jsonMessage);
                    if (topic.matches(subscribeTopics[0]) && deviceState.length() == 0) {
                        consumptionBacklog.add(jsonMessage);
                    } else {
                        handleConsumption(topic, jsonMessage);   
                    }                        
                    //System.out.println( jsonMessage + "jsonMessage Handled"); 
                }
            
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            
                @Override
                public void connectionLost(Throwable cause) {
                    JSONObject notificationObject = new JSONObject();
                    Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                    notificationObject.put("priority", NotificationPriorityType.PRIORITY_CRITICAL.toString());
                    notificationObject.put("message", "Lost Connection to MQTT Broker.");
                    deviceManager.notificationService.addNotification("Lost Connection to MQTT Broker.", NotificationPriorityType.PRIORITY_CRITICAL.toString(), currentTimestamp);
                    deviceManager.simpMessagingTemplate.convertAndSend("/notification", notificationObject);
                }
            });
        } catch (MqttException me) {
            // me.printStackTrace();
            //Handle the exception appropriate
            // System.out.println( "connection error");
            //throw new Exception("Connection error mqtt device");
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            JSONObject notificationObject = new JSONObject();
            notificationObject.put("priority", NotificationPriorityType.PRIORITY_CRITICAL.toString());
            notificationObject.put("message", "Could not connect to MQTT Broker.");
            deviceManager.notificationService.addNotification("Could not connect to MQTT Broker.", NotificationPriorityType.PRIORITY_CRITICAL.toString(), currentTimestamp);
            deviceManager.simpMessagingTemplate.convertAndSend("/notification", notificationObject);
        }
        configureDevice();
    }

    public long getId() {
        return device.getDeviceId();
    }

    public String getName() {
        return device.getDeviceName();
    }

    private void setSubscriptionTopics() {
        String[] topics = new  String[4];
        topics[0] = new String("tele/" + device.getDeviceTopic() + "/SENSOR"); //auto consumption
        topics[1] = new String("stat/" + device.getDeviceTopic() + "/POWER"); //get state
        topics[2] = new String("stat/" + device.getDeviceTopic() + "/STATUS8"); //get consumption
        topics[3] = new String("tele/" + device.getDeviceTopic() + "/LWT"); //last will
        subscribeTopics = topics;

        int[] qos = new int[4];
        qos[0] = 2;
        qos[1] = 0;
        qos[2] = 2;
        qos[3] = 2;

        deviceQos = qos;
    }

    private void setPublishTopics() {
        String[] topics = new  String[2];
        topics[0] = new String("cmnd/" + device.getDeviceTopic() + "/Power"); //power control
        topics[1] = new String("cmnd/" + device.getDeviceTopic() + "/Status"); //get state
        publishTopics = topics;
    }

    /* Device Controlling */

    public void turnOff() {
        try {
            asyncClient.publish(publishTopics[0], "OFF".getBytes(), 0, false);
        } catch (MqttException me) {
            //me.printStackTrace();
            JSONObject notificationObject = new JSONObject();
            notificationObject.put("priority", NotificationPriorityType.PRIORITY_CRITICAL.toString());
            String message = "Could not turn off device." + getName();
            notificationObject.put("message", message);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            deviceManager.notificationService.addNotification(message, NotificationPriorityType.PRIORITY_CRITICAL.toString(), currentTimestamp);
            deviceManager.simpMessagingTemplate.convertAndSend("/notification", notificationObject);
        }
    }

    public void turnOn() {
        try {
            asyncClient.publish(publishTopics[0], "ON".getBytes(), 0, false);
        } catch (MqttException me) {
            // me.printStackTrace();
            JSONObject notificationObject = new JSONObject();
            notificationObject.put("priority", NotificationPriorityType.PRIORITY_CRITICAL.toString());
            String message = "Could not turn on device." + getName();
            notificationObject.put("message", message);
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            deviceManager.notificationService.addNotification(message, NotificationPriorityType.PRIORITY_CRITICAL.toString(), currentTimestamp);
            deviceManager.simpMessagingTemplate.convertAndSend("/notification", notificationObject);
        }
    }

    public void toggle() {
        try {
            asyncClient.publish(publishTopics[0], "TOGGLE".getBytes(), 0, false);
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

    /* Device Query */

    public String getCurrentState() {
        try {
            asyncClient.publish(publishTopics[0], "".getBytes(), 2, false).waitForCompletion();
        } catch (MqttException me) {
            me.printStackTrace();
        }

        return deviceState;
    }

    /* Handler Functions */
    private void processConsumptionBacklog() {
        if (consumptionBacklog.isEmpty()) {
            return;
        }
            
        while (!consumptionBacklog.isEmpty()) {
            parseAndSaveConsumption(consumptionBacklog.remove(0));
        }         
    }

    private void handleConsumption(String subscribeTopic, String message) {
        if (!subscribeTopic.matches(subscribeTopics[0])) {
            return;
        }
            
        processConsumptionBacklog();
        parseAndSaveConsumption(message);
    }

    private void parseAndSaveConsumption(String message) {
        JsonObject jsonContent = new JsonParser().parse(message)
                                                        .getAsJsonObject();

        if (jsonContent.has("ENERGY")) {
            JsonObject energyObject = jsonContent.get("ENERGGY").getAsJsonObject();
            float consumption = energyObject.get("Power").getAsFloat();
            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            deviceManager.deviceConsumptionService.addDeviceConsumption(
                device.getDeviceId(), 
                currentTimestamp, deviceState, consumption);
    
            JSONObject consumptionObject = new JSONObject();
            consumptionObject.put("deviceConsumptionTimestamp", currentTimestamp.toString());
            consumptionObject.put("deviceConsumption", Float.toString(consumption));
            deviceManager.simpMessagingTemplate.convertAndSend("/device/" 
                + device.getDeviceTopic() 
                + "/consumption", consumptionObject);
        }         
    }

    private void handleState(String subscribeTopic, String message) {
        if (!subscribeTopic.matches(subscribeTopics[1])) {
            return;
        }
            
        deviceState = message;
        JSONObject stateObject = new JSONObject();
        stateObject.put("state", deviceState);
        deviceManager.simpMessagingTemplate.convertAndSend("/device/" + device.getDeviceTopic() + "/state", stateObject);
    }

    private void handleWill(String subscribeTopic, String message) {
        if (!subscribeTopic.matches(subscribeTopics[3])) {
            return;
        }
            
        isAvailable = message.toLowerCase().matches("online");
        JSONObject stateObject = new JSONObject();
        stateObject.put("available", isAvailable);
        deviceManager.simpMessagingTemplate.convertAndSend("/device/" + device.getDeviceTopic() + "/state", stateObject);
    }

    private void configureDevice() {
        //configure telemetry
        try {
            asyncClient.publish(publishTopics[0], "".getBytes(), 0, false).waitForCompletion();
        } catch (MqttException me) {
            me.printStackTrace();
        }
    }

}