package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Map;
import java.util.UUID;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.services.databaseManagementSystem.DeviceConsumptionService;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

//@Service
public class MQTTDevice {
    String serverUrl = "tcp://192.168.8.101:1883";
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

    String deviceState = "ON";

    MQTTDeviceManager deviceManager;

    Device device;

    IMqttAsyncClient asyncClient;

    public MQTTDevice(Device device, MQTTDeviceManager deviceManager) {
        this.device = device;
        this.asyncClientId = UUID.randomUUID().toString();
        this.deviceManager = deviceManager;
        try {
            this.asyncClient = new MqttAsyncClient(serverUrl, asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(mqttUserName);
            options.setPassword(mqttPassword.toCharArray());

            this.asyncClient.connect(options).waitForCompletion();

            setSubscriptionTopics();
            setPublishTopics();

            this.asyncClient.subscribe(subscribeTopics, deviceQos).waitForCompletion();
            this.asyncClient.setCallback(new MqttCallback(){
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {

                    if (topic.matches(subscribeTopics[0])) { // telemetry
                        String jsonMessage = new String(message.getPayload());
                        Map<String, Map<String, Float>> telemetryObject = jsonToMap(jsonMessage);
                        String hack = new String(""+telemetryObject.get("ENERGY").get("Power")+"");
                        float consumption = Float.parseFloat(hack);
                        Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
                        deviceManager.deviceConsumptionService.addDeviceConsumption(device.getDeviceId(), currentTimestamp, deviceState, consumption);
                    } else if (topic.matches(subscribeTopics[1])) { // power state

                    } else if (topic.matches(subscribeTopics[2])) { // requested consumption
                        
                    } else {
                        //ignore all other topics
                    }
                }
            
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {

                }
            
                @Override
                public void connectionLost(Throwable cause) {
                    System.out.println("Connection lost attempting to reconnect!");
                }
            });
        } catch(MqttException me) {
            me.printStackTrace();
            //Handle the exception appropriate
        }
    }

    public long getId() {
        return device.getDeviceId();
    }

    public String getName() {
        return device.getDeviceName();
    }

    private void setSubscriptionTopics() {
        String[] topics = new  String[3];
        topics[0] = new String("tele/" + device.getDeviceTopic() + "/SENSOR"); //auto consumption
        topics[1] = new String("stat/" + device.getDeviceTopic() + "/RESULT"); //get state
        topics[2] = new String("stat/" + device.getDeviceTopic() + "/STATUS8"); //get consumption
        subscribeTopics = topics;

        int[] qos = new int[3];
        qos[0] = 2;
        qos[1] = 2;
        qos[2] = 2;

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
            asyncClient.publish(publishTopics[0], "OFF".getBytes(), 2, false);
        } catch(MqttException me) {
            me.printStackTrace();
        }
    }

    public void turnOn() {
        try {
            asyncClient.publish(publishTopics[0], "ON".getBytes(), 2, false);
        } catch(MqttException me) {
            me.printStackTrace();
        }
    }

    public void toggle() {
        try {
            asyncClient.publish(publishTopics[0], "TOGGLE".getBytes(), 2, false);
        } catch(MqttException me) {
            me.printStackTrace();
        }
    }

    /* Device Query */

    public String getCurrentState() {
        try {
            asyncClient.publish(publishTopics[1], "".getBytes(), 2, false);
        } catch(MqttException me) {
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
}