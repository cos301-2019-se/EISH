# COS301-Phase3

## API Documentation
    
    URL = http://localhost
    PORT = 8080
  

| Endpoint  | API              | Method 	|Option Parameter| Parameters  | Response   | 
|:--------: |:------------     | :--:  	  | ----------- |-------------|----------|
| /api/devices         |Add Device   | POST   	| add_device      |option: string <br/> devicename: string <br/> publish_topic: string <br/> subscribe_topic: string <br/> max_watts:  int <br/> device_type: string <br/>| success: boolean <br> data: string |
| /api/devices          |View Device | POST     | view_devices  |option: string <br/> devicename: string | status: string <br> message: string|
|  /api/devices           |Control Device | POST   	| control_device  |option: string <br/> devicename: string | status: string <br> message: string  |
|  /api/devices           |View Device Consumption         | POST   	| device_consumption    |option: string <br/> devicename: string | email: string <br> name: string <br> surname: string  |
| /api/devices            |View Total Home Consumption         | POST   	| total_home_consumption    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|  /api/devices           |Add Generator Device        | POST   	| add_generator    |option: string <br/> devicename: string | email: string <br> name: string <br> surname: string  |
|  /api/devices           |View Generators       | POST   	|  view_generators  |option: string <br/> devicename: string | email: string <br> name: string <br> surname: string  |
|  /api/devices           |View Generator Generation       | POST   	| generator_generation    |option: string <br/> devicename: string | email: string <br> name: string <br> surname: string  |
|  /api/devices          |Total Home Generation       | POST   	| total_home_generation    |option: string <br/> devicename: string | email: string <br> name: string <br> surname: string  |

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