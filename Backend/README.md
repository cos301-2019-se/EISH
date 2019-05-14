# COS301-Capstone Project Phase 1

## API Documentation
    
    URL = http://localhost
    PORT = 8080
  

| Endpoint  | API              | Method 	| Parameters  | Response   | 
|:--------: |:------------     | :--:  	  | -------------|----------|
| /api/add/device         |Add Device   | POST   	|device_name: string <br/> topic: string <br/> max_watts:  int <br/> min_watts: int <br/> device_type: string <br/> priority: int <br/> auto_start: boolean <br/>|data: string |
| /api/view/devices          |View Devices | GET     |  |data: array of objects|
|  /api/control/device/{device_name}           |Control Device | PUT/PATCH   	|device_state: boolean|data: object  |
|  /api/view/device/consumption/{device_name}           |View Device Consumption         | GET   	|start_date: timestamp <br/> end_date: timestamp |data: array of objects|
| /api/view/home/consumption            |View Total Home Consumption         | GET   	|start_date: timestamp <br/> end_date: timestamp  |data: array of objects |
|  /api/add/generator           |Add Generator Device        | POST   	| generator_name: string <br/> topic: string <br/> max_capacity:  int <br/> min_capacity: int <br/> generator_type: string <br/> |data: string |
|  /api/view/generators           |View Generators       | GET   	| |data: array of objects |
|  /api/view/generator/generation/{generator_name}         |View Generator Generation       | GET   	| start_date: timestamp <br/> end_date: timestamp | data: array of objects|
|  /api/view/home/generation          |Total Home Generation       | GET   	| start_date: timestamp <br/> end_date: timestamp  |data: array of objects |

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
  "data":"Fridge successfully inserted."
}
```
REQUEST: View Devices
```javascript
{
}
```

RESPONSE: View Devices 
    
  * Successful
```javascript
{
   "data":[{"device_name":"Fridge","device_type":"Fridge","device_state":true},...,{"device_name":"Bedroom Light","device_type":"Light","device_state":false}]
}
```

REQUEST: Control Device
```javascript
{
    "device_state":false
}
```

RESPONSE: Control Device 
    
  * Successful
```javascript
{
  "data":{"device_name":"Fridge", "device_state":false}
}
```

REQUEST: View Device Consumption
```javascript
{
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: View Device Consumption 
    
  * Successful
```javascript
{
  "data":[{"date_time":15321484654, "consumption":150},...,{"date_time":18548484654, "consumption":181}]
}
```

REQUEST: Total Home Consumption
```javascript
{
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: Total Home Consumption 
    
  * Successful
```javascript
{
  "data":[{"date_time":15321484654, "consumption":1680},...,{"date_time":18548484654, "consumption":1912}]
}
```

### Implement functionalities below if there is time.

REQUEST: Add Generation Device
```javascript
{
    "generator_name":"Solar Power System",
    "topic":"solar-power",
    "min_capacity_watt":1384,
    "max_capacity_watt":3254,
    "generator_type":"Solar Power"
}
```

RESPONSE: Add Generation Device 
    
  * Successful
```javascript
{
  "data":"Solar Power System successfully inserted."
}
```

REQUEST: View Generators
```javascript
{
}
```

RESPONSE: View Generators 
    
  * Successful
```javascript
{
  "data":[{"generator_name":"Solar Power System","generator_type":"Solar Power","generator_state":true},...,{"generator_name":"Diesel Generator","generator_type":"Standby Generator","generator_state":false}]
}
```

REQUEST: View Generator Generation
```javascript
{
    "generator_name":"Solar Power System",
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: View Generator Generation 
    
  * Successful
```javascript
{
  "data":[{"date_time":15321484654, "generation":240},...,{"date_time":18548484654, "generation":181}]
}
```

REQUEST: Total Home Generation
```javascript
{
    "start_date":15321484654,
    "end_date":18548484654
}
```

RESPONSE: Total Home Generation 
    
  * Successful
```javascript
{
  "data":[{"date_time":15321484654, "generation":2640},...,{"date_time":18548484654, "generation":1713}]
}
```
