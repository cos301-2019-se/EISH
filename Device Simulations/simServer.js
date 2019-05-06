var express = require('express')
var bodyParser = require('body-parser')
var mqtt = require('mqtt');

//inits
const app = express()
const port = 3000

//uses
app.use(bodyParser.urlencoded({ extended: true }))
app.use(bodyParser.json())


//setup
app.listen(port,()=>
    console.log(`Communication Module: listening on port ${port}!`)
)
app.get("/", function(req,res){
    return res.send("What now bitch")
})
app.post("/", function(req,res){
    console.log(req.body)
    var device = req.body
    device['device_consumption'] = randomConsumption(device['min_watt'],device['max_watt'])
    var resMsg = ('{"Consumption": "' + device['device_consumption'] + '" }')
    console.log(resMsg)
    sendToMQTTBroker(device['device_name'],resMsg)
    return res.send(resMsg)
})

function sendToMQTTBroker(device_name,device_consumption){
    var client = mqtt.connect('mqtt://127.0.0.1:1883')
    client.on('connect',() => {
        var topic = "sonoff-"+device_name+"/device-info"
        client.publish(topic,device_consumption)
    })
}

function randomConsumption(min, max){
	return Math.round(Math.random()* (max-min)+min)
}