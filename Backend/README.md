

## API Documentation

    URL = http://localhost
    PORT = 8080

| Endpoint  | API              | Method 	|Option Parameter| Parameters  | Response   | 
|:--------: |:------------     | :--:  	  | ----------- |-------------|----------|
| /api/devices         |Add Device   | POST   	| add_device      |option: string <br/> devicename: string <br/> publish_topic: string <br/> subscribe_topic: string <br/> max_watts:  int <br/> device_type: string <br/>| success: boolean <br> data: string |
| /api/devices          |View Device | POST     | view_devices  |option: string <br/> clientId: string | status: string <br> message: string|
|  /api/devices           |Control Device | POST   	| control_device  |option: string <br/> clientId: string | status: string <br> message: string  |
|  /api/devices           |View Device Consumption         | POST   	| device_consumption    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
| /api/devices            |View Total Home Consumption         | POST   	| total_home_consumption    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|  /api/devices           |Add Generator Device        | POST   	| add_generator    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|  /api/devices           |View Generators       | POST   	|  view_generators  |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|  /api/devices           |View Generator Generation       | POST   	| generator_generation    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|  /api/devices          |Total Home Generation       | POST   	| total_home_generation    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |


### Example Usage

REQUEST: Add New Client
```javascript
var http = require('http');

const data = JSON.stringify({
  option : 'insert',
  name: 'Peter',
  surname: 'Griffin',
  email: 'peter.griff@familymail.com',
  phonenumber: '5550112',
  address: '31 Spooner Street'
})

const options = {
  hostname : CIS_URL,
  port : CIS_PORT,
  path : "/",
  method : "POST",
  headers : {
      'Content-Type': 'application/json',
      'Content-Length': data.length
  }
}

http.request(options, (res) =>{
  res.on('data', (chunk) => {
    console.log(`Response Body: ${chunk}`);
  });
}).write(data);

```

RESPONSE: Add New Client 
    
  * Successful
```javascript
{
  "status":"success",
  "message":"successfully inserted"
}
```

  * Failed
```javascript
  //TODO
```

REQUEST: Get Email
```javascript
var http = require('http');

const data = JSON.stringify({
  option : 'getEmail',
  clientid: "1"
})

const options = {
  hostname : CIS_URL,
  port : CIS_PORT,
  path : "/",
  method : "POST",
  headers : {
      'Content-Type': 'application/json',
      'Content-Length': data.length
  }
}

http.request(options, (res) =>{
  res.on('data', (chunk) => {
    console.log(`Response Body: ${chunk}`);
  });
}).write(data);
```
