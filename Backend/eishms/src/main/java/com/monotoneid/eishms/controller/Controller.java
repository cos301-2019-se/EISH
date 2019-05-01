package com.monotoneid.eishms.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monotoneid.eishms.model.Device;
import com.monotoneid.eishms.model.DeviceRequestBody;
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

    @PostMapping("/devices")
    public ObjectNode postRequest(@RequestBody DeviceRequestBody drb) {
        ObjectNode objectNode = null;
        if (drb.getOption().matches("add_device")) {
            objectNode = addDevice(drb.getDeviceName(), drb.getPublishTopic(), drb.getSubscribeTopic(), drb.getMaxWatt(), drb.getMinWatt(), drb.getDeviceType(), false);
        }
        return objectNode;
    }

    // @PostMapping("/devices")
    // public ObjectNode postRequest(@RequestBody DeviceRequestBody drb) {
    //     ArrayNode aa = mapper.createArrayNode();
    //     ObjectNode objectNode = mapper.createObjectNode();
    //     if (drb.getOption().matches("add_device")) {
    //         objectNode.put("success", true);
    //         objectNode.put("data", aa);
    //     }
    //     return objectNode;
    // }

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
            objectNode.put("success", true);
            objectNode.put("data", "Failed to insert " + device_name + ".");
        }

        return objectNode;
    }
}