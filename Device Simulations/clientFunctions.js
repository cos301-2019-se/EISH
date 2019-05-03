var mqtt = require("mqtt")
var client = mqtt.connect("mqtt://127.0.0.1:1883")

//variables
var deviceState = false;
var currentConsumption =0;
var timeInterval = 30000;
var minWatt = 50
var maxWatt = 70

//functions
client.on('connect',()=>{
	client.subscribe("cmnd/sonoff-fridge/Power");
	client.subscribe('cmnd/sonoff-fridge/Status');
	setInterval(publishConsumption(client),timeInterval);
})

client.on('message',(topic, message) => 
{
	if(topic == "cmnd/sonoff-fridge/Power")
	{
		var msg = message.toString()
		if(msg =="")
		{
			client.publish("cmnd/sonoff-fridge/RESULT", {"POWER":"OFF"})
			client.publish("stat/sonoff-switch/POWER" ,"OFF")
		}
		else if(msg =="TOGGLE")
		{
			deviceState = !deviceState
			client.publish
		}
	}

	if(topic == "cmnd/sonoff-fridge/Status"){
		var msg = message.toString()
		if(msg == "8")
		{
			publishConsumption(client);
		}
	}

	
});

function publishConsumption(client)
{
	var currentCon = getCurrentConsumption()
	client.publish('cmnd/sonoff-fridge/Status',{"Power":currentCon})

}

function publishState(client)
{
	client.publish('someTopic',deviceState)

}

function getCurrentConsumption()
{
	if(deviceState == true)
		currentConsumption = randomConsumption(minWatt,maxWatt);
	else{
		currentConsumption = 0;
	}

	return currentConsumption
}

function randomConsumption(min, max)
{
	return Math.random()* (max-min)+min
}
module.exports.publishConsumption;
module.exports.randomConsumption;

