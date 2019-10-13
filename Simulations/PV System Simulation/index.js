const express = require('express');
const PvSystem = require('./pv-system.js');

const app = express();
process.env.PORT = 3001;

app.get('/', (req, res) => {
	res.send("Endpoint Works!");
});

app.get('/v2/installations/:uid/SolarCharger/all', (req, res) => {
	let siteId = req.params.uid;
	let SolarCharger = PvSystem.getSolarCharger();

	if (siteId != SolarCharger.siteId) {
		res.status(400);
		res.set('Content-Type', 'application/json');
		res.send({message: ('Error: Supplied incorrect site id: ' + siteId)});	
	}

	res.send({SolarData: SolarCharger.getHistoricSolarData()});
	// res.send("The site id is "+siteId);
});

app.get('/v2/installations/:uid/SolarCharger/current', (req, res) => {
	let siteId = req.params.uid;
	let SolarCharger = PvSystem.getSolarCharger();

	if (siteId != SolarCharger.siteId) {
		res.status(400);
		res.set('Content-Type', 'application/json');
		res.send({message: ('Error: Supplied incorrect site id: ' + siteId)});	
	}

	res.send({SolarData: SolarCharger.getCurrentSolarData()});
	// res.send("The site id is "+siteId);
});

app.get('/v2/installations/:uid/Battery', (req, res) => {
	let siteId = req.params.uid;
	let Battery = PvSystem.getBattery();
	
	if (siteId != Battery.siteId) {
		res.status(400);
		res.set('Content-Type', 'application/json');
		res.send({message: ('Error: Supplied incorrect site id: ' + siteId)});	
	}

	return res.send(Battery.getBatteryState());
	// res.send("The site id is "+siteId);
});

app.listen(process.env.PORT, () => {
	console.log(`PV Simulation is listening on port ${process.env.PORT}!`)
});

