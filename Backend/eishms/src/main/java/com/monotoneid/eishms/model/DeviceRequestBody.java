package com.monotoneid.eishms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DeviceRequestBody {
    private String option;
    private String device_name;
    private String publish_topic;
    private String subscribe_topic;
    private int max_watt;
    private int min_watt;
    private String device_type;
    private boolean device_state;
    private long start_date;
    private long end_date;
    
    public DeviceRequestBody(@JsonProperty("option") String o, @JsonProperty("device_name") String dn,
                            @JsonProperty("publish_topic") String pt, @JsonProperty("subscribe_topic") String st,
                            @JsonProperty("max_watt") int max, @JsonProperty("min_watt") int min,
                            @JsonProperty("device_type") String dt, @JsonProperty("start_date") long sd, 
                            @JsonProperty("end_date") long ed, @JsonProperty("device_state") boolean ds) {
        this.option = o;
        this.device_name = dn;
        this.publish_topic = pt;
        this.subscribe_topic = st;
        this.max_watt = max;
        this.min_watt = min;
        this.device_type = dt;
        this.start_date = sd;
        this.end_date = ed;    
        this.device_state = ds;                    
    }

    public String getOption() {
        return this.option;
    }

    public String getDeviceName() {
        return this.device_name;
    }

    public String getPublishTopic() {
        return this.publish_topic;
    }

    public String getSubscribeTopic() {
        return this.subscribe_topic;
    }

    public int getMaxWatt() {
        return this.max_watt;
    }

    public int getMinWatt() {
        return this.min_watt;
    }

    public String getDeviceType() {
        return this.device_type;
    }

    public boolean getDeviceState() {
        return this.device_state;
    }

    public long getStartDate() {
        return this.start_date;
    }

    public long getEndDate() {
        return this.end_date;
    }
}