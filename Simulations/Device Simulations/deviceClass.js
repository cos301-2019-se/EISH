const Coordinator = require('./coordinator.js');

module.exports = class Device {
    constructor(object, mqtt) {
        this.name = object.name;
        this.topic = object.topic;
        this.minWatt = object.minWatt;
        this.maxWatt = object.maxWatt;
        this.consumption = object.consumption;
        this.state = false;
        // this.state = object.state;
        this.type = object.type;
        this.client = mqtt.connect('mqtt://127.0.0.1:1883',{username: "eishms", password: "eishms"});
        this.timeInterval = 10000 + object.timeInterval;
        this.socket = null;
        this.powerOutletConnection = null;
        this.coordinator = Coordinator;
    }

    getCurrentConsumption() {
        var requestedPower = null;

        if (this.state == true)
            // requestedPower = Math.floor(Math.random() * (this.maxWatt - this.minWatt) + this.minWatt);
            // console.log("The power i want to request is " + Math.floor(Math.random() * (this.maxWatt - this.minWatt) + this.minWatt));
            // console.log("Min and Max: " + this.minWatt + " " + this.maxWatt);
            // this.somePower = this.maxWatt;
            requestedPower = 0;
            if (this.powerOutletConnection != null) {
                //this.somePower = this.maxWatt;
                //console.log("some power: " + this.somePower);
                requestedPower = this.randomConsumption(this.minWatt, this.maxWatt);
                // requestedPower = 5;
                // console.log("The power i want to request is " + requestedPower);
                // console.log(this.name + " Making a power request!!!");
                this.powerOutletConnection.send(JSON.stringify({name: this.name, RequestedPower: requestedPower}));
                
                // this.coordinator.wait();
                // console.log(this.name + " can't be reached!!!");
            }
            // if (this.consumption != requestedPower && this.consumption == 0) {
            //     this.powerOffDevice();
            // } 
        else {
            this.consumption = 0;
        }

        return this.consumption;
    }

    publishConsumption(deviceObject) {
        var currentCon = deviceObject.getCurrentConsumption();
        deviceObject.client.publish("cmnd/" + deviceObject.topic + "/Status", JSON.stringify({ "Power": currentCon }));
        if (deviceObject.socket != null)
            deviceObject.socket.send(JSON.stringify({type: "consumption", "consumption": currentCon}));
    }

    randomConsumption(minWatt, maxWatt) {
        return Math.floor(Math.random() * (maxWatt - minWatt) + minWatt);
    }

    setSocket(deviceSocket) {
        this.socket = deviceSocket;
        this.socket.on('message', (message) => {
            var responseObject = JSON.parse(message);
            //console.log(responseObject);
            if (responseObject.type == 'command') {
                if (responseObject.Power != this.state) {
                    this.state = !this.state;
                    //console.log(this.client);
                    if (this.client != null) {
                        this.client.publish("cmnd/" + this.topic + "/RESULT", JSON.stringify({ "POWER": this.state ? "ON" : "OFF" }));
                        this.client.publish("stat/" + this.topic + "/POWER", this.state ? "ON" : "OFF");
                    }
                }
            }
        });
    }

    setPowerOutletConnection(powerOutlet) {
        this.powerOutletConnection = powerOutlet;
        // console.log("Setting powerOutlet Socket.");
        this.powerOutletConnection.on('message', (message) => {
            // console.log("poweroutlet message" + message);
            let responseObject = JSON.parse(message);
            if (responseObject.name == this.name) {
                this.consumption = responseObject.power;
                if (this.consumption == 0)
                    this.powerOffDevice();
                //console.log("Received Power is " + this.consumption);
                // this.coordinator.notify();    
            }
        });
    } 

    configure() {
        this.client.on('connect', () => {
            this.client.subscribe("cmnd/" + this.topic + "/Power");
            this.client.subscribe("cmnd/" + this.topic + "/Status");
            setInterval(this.publishConsumption, this.timeInterval, this);
        });
        this.client.on('message', (topic, message) => {
            if (topic == "cmnd/" + this.topic + "/Power") {
                var msg = message.toString();
                if (msg == "") {
                    console.log("empty message sent to topic")
                    this.client.publish("cmnd/" + this.topic + "/RESULT", JSON.stringify({ "POWER": this.state ? "ON" : "OFF" }));
                    this.client.publish("stat/" + this.topic + "/POWER", this.state ? "ON" : "OFF");
                }
                else if (msg == "TOGGLE") {
                    console.log("toggle message sent to topic")
                    this.state = !this.state;
                    this.socket.send(JSON.stringify({"type": "command","powerState": this.state}));
                    this.client.publish("cmnd/" + this.topic + "/RESULT", JSON.stringify({ "POWER": this.state ? "ON" : "OFF" }));
                    this.client.publish("stat/" + this.topic + "/POWER", this.state ? "ON" : "OFF");
                }
            }
            if (topic == "cmnd/" + this.topic + "/Status") {
                var msg = message.toString();
                if (msg == "9") {
                    publishConsumption();
                }
            }
        });
    }

    powerOnDevice() {
        // birth will
    }

    powerOffDevice() {
        // send a last will
        this.state = false;
    }
}
