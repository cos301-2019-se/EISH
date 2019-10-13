class SolarCharger {
	constructor() {
		this.siteId = 0;
		this.currentSolarData = {timestamp: (new Date()),
								generation: (Math.floor(Math.random() * (600 - 100)) + 100)};
		this.historicSolarData = [];
		this.callback = null;
		this.argument = null;
		setInterval(() => {
			// Do some complex computation to aarive at some generation
			// currentSolarData.generation = Math.floor(Math.random() * (max - min)) + min;
			this.currentSolarData.generation = Math.floor(Math.random() * (600 - 100)) + 100;
			this.currentSolarData.timestamp = new Date();
			this.historicSolarData.push({timestamp: this.currentSolarData.timestamp,
										generation: this.currentSolarData.generation});
			this.callback(this.currentSolarData.generation, this.argument);	
		}, 20000);
	}

	getCurrentSolarData() {
		return this.currentSolarData;
	}

	getHistoricSolarData() {
		return this.historicSolarData;
	}

	setCallback(callback, argument) {
		this.callback = callback;
		this.argument = argument;
	}
}

module.exports = new SolarCharger();
