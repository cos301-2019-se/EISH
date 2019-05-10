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
        this.timeInterval = 30000;
    }
    getCurrentConsumption() {
        if (this.state == true)
            this.consumption = this.randomConsumption(this.minWatt, this.maxWatt);
        else {
            this.consumption = 0;
        }
        return this.consumption;
    }
    publishConsumption(){
        var currentCon = getCurrentConsumption();
        this.client.publish("cmnd/" + this.topic + "/Status", JSON.stringify({ "Power": currentCon }));
    };
    randomConsumption(minWatt, maxWatt){
        return Math.random() * (maxWatt - minWatt) + minWatt;
    };
    configure()
    {
        this.client.on('connect', () => {
            this.client.subscribe("cmnd/" + this.topic + "/Power");
            this.client.subscribe("cmnd/" + this.topic + "/Status");
            setInterval(this.publishConsumption, this.timeInterval);
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
