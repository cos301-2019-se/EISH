package com.monotoneid.eishms.mqtt_devices;

import java.util.UUID;

import com.monotoneid.eishms.model.Devices;

import org.apache.tomcat.util.json.JSONParser;
import org.eclipse.paho.client.mqttv3.IMqttActionListener;
import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.IMqttToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.JSONObject;

//If you wanna code this resources are
//http://www.yasith.me/2016/04/setting-up-mqtt-client-using-eclipse.html to get started
//https://www.eclipse.org/paho/files/javadoc/index.html
//specifically : MqttAsyncClient, MqttToken, and IMqttMessageListener
//https://www.baeldung.com/websockets-spring
//https://www.oracle.com/corporate/features/simple-messaging-with-mqtt.html a bit complicated but you'll manage

public class MqttDevice {
    private Devices device;
    private String consumptionMessage;
    private String consumptionTopic;
    private String stateTopic;
    private String powerMessage;
    private String asyncClientId; 
    private IMqttAsyncClient asyncClient;
    private IMqttToken connectToken; 
    //private DeviceConsumptionRepository dcr; //take as pointer from controller and store here! via constructor
    public MqttDevice(Devices dev) {
        this.device = dev;
        asyncClientId = UUID.randomUUID().toString();
        consumptionTopic = "cmnd/" + device.getDeviceTopic() + "/Status";
        stateTopic = "cmnd/" + device.getDeviceTopic() + "/RESULT";
        try {
            asyncClient = new MqttAsyncClient("tcp://127.0.0.1:1883", asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();
            this.connectToken = asyncClient.connect(options);
            System.out.println("Initializing mqttDevice "+ this.device.getDeviceName());
            this.connectToken.waitForCompletion();
            System.out.println(this.device.getDeviceName() +" connected to broker!");
            asyncClient.subscribe(consumptionTopic, 0).waitForCompletion();
            asyncClient.subscribe(stateTopic, 0).waitForCompletion();
            System.out.println("Done with subscribing");
            asyncClient.setCallback(new MqttCallback(){
            
                @Override
                public void messageArrived(String topic, MqttMessage message) throws Exception {
                    if (topic.matches(consumptionTopic)) {
                        System.out.println(device.getDeviceName() +" consumption: " + message.toString());
                        // ObjectMapper mapper = new ObjectMapper();
                        // try {
                            
                        //}
                    }
                    if (topic.matches(stateTopic)) {
                        System.out.println(device.getDeviceName() +" state: " + message.toString());
                    }
                    //System.out.println("Message arrived from " + topic + " and is " + message.getPayload().toString());            
                }
            
                @Override
                public void deliveryComplete(IMqttDeliveryToken token) {
                    
                }
            
                @Override
                public void connectionLost(Throwable cause) {
                    
                }
            });
        } catch(MqttException e) {
            System.out.println("The client is connected the broker!");
            e.printStackTrace();
        }
        
    }

    public void toggle() {
        if (!connectToken.isComplete())
            return;
        try {
            asyncClient.publish("cmnd/" + this.device.getDeviceTopic() + "/Power", new MqttMessage("TOGGLE".getBytes()));    //
        } catch(MqttException e) {
            e.printStackTrace();
        }
        
    }

    public long getDeviceID() {
        return device.getDeviceId();
    }

    //check actual device state, if on start monitoring/subscribe power usage and enter in database
    //(so subscribe callback must contain logic to write to database!)
    //else unsubscribe to power usage

    //write a control device method that will publish commands to physical device
    //once the device has completed the action notify frontend

    //monitor device state if state changes notify frontend via websockests

}