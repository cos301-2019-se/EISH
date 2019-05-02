package com.monotoneid.eishms.mqtt_devices;

import org.eclipse.paho.client.mqttv3.MqttAsyncClient;


//If you wanna code this resources are
//http://www.yasith.me/2016/04/setting-up-mqtt-client-using-eclipse.html to get started
//https://www.eclipse.org/paho/files/javadoc/index.html
//specifically : MqttAsyncClient, MqttToken, and IMqttMessageListener
//https://www.baeldung.com/websockets-spring
//https://www.oracle.com/corporate/features/simple-messaging-with-mqtt.html a bit complicated but you'll manage

//please asynchronous client MqttAsyncClient
//Whoever you are I believe in you!!! 

public class MqttDevice {
    private String device_name;
    private String publish_topic;
    private String subscribe_topic;
    private boolean device_state;
    private String clientId; 
    private MqttAsyncClient asyncClient;
    //private DeviceConsumptionRepository dcr; //take as pointer from controller and store here! via constructor
    

    //check actual device state, if on start monitoring/subscribe power usage and enter in database
    //(so subscribe callback must contain logic to write to database!)
    //else unsubscribe to power usage

    //write a control device method that will publish commands to physical device
    //once the device has completed the action notify frontend

    //monitor device state if state changes notify frontend via websockests


}