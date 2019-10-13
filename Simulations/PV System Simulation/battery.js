const BatteryPowerStates = {
	FULL: 'POWERSTATE_FULL',
	NORMAL: 'POWERSTATE_NORMAL',
	LOW: 'POWERSTATE_LOW',
	CRITICALLY_LOW: 'POWERSTATE_CRITICALLYLOW',
	EMPTY: 'POWERSTATE_EMPTY'
};

const BatteryChargingStates = {
	CHARGING: 'CHARGINGSTATE_CHARGING',
	DISCHARGING: 'CHARGINGSTATE_DISCHARGING',
	IDLE: 'CHARGINGSTATE_IDLE'
};

const FULL_POWER = 100;
const NORMAL_POWER = 99;
const LOW_POWER = 15;
const CRITICAL_POWER = 5;
const DEAD = 0;


class HomeBattery {
	constructor() {
		this.siteId = 0;
		this.storageCapacity = 13500;
		this.currentPower = 13500;
		this.powerState = BatteryPowerStates.NORMAL;
		this.chargingState = BatteryChargingStates.IDLE; 

		// Variables for connection to Device Simulations
		this.batteryToConsumers = 0;
	}

	getBatteryState() {
		return {storageCapacity: this.storageCapacity,
				currentPower: this.currentPower,
				powerState: this.powerState,
				chargingState: this.chargingState,
				powerPercentage: this.getStoragePercentage(),
				timestamp: new Date()};
	}

	/** 
	 * Add additional methods to coordinate with a charging from SolarCharger
	 * also add methods provide power to the simulations and discharge the 
	 * battery.
	 **/
	 getStoragePercentage() {
	 	return Math.floor((this.currentPower/this.storageCapacity) * 100);
	 }

	 chargeBattery(power) {
	 	if (this.storageCapacity >= (power + this.currentPower)) {
	 		this.chargingState = BatteryChargingStates.CHARGING;
	 		this.currentPower += power;
	 		if (this.getStoragePercentage() > LOW_POWER)
	 			this.powerState = BatteryPowerStates.NORMAL;
	 	} else if (this.storageCapacity < (power + this.currentPower) 
	 				&& this.storageCapacity != this.currentPower) {
	 		this.currentPower = this.storageCapacity;
	 		this.chargingState = BatteryChargingStates.IDLE;
	 	}

	 	if (this.currentPower == this.storageCapacity) {
	 		this.chargingState = BatteryChargingStates.IDLE;
	 		this.powerState = BatteryPowerStates.FULL;
	 	}

	 	if (this.getStoragePercentage() == FULL_POWER) {
	 		this.powerState = BatteryPowerStates.FULL;
	 	}

	 	if (this.getStoragePercentage() <= NORMAL_POWER) {
	 		this.powerState = BatteryPowerStates.NORMAL;
	 	}

	 	if (this.getStoragePercentage() <= LOW_POWER) {
	 		this.powerState = BatteryPowerStates.LOW;
	 	}

	 	if (this.getStoragePercentage() <= CRITICAL_POWER) {
	 		this.powerState = BatteryPowerStates.CRITICALLY_LOW;
	 	}

	 	if (this.getStoragePercentage() == DEAD) {
	 		this.powerState = BatteryPowerStates.EMPTY;
	 		// this.chargingState = BatteryChargingStates.IDLE;
	 	}
	 }

	 stopChargingBattery() {
	 	this.chargingState = BatteryChargingStates.IDLE;
	 }

	 dischargeBattery(power) {
	 	let outputPower = 0;

	 	if ((this.currentPower - power) >= 0) {
	 		this.chargingState = BatteryChargingStates.DISCHARGING;
	 		this.currentPower -= power;
	 		outputPower = power;
	 	}

	 	if (this.getStoragePercentage() <= LOW_POWER) {
	 		this.powerState = BatteryPowerStates.LOW;
	 	}

	 	if (this.getStoragePercentage() <= CRITICAL_POWER) {
	 		this.powerState = BatteryPowerStates.CRITICALLY_LOW;
	 	}

	 	if (this.getStoragePercentage() == DEAD) {
	 		this.powerState = BatteryPowerStates.EMPTY;
	 		this.chargingState = BatteryChargingStates.IDLE;
	 	}

	 	return outputPower;
	 }
}

module.exports = new HomeBattery();