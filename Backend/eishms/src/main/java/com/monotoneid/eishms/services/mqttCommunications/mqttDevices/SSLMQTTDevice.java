package com.monotoneid.eishms.services.mqttCommunications.mqttDevices;

import java.util.UUID;

import javax.net.ssl.SSLSocketFactory;

import com.monotoneid.eishms.dataPersistence.models.Device;
import com.monotoneid.eishms.dataPersistence.repositories.DeviceConsumptions;
import com.monotoneid.eishms.services.mqttCommunications.SSLSocketUtility;

import org.eclipse.paho.client.mqttv3.IMqttAsyncClient;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttAsyncClient;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;

public class SSLMQTTDevice {
    String serverUrl = "ssl://serverip:1883";
    String caFilePath = "/your_ssl/cacert.pem";
    String clientCrtFilePath = "/your_ssl/client.pem";
    String clientKeyFilePath = "/your_ssl/client.key";
    String mqttUserName = "guest";
    String mqttPassword = "123123";

    String asyncClientId;
    String[] subscribeTopics;
    String[] publishTopics;
    int[] deviceQos; 

    //Device Consumption Repository
    DeviceConsumptions deviceConsumptions;

    //Device Model
    Device device;

    //MQTT client
    IMqttAsyncClient asyncClient;

    public SSLMQTTDevice(Device device) {
        this.device = device;
        this.asyncClientId = UUID.randomUUID().toString();
        //Add all the topics to subscribe to, and all topics to publish to.
        
        try {
            this.asyncClient = new MqttAsyncClient(serverUrl, asyncClientId);
            MqttConnectOptions options = new MqttConnectOptions();

            options.setUserName(mqttUserName);
            options.setPassword(mqttPassword.toCharArray());

            options.setConnectionTimeout(60);
            options.setKeepAliveInterval(60);
            options.setMqttVersion(MqttConnectOptions.MQTT_VERSION_3_1);
            
            SSLSocketFactory socketFactory = SSLSocketUtility.getSocketFactory(caFilePath, clientCrtFilePath, clientKeyFilePath, "");
            
            options.setSocketFactory(socketFactory);
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
            //Handle the exception appropriately
        } catch(Exception e) {
            //Handle the exception appropriately
        }
    }
}