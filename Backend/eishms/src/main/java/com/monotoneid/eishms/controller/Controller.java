package com.monotoneid.eishms.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("api")
@RestController
public class Controller {
    
    @GetMapping("/devices")
    public String doSomething() {
        return "";
    }
}