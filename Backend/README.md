# COS301-Phase3

## API Documentation

    CIS_URL = N/A
    CIS_PORT = N/A

| Endpoint  | API              | Method 	|Option Parameter| Parameters  | Response   | 
|:--------: |:------------     | :--:  	  | ----------- |-------------|----------|
| /         |Add Device   | POST   	| insert      |option: string <br/> name: string <br/> surname: string <br/> email: string <br/> phoneNumber:  string <br/> address: string <br/>| status: string <br> message: string |
|           |View Device | POST     | deactivate  |option: string <br/> clientId: string | status: string <br> message: string|
|           |Control Device | POST   	| reactivate  |option: string <br/> clientId: string | status: string <br> message: string  |
|           |View Device Consumption         | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|           |View Total Home Consumption         | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|           |Add Generator Device        | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|           |View Generators       | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|           |View Generator Generation       | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |
|           |Total Home Generation       | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |


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
