package com.monotoneid.eishms.model;

import com.fasterxml.jackson.annotation.JsonProperty;

enum Priority{
    HIGH,MEDIUM,LOW;
}

public class DeviceRequestBody {

    
    private long device_id;
    private String device_name;
    private String topic;
    private int max_watts;
    private int min_watts;
    private String device_type;
    private boolean device_state;
    private long start_date;
    private long end_date;
    private boolean auto_start;
    private int device_priority;

    private String generator_name;
    private int generator_min_capacity;
    private int generator_max_capacity;
    private String generator_type;
    private String generator_topic;

    public DeviceRequestBody(){}
    
    
    public DeviceRequestBody(@JsonProperty("device_id") long di,
                            @JsonProperty("device_name") String dn,
                            @JsonProperty("topic") String t,
                            @JsonProperty("max_watts") int max,
                            @JsonProperty("min_watts") int min,
                            @JsonProperty("device_type") String dt, 
                            @JsonProperty("start_date") long sd, 
                            @JsonProperty("end_date") long ed,
                            @JsonProperty("device_state") boolean ds,
                            @JsonProperty("auto_start") boolean as,
                            @JsonProperty("device_priority") String p,
                            @JsonProperty("generator_name") String gn,
                            @JsonProperty("generator_type") String gt,
                            @JsonProperty("min_capacity_watts") int mincw,
                            @JsonProperty("max_capacity_watts") int maxcw,
                            @JsonProperty("generator_topic") String gentopic) {
        
        this.device_id = di;
        this.device_name = dn;
        this.topic = t;
        this.max_watts = max;
        this.min_watts = min;
        this.device_type = dt;
        this.start_date = sd;
        this.end_date = ed;    
        this.device_state = ds;
        this.auto_start=as;
         
        if(p!=null){
            if(p.toUpperCase().matches("HIGH")){
                this.device_priority=2;
            } else if(p.toUpperCase().matches("MEDIUM")){
                this.device_priority=1;
            }else if(p.toUpperCase().matches("LOW")){
                this.device_priority=0;
            }
            else{
                this.device_priority=0;
            }
    }
        this.generator_name=gn;
        this.generator_type=gt;
        this.generator_max_capacity=maxcw;
        this.generator_min_capacity=mincw;
        this.generator_topic=gentopic;

         
                          
    }

    public long getDeviceID() {
        return this.device_id;
    }
    public String getDeviceName() {
        return this.device_name;
    }

    public String getDeviceTopic() {
        return this.topic;
    }

    public int getMaxWatt() {
        return this.max_watts;
    }

    public int getMinWatt() {
        return this.min_watts;
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
    public boolean getDeviceAutoStart(){
        return this.auto_start;
    }
    public int getDevicePriority(){
        return this.device_priority;
    }
    public String getGeneratorName(){
        return this.generator_name;
    }
    public String getGeneratorTopic(){
        return this.generator_topic;
    }
    public String getGeneratorType(){
        return this.generator_type;
    }
    public int getGeneratorMaxCapacity(){
        return this.generator_max_capacity;
    }
    public int getGeneratorMinCapacity(){
        return this.generator_min_capacity;
    }


}