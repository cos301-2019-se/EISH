package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import java.util.UUID;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.DeviceConsumptions;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.stereotype.Service;

//@Service
public class MQTTDevice {
    String serverUrl = "tcp://192.168.8.100:1883";
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

    //Device Consumption Service
    

    //Device Model
    Device device;

    //MQTT client
    IMqttAsyncClient asyncClient;

    public MQTTDevice(Device device) {
        this.device = device;
        this.asyncClientId = UUID.randomUUID().toString();
        //Add all the topics to subscribe to, and all topics to publish to.
        
        try {
            this.asyncClient = new MqttAsyncClient(serverUrl, asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(mqttUserName);
            options.setPassword(mqttPassword.toCharArray());

            this.asyncClient.connect(options).waitForCompletion();
            this.asyncClient.subscribe(subscribeTopics, deviceQos).waitForCompletion();
            this.asyncClient.setCallback(new MqttCallback(){
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    //if (topic.matches(deviceTopics[0])); //get all the messages from a particular topic
                }
            
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                }
            
                @Override
                public void connectionLost(Throwable cause) {
                    
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

    public String[] createSubscriptionTopics() {
        return null;
    }

    public String[] createPublishTopics() {
        return null;
    }

    /* Device Controlling */

    public void turnOff() {

    }

    public void turnOn() {

    }

    public void toggle() {

    }
}