package com.monotoneid.eishms.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.monotoneid.eishms.model.DeviceRequestBody;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class Controller {
    @Autowired
    ObjectMapper mapper;

    @PostMapping("/devices")
    public ObjectNode postRequest(@RequestBody DeviceRequestBody drb) {
        ArrayNode aa = mapper.createArrayNode();
        aa.add(5);
        aa.add(6);
        aa.add(7);
        ObjectNode objectNode = mapper.createObjectNode();
        if (drb.getOption().matches("add_device")) {
            objectNode.put("success", true);
            objectNode.put("data", aa);
        }
        return objectNode;
    }
}