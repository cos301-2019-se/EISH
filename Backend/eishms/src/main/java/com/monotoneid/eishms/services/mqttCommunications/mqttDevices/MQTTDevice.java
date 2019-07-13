package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.util.UUID;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.services.mqttCommunications.QueryReplyManager;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class MQTTDevice {
    String serverUrl = "tcp://eishms.ddns.net:1883";
    /* This Requires no ssl */
    //String caFilePath = "/your_ssl/cacert.pem";
    //String clientCrtFilePath = "/your_ssl/client.pem";
    //String clientKeyFilePath = "/your_ssl/client.key";
    String mqttUserName = "eishms";
    String mqttPassword = "eishms";

    String asyncClientId;
    String[] subscribeTopics;
    String[] publishTopics;
    int[] deviceQos; 

    String deviceState;

    List<String> consumptionBacklog;

    MQTTDeviceManager deviceManager;

    Device device;

    IMqttAsyncClient asyncClient;

    QueryReplyManager queryReplyManager;

    boolean isAvailable;

    public MQTTDevice(Device device, MQTTDeviceManager deviceManager) {
        this.device = device;
        //this.asyncClientId = device.getDeviceName();
        this.asyncClientId = UUID.randomUUID().toString();
        System.out.println("uuid :" + this.asyncClientId);

        this.deviceManager = deviceManager;
        queryReplyManager = new QueryReplyManager();
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
            this.asyncClient.subscribe(subscribeTopics, deviceQos).waitForCompletion();
            this.asyncClient.setCallback(new MqttCallback(){
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    String jsonMessage = new String(message.getPayload());
                    queryReplyManager.setReply(topic, jsonMessage);
                    handleWill(topic, jsonMessage);
                    handleState(topic, jsonMessage);
                    if (topic.matches(subscribeTopics[0]) && deviceState.length() == 0)
                        consumptionBacklog.add(jsonMessage);
                    else
                        handleConsumption(topic, jsonMessage);   
                    //System.out.println( jsonMessage + "jsonMessage Handled"); 
                }
            
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {}
            
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection to MQTT Broker lost.");
                }
            });
        } catch(MqttException me) {
            me.printStackTrace();
            //Handle the exception appropriate
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
        } catch(MqttException me) {
            me.printStackTrace();
        }
    }

    public void turnOn() {
        try {
            asyncClient.publish(publishTopics[0], "ON".getBytes(), 0, false);
        } catch(MqttException me) {
            me.printStackTrace();
        }
    }

    public void toggle() {
        try {
            asyncClient.publish(publishTopics[0], "TOGGLE".getBytes(), 0, false);
        } catch(MqttException me) {
            me.printStackTrace();
        }
    }

    /* Device Query */

    public String getCurrentState() {
        String mqttReply;

        try {
            queryReplyManager.register(publishTopics[0], subscribeTopics[1]);
            asyncClient.publish(publishTopics[0], "".getBytes(), 2, false).waitForCompletion();
            if (queryReplyManager.replyExists(publishTopics[0])) {
                mqttReply = queryReplyManager.getReply(publishTopics[0]);
                //decode mqttReply and set deviceState
                deviceState = mqttReply;
            }
        } catch(MqttException me) {
            //It failed to publish a message so handle the error appropiately.
            me.printStackTrace();
        }

        return deviceState;
    }

    /* Helper Methods */
    private Map<String, Map<String, Float>> jsonToMap(String strJSON) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, Map<String, Float>> jsonMap = null;
        
        try {
            jsonMap = mapper.readValue(strJSON, Map.class);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return jsonMap;
    }

    private Map<String, String> jsonToMapString(String strJSON) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> jsonMap = null;
        
        try {
            jsonMap = mapper.readValue(strJSON, Map.class);
        } catch(IOException e) {
            e.printStackTrace();
        }

        return jsonMap;
    }

    /* Handler Functions */
    private void processConsumptionBacklog() {
        if (consumptionBacklog.isEmpty())
            return;
        
        while (!consumptionBacklog.isEmpty()) {
            parseAndSaveConsumption(consumptionBacklog.remove(0));
        }         
    }

    private void handleConsumption(String subscribeTopic, String message) {
        if (!subscribeTopic.matches(subscribeTopics[0]))
            return;
        
        processConsumptionBacklog();
        parseAndSaveConsumption(message);
    }

    private void parseAndSaveConsumption(String message) {
        Map<String, Map<String, Float>> telemetryObject = jsonToMap(message);
        String hack = new String(""+telemetryObject.get("ENERGY").get("Power")+"");
        float consumption = Float.parseFloat(hack);
        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
        //Remember to use the timestamp from the device not the server one
        deviceManager.deviceConsumptionService.addDeviceConsumption(device.getDeviceId(), currentTimestamp, deviceState, consumption);                
        Map<String, String> jsonConsumption = new HashMap<>();
        jsonConsumption.put("timestamp", currentTimestamp.toString());
        jsonConsumption.put("consumption", Float.toString(consumption));
        deviceManager.simpMessagingTemplate.convertAndSend("/device/" + device.getDeviceTopic() + "/consumption", jsonConsumption);
        System.out.println("Inserted Consumption in Database.");
    }

    private void handleState(String subscribeTopic, String message) {
        if (!subscribeTopic.matches(subscribeTopics[1]))
            return;

        deviceState = message;
        Map<String, String> jsonDeviceState = new HashMap<>();
        jsonDeviceState.put("state", deviceState);
        //deviceManager.simpMessagingTemplate.convertAndSend("/device/" + device.getDeviceTopic() + "/deviceState", jsonDeviceState);
        //System.out.println("Device State is now: " + deviceState);
    }

    private void handleWill(String subscribeTopic, String message) {
        if (!subscribeTopic.matches(subscribeTopics[3]))
            return;

        isAvailable = message.toLowerCase().matches("online");
        Map<String, Boolean> jsonDeviceState = new HashMap<>();
        jsonDeviceState.put("available", isAvailable);
        //deviceManager.simpMessagingTemplate.convertAndSend("/device/" + device.getDeviceTopic() + "/available", jsonDeviceState);
        //System.out.println("Device isAvailable: " + isAvailable);
        //Inform websocket of the current availability state
    }

    private void configureDevice() {
        //configure telemetry
        try {
            asyncClient.publish(publishTopics[0], "".getBytes(), 0, false).waitForCompletion();
        } catch(MqttException me) {
            me.printStackTrace();
        }
        
    }

}