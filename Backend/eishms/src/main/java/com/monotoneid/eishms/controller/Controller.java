package com.monotoneid.eishms.controller;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monotoneid.eishms.device_manager.DeviceManager;
import com.monotoneid.eishms.model.Device;
import com.monotoneid.eishms.model.DeviceRequestBody;
import com.monotoneid.eishms.mqtt_devices.MqttDevice;
import com.monotoneid.eishms.repository.DevicesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class Controller {
    @Autowired
    private DevicesRepository devicesRepository;
    
    @Autowired
    ObjectMapper mapper;

    //private List<MqttDevice> mqttDevices;
    private DeviceManager deviceManager;

    public Controller() {
        //initialize mqtt devices with data in the database, such as name, topics, ect...
        deviceManager = new DeviceManager(devicesRepository);
    }

    @PostMapping("/devices")
    public ObjectNode postRequest(@RequestBody DeviceRequestBody drb) {
        ObjectNode objectNode = null;
        if (drb.getOption().matches("add_device")) {
            objectNode = addDevice(drb.getDeviceName(), drb.getPublishTopic(), drb.getSubscribeTopic(), drb.getMaxWatt(), drb.getMinWatt(), drb.getDeviceType(), false);
        } else  if (drb.getOption().matches("view_devices")) {
            objectNode = viewDevices();
        } else if (drb.getOption().matches("control_device")) {
            //find the correct device in mqttDevices and call the method that controls the physical device
        } else if (drb.getOption().matches("device_consumption")) {

        } else if (drb.getOption().matches("device_consumption")) {

        } else {
            //Did not enter correct option
        }
        return objectNode;
    }

    public ObjectNode addDevice(String device_name, String publish_topic, String subscribe_topic, int max_watt, int min_watt, String device_type, boolean device_state) {
        ObjectNode objectNode = mapper.createObjectNode();
        Device newDevice = new Device();
        Device returnDevice;
        newDevice.setDeviceName(device_name);
        newDevice.setPublishTopic(publish_topic);
        newDevice.setSubscribeTopic(subscribe_topic);
        newDevice.setMaxWatt(max_watt);
        newDevice.setMinWatt(min_watt);
        newDevice.setDeviceType(device_type);
        newDevice.setDeviceState(device_state);
        
        if ((returnDevice = devicesRepository.save(newDevice)) != null) {
            objectNode.put("success", true);
            objectNode.put("data", returnDevice.getDeviceName() + " successfully inserted.");
        } else {
            objectNode.put("success", false);
            objectNode.put("data", "Failed to insert " + device_name + ".");
        }

        return objectNode;
    }

    public ObjectNode viewDevices() {
        List<Device> allDevices = devicesRepository.findAll();
        ObjectNode objectNode = mapper.createObjectNode();
        ObjectNode insideObjects = mapper.createObjectNode();
        ArrayNode arrayObjects = mapper.createArrayNode();

        if (allDevices.isEmpty()) {
            objectNode.put("success", false);
            objectNode.put("data", "Could not find any device.");
            return objectNode;
        }

        Device curDevice;
        for (int i=0; i < allDevices.size(); i++) {
            curDevice = allDevices.get(i);
            insideObjects.put("device_name", curDevice.getDeviceName());
            insideObjects.put("device_type", curDevice.getDeviceType());
            insideObjects.put("device_state", curDevice.getDeviceState());
            arrayObjects.add(insideObjects);
            insideObjects = mapper.createObjectNode();
        }

        objectNode.put("success", true);
        objectNode.put("data", arrayObjects);

        return objectNode;
    }
}