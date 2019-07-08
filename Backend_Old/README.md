

## API Documentation
    
### Example Usage for device
GET /api/devices
retrieve all devices 
response
[
    {
        "deviceName": "PanasonicTv",
        "deviceTopic": "sonoff-loungetv",
        "deviceStates": [
            "ON",
            "OFF",
            "STANDBY"
        ],
        "deviceId": 1,
        "devicePriority": "PRIORITY_NICETOHAVE"
    },
    
    {
        "deviceName": "SonyTv",
        "deviceTopic": "sonoff-masterbedroomtv",
        "deviceStates": [
            "ON",
            "OFF",
            "STANDBY"
        ],
        "deviceId": 5,
        "devicePriority": "PRIORITY_NICETOHAVE"
    }
]


GET api/device?deviceId=5
{
    "deviceName": "SonyTv",
    "deviceTopic": "sonoffmasterbedroomtv",
    "deviceStates": [
        "ON",
        "OFF",
        "STANDBY"
    ],
    "deviceId": 5,
    "devicePriority": "PRIORITY_NICETOHAVE"
}

add a new device to the system
POST /api/device
{
	"deviceName": "SonyTv",
	"deviceTopic":"sonoff-masterbedroomtv",
	"devicePriorityType":"PRIORITY_NICETOHAVE",
	"deviceStates":["ON","OFF","STANDBY"]
}

update a device to the system
PUT /api/device
{
    "deviceName": "SonyTv",
    "deviceTopic": "sonoffmasterbedroomtv",
    "deviceStates": [
        "ON",
        "OFF",
        "STANDBY"
    ],
    "deviceId": 5,
    "devicePriority": "PRIORITY_NICETOHAVE"
}
make sure the devicename and topic is unique



DELETE /api/device
{
  "deviceId":4
}



