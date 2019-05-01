package com.monotoneid.eishms.controller;

import java.util.List;

import javax.validation.Valid;

import com.monotoneid.eishms.datamodel.Devices;
import com.monotoneid.eishms.repository.DevicesRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
@RequestMapping("api")
public class DevicesController{
    @Autowired
    private DevicesRepository devicesRepository;

    @GetMapping("/devices")
    public List<Devices> getAllDevices() {
        return devicesRepository.findAll();
    }

}