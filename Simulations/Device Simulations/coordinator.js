class Coordinator {
	constructor() {
		this.waiting = false;
	}

	executeAndWait(callback, timeout_milliseconds) {
		if (arguments.length == 1) {
			callback();
			this.waiting = true;

			while (this.waiting) {}

			return;
		}

		let startTime = (new Date()).getTime();
		let nowTime = startTime;
		
		callback();
		this.waiting = true;

		while (this.waiting && ((nowTime - startTime) <= timeout_milliseconds)) {
			nowTime = (new Date()).getTime();
		}

		if (this.waiting) {
			throw new Error('Error: ran out of time :(');
		}
	}

	wait(timeout_milliseconds) {
		if (arguments.length == 0) {
			this.waiting = true;
			while (this.waiting) {}
			return;
		}

		let startTime = (new Date()).getTime();
		let nowTime = startTime;
		
		this.waiting = true;

		while (this.waiting && ((nowTime - startTime) <= timeout_milliseconds)) {
			nowTime = (new Date()).getTime();
		}

		if (this.waiting) {
			throw new Error('Error: ran out of time :(');
		}	
	}

	notify() {
		this.waiting = false;
	}
}

module.exports = new Coordinator();