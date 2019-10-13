const SolarCharger = require('./solar-charger.js');
const Battery = require('./battery.js');
const WebSocket = require('ws');

class PvSystem {
	constructor() {
		this.powerOutlet = null; // This represent a connection between the device and a battery
		this.configurePowerOutlet();
		this.Battery = Battery;
		this.SolarCharger = SolarCharger;
		// this.SolarCharger = new SolarCharger((generation) => {
		// 	this.Battery.chargeBattery(generation);	
		// });
		// this.SolarCharger = new SolarCharger(this.chargeBattery);
		this.SolarCharger.setCallback(this.chargeBattery, this.Battery);
 	}

 	getSolarCharger() {
 		return this.SolarCharger;
 	}

 	getBattery() {
 		return this.Battery;
 	}

 	chargeBattery(generation, battery) {
 		battery.chargeBattery(generation);
 	}

 	configurePowerOutlet() {
 		this.powerOutlet = new WebSocket('ws://localhost:3000');
 		
 		this.powerOutlet.on('open', () => {
			this.powerOutlet.send(JSON.stringify({type: 'pv'}));
			console.log("Openning Socket Connection...");
		});

		this.powerOutlet.on('message', (message) => {
			// console.log("this the message "+ message);
			let responseObject = JSON.parse(message);
			let requestedPower = null;
			// console.log("Received msg from socket" + responseObject.name);
			if (typeof responseObject.RequestedPower === 'undefined') {

			} else {
				// console.log("Received power request");
				requestedPower = this.Battery.dischargeBattery(responseObject.RequestedPower);
				this.powerOutlet.send(JSON.stringify({name: responseObject.name, power: requestedPower}));
			}
		});
 	}
}

module.exports = new PvSystem();