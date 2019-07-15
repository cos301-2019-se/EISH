package com.monotoneid.eishms.configuration;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.*;


//@Configuration
public class LocalTimeConfig {

    @PostConstruct
    public void init() {

        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));

       System.out.println("Date in UTC: " + new Date().toString());
   }
}