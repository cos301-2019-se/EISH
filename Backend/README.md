# COS301-Capstone Project Phase 1

## API Documentation
    
    URL = http://localhost
    PORT = 8080
  

| Endpoint  | API              | Method 	|Option Parameter| Parameters  | Response   | 
|:--------: |:------------     | :--:  	  | ----------- |-------------|----------|
| /api/devices         |Add Device   | POST   	| add_device      |option: string <br/> device_name: string <br/> publish_topic: string <br/> subscribe_topic: string <br/> max_watts:  int <br/> min_watts: int <br/> device_type: string <br/>| success: boolean <br/> data: string |
| /api/devices          |View Device | POST     | view_devices  |option: string  | success: boolean <br> data: array of objects|
|  /api/devices           |Control Device | POST   	| control_device  |option: string <br/> device_name: string <br/> device_state: boolean| success: boolean <br/> data: object  |
|  /api/devices           |View Device Consumption         | POST   	| device_consumption    |option: string <br/> device_name: string <br/> start_date: timestamp <br/> end_date: timestamp | success: boolean <br> data: array of objects|
| /api/devices            |View Total Home Consumption         | POST   	| total_home_consumption    |option: string <br/>start_date: timestamp <br/> end_date: timestamp  | success: boolean <br> data: array of objects |
|  /api/generators           |Add Generator Device        | POST   	| add_generator    |option: string <br/> generator_name: string <br/> publish_topic: string <br/> subscribe_topic: string <br/> max_capacity:  int <br/> min_capacity: int <br/> generator_type: string <br/> |success: boolean <br/> data: string |
|  /api/generators           |View Generators       | POST   	|  view_generators  |option: string <br/> generator_name: string |  success: boolean <br> data: array of objects |
|  /api/generators          |View Generator Generation       | POST   	| generator_generation    |option: string <br/> generator_name: string <br/> start_date: timestamp <br/> end_date: timestamp | success: boolean <br> data: array of objects|
|  /api/generators          |Total Home Generation       | POST   	| total_home_generation    |option: string <br/>start_date: timestamp <br/> end_date: timestamp  | success: boolean <br> data: array of objects |

### Example Usage

REQUEST: Add Device
```javascript
{
    "option":"add_device",
    "device_name":"Fridge",
    "publish_topic":"stat/sonoff-fridge/POWER",
    "subscribe_topic":"cmnd/sonoff-fridge/Power",
    "min_watt":100,
    "max_watt":200,
    "device_type":"Fridge"
}
```

RESPONSE: Add Device 
    
  * Successful
```javascript
{
  "success":true,
  "data":"Fridge successfully inserted."
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Failed to insert Fridge."
  }
```

REQUEST: View Devices
```javascript
{
    "option":"view_devices",
}
```

RESPONSE: View Devices 
    
  * Successful
```javascript
{
  "success":true,
  "data":[{"device_name":"Fridge","device_type":"Fridge","device_state":true},...,{"device_name":"Bedroom Light","device_type":"Light","device_state":false}]
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Failed to retrieve devices."
  }
```

REQUEST: Control Device
```javascript
{
    "option":"control_device",
    "device_name":"Fridge",
    "device_state":false
}
```

RESPONSE: Control Device 
    
  * Successful
```javascript
{
  "success":true,
  "data":{"device_name":"Fridge", "device_state":false}
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Couldn't turn off Fridge OR Fridge not found."
  }
```

REQUEST: View Device Consumption
```javascript
{
    "option":"device_consumption",
    "device_name":"Fridge",
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: View Device Consumption 
    
  * Successful
```javascript
{
  "success":true,
  "data":[{"date_time":15321484654, "consumption":150},...,{"date_time":18548484654, "consumption":181}]
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Consumption data between 15321484654 and 18548484654 not found."
  }
```

REQUEST: Total Home Consumption
```javascript
{
    "option":"total_home_consumption",
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: Total Home Consumption 
    
  * Successful
```javascript
{
  "success":true,
  "data":[{"date_time":15321484654, "consumption":1680},...,{"date_time":18548484654, "consumption":1912}]
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Consumption data between 15321484654 and 18548484654 not found."
  }
```

### Implement functionalities below if there is time.

REQUEST: Add Generation Device
```javascript
{
    "option":"add_generator",
    "generator_name":"Solar Power System",
    "publish_topic":"stat/solar-power/POWER",
    "subscribe_topic":"cmnd/solar-power/Power",
    "min_capacity_watt":1384,
    "max_capacity_watt":3254,
    "generator_type":"Solar Power"
}
```

RESPONSE: Add Generation Device 
    
  * Successful
```javascript
{
  "success":true,
  "data":"Solar Power System successfully inserted."
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Failed to insert Solar Power System."
  }
```

REQUEST: View Generators
```javascript
{
    "option":"view_generators",
}
```

RESPONSE: View Generators 
    
  * Successful
```javascript
{
  "success":true,
  "data":[{"generator_name":"Solar Power System","generator_type":"Solar Power","generator_state":true},...,{"generator_name":"Diesel Generator","generator_type":"Standby Generator","generator_state":false}]
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Failed to retrieve generators."
  }
```
REQUEST: View Generator Generation
```javascript
{
    "option":"generator_generation",
    "generator_name":"Solar Power System",
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: View Generator Generation 
    
  * Successful
```javascript
{
  "success":true,
  "data":[{"date_time":15321484654, "generation":240},...,{"date_time":18548484654, "generation":181}]
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Generation data between 15321484654 and 18548484654 not found."
  }
```

REQUEST: Total Home Generation
```javascript
{
    "option":"total_home_consumption",
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: Total Home Generation 
    
  * Successful
```javascript
{
  "success":true,
  "data":[{"date_time":15321484654, "generation":2640},...,{"date_time":18548484654, "generation":1713}]
}
```

  * Failed
```javascript
  {
    "success":false,
    "data":"Generation data between 15321484654 and 18548484654 not found."
  }
```
