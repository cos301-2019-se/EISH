module.exports = class Device {
    constructor(object, client) {
        this.name = object.name;
        this.topic = object.topic;
        this.minWatt = object.minWatt;
        this.maxWatt = object.maxWatt;
        this.consumption = object.consumption;
        this.state = object.state;
        this.type = object.type;
        this.client = client;
        this.timeInterval = 1000;
        this.socket = null;
    }

    getCurrentConsumption() {
        if (this.state == true)
            this.consumption = this.randomConsumption(this.minWatt, this.maxWatt);
        else {
            this.consumption = 0;
        }
        return this.consumption;
    }

    publishConsumption(deviceObject){
        var currentCon = deviceObject.getCurrentConsumption();
        deviceObject.client.publish("cmnd/" + deviceObject.topic + "/Status", JSON.stringify({ "Power": currentCon }));
        if (deviceObject.socket != null)
            deviceObject.socket.send(JSON.stringify({type: "consumption", "consumption": currentCon}));
    };

    randomConsumption(minWatt, maxWatt){
        return parseInt(Math.random() * (maxWatt - minWatt) + minWatt);
    };

    setSocket(deviceSocket) {
        this.socket = deviceSocket;
        this.socket.on('message', (message) => {
            var responseObject = JSON.parse(message);
            //console.log(responseObject);
            if (responseObject.type == 'command') {
                if (responseObject.Power != this.state){
                    this.state = !this.state;
                    console.log(this.client);
                    if (this.client != null) {
                        this.client.publish("cmnd/" + this.topic + "/RESULT", JSON.stringify({ "POWER": this.state ? "ON" : "OFF" }));
                        this.client.publish("stat/" + this.topic + "/POWER", this.state ? "ON" : "OFF");
                    }
                }
            }
        });
    }

    configure()
    {
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
}
