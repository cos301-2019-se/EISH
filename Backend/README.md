#API Documentation
| Endpoint  | API              | Method 	|Option Parameter| Parameters  | Response   | 
|:--------: |:------------     | :--:  	  | ----------- |-------------|----------|
| /         |Add New Client    | POST   	| insert      |option: string <br/> name: string <br/> surname: string <br/> email: string <br/> phoneNumber:  string <br/> address: string <br/>| status: string <br> message: string |
|           |Deactivate Client | POST     | deactivate  |option: string <br/> clientId: string | status: string <br> message: string|
|           |Reactivate Client | POST   	| reactivate  |option: string <br/> clientId: string | status: string <br> message: string  |
|           |Get Email         | POST   	| getEmail    |option: string <br/> clientId: string | email: string <br> name: string <br> surname: string  |

### Example Usage
