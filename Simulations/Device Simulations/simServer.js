var express = require('express');
var SocketServer = require('ws').Server;
var bodyParser = require('body-parser');
var mqtt = require('mqtt');
var jsonfile = require('jsonfile');
var fs = require('fs');
var Device = require('./deviceClass');
var path = require('path');

//inits
const app = express();
const port = 3000;

//var client = mqtt.connect('mqtt://127.0.0.1:1883');
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

//Serving simulations.html web page
app.get('/', function(req, res) {
    res.set('Content-Type', 'text/html');
    res.sendFile(path.join(__dirname + '/simulations.html'));
});

app.use(express.static(path.join(__dirname, '')))

app.get("/view/devices", function(req,res) {
    var  displayInfo = getDevicesDisplayInfo();
    return res.send(JSON.stringify(displayInfo));
})

app.post("/add/device", function(req,res) {
    appendDevice(req.body);
    //return res.send(device.name + " has been successfully created!");
    return res.send("Device has been successfully created!");
})

const wss = new SocketServer({server});
let outletSocket = null;

wss.on('connection', (ws) => {
	ws.on('message', (message) => {
		var msgJson = JSON.parse(message);
        if (msgJson.type == 'pv') {
            outletSocket = ws;
            console.log("Connected to pv socket");
        }

		if (msgJson.type == 'name') {
            activeDevices.forEach((device) => {
                if (device.name == msgJson.deviceName) {
                    //console.log("Found the device");
                    device.setSocket(ws);
                    device.setPowerOutletConnection(outletSocket);
                }
            });
        }
			
	});
});

/**
 * Appends JSON device object to devices.json
 * @param device 
 */
function appendDevice(deviceInfo) {
    if (!fs.existsSync(file)) {
        initialiseDevicesFile();
    }

    var device = new Device(deviceInfo,mqtt);
    device.configure();
    var fileObj = jsonfile.readFileSync(file);
    var tempClient = device.client;
    device.client = null;
    fileObj['data'].push(device);
    writeToDevicesFile(fileObj);
    device.client = tempClient;
    activeDevices.push(device);
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
        var device = new Device(fileObj['data'][i],mqtt);
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

/**
 * Performs the operation of writing the device into the devices.json file
 * @param jsonObj 
 */
function writeToDevicesFile(jsonObj) {
    jsonfile.writeFileSync(file, jsonObj, { flag: "w+" }, function(err) {
      if (err) console.error(err);
    });
}