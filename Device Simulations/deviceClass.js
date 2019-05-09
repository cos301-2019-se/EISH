module.exports = class Device {
    constructor(object, client) {
        this.name = object.deviceName;
        this.topic = object.topic;
        this.minWatt = object.minWatts;
        this.maxWatt = object.maxWatt;
        this.consumption = object.consumption;
        this.state = object.state;
        this.client = client;
    }
    getCurrentConsumption() {
        if (this.state == "ON")
            this.consumption = this.randomConsumption(this.minWatt, this.maxWatt);
        else {
            this.consumption = 0;
        }
        return this.consumption;
    }
    publishConsumption(){
        var currentCon = getCurrentConsumption();
        client.publish("cmnd/" + this.topic + "/Status", { "Power": currentCon });
    };
    randomConsumption(minWatt, maxWatt){
        return Math.random() * (maxWatt - minWatt) + minWatt;
    };
    configure(client)
    {
        client.on('connect', () => {
            client.subscribe("cmnd/" + this.topic + "/Power");
            client.subscribe("cmnd/" + this.topic + "/Status");
            setInterval(publishConsumption(client), timeInterval);
        });
        client.on('message', (topic, message) => {
            if (topic == "cmnd/" + this.topic + "/Power") {
                var msg = message.toString();
                if (msg == "") {
                    client.publish("cmnd/" + this.topic + "/RESULT", { "POWER": deviceState ? "ON" : "OFF" });
                    client.publish("stat/" + this.topic + "/POWER", deviceState ? "ON" : "OFF");
                }
                else if (msg == "TOGGLE") {
                    deviceState = !deviceState;
                    client.publish("cmnd/" + this.topic + "/RESULT", { "POWER": deviceState ? "ON" : "OFF" });
                    client.publish("stat/" + this.topic + "/POWER", deviceState ? "ON" : "OFF");
                }
            }
            if (topic == "cmnd/" + this.topic + "/Status") {
                var msg = message.toString();
                if (msg == "9") {
                    publishConsumption(client);
                }
            }
        });
    }
}
//module.exports.device;
