var express = require('express');
var bodyParser = require('body-parser');
var mqtt = require('mqtt');
var jsonfile = require('jsonfile');
var fs = require('fs');
var Device = require('./deviceClass');
var path = require('path');

//inits
const app = express();
const port = 3000;

var client = mqtt.connect('mqtt://127.0.0.1:1883');
var activeDevices = [];
var file = 'devices.json';

//uses
app.use(bodyParser.urlencoded({ extended: true }));
app.use(bodyParser.json());

//setup
var server = app.listen(port,()=> {
    loadDevices();
    console.log(`Communication Module: listening on port ${port}!`);
})

app.get('/', function(req, res) {
    res.sendFile(path.join(__dirname + '/simulation.html'));
});

app.get("/view/devices", function(req,res) {
    var  displayInfo = getDevicesDisplayInfo();
    return res.send(displayInfo);
})

app.post("/add/device", function(req,res) {
    var device = new Device(req.body,client);
    device.configure();
    appendDevice(device);
    return res.send(device.name + " has been successfully created!");
})

const wss = new SocketServer({server});

wss.on('connection', (ws) => {
	ws.on('message', (message) => {
		var msgJson = JSON.parse(message);
		if (msgJson.type == 'name')
			activeDevices.forEach((device) => {
				if (device.deviceName == msgJson.deviceName)
					device.setSocket(ws);
			});
	});
});

/**
 * Appends JSON device object to devices.json
 * @param device 
 */
function appendDevice(device) {
    if (!fs.existsSync(file)) {
        initialiseDevicesFile();
    }

    activeDevices.push(device);
    var fileObj = jsonfile.readFileSync(file);
    device.client = null;
    fileObj['data'].push(device);
    writeToDevicesFile(fileObj);
    console.log("Device was appended to file.");
}

/**
 * loads the devices from device.json into devices[]
 */
function loadDevices() {
    if (!fs.existsSync(file)) {
        initialiseDevicesFile(); 
    }
    var fileObj = jsonfile.readFileSync(file);
    for(var i = 0; i < fileObj['data'].length; i++) {
        var device = new Device(fileObj['data'][i],client);
        device.configure();    
        activeDevices.push(device);
    }
}

/**
 * Makes array with data relevant for the device box on the interface side
 * Send data for Device box 
 * Load through the devices array
 */
function getDevicesDisplayInfo(){
    var deviceObjs = [];
    var deviceObj = null;
    for(var i = 0; i < activeDevices.length; i++){
        deviceObj = {
            name: activeDevices[i].name,
            type: activeDevices[i].type,
            state: activeDevices[i].state
        }
        deviceObjs.push(deviceObj);
    }
    return deviceObjs;
}

/**
 * Initialises the json log file
 */
function initialiseDevicesFile() {
    var header = { List: "Devices", data: [] };
    writeToDevicesFile(header);
    console.log("Devices file has been initialised.");
}

function writeToDevicesFile(jsonObj) {
    jsonfile.writeFileSync(file, jsonObj, { flag: "w+" }, function(err) {
      if (err) console.error(err);
    });
}