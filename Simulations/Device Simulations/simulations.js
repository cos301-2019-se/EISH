// Variable Defenitions
var openedModal = null;
var availableRows = [];
var devices = [];

/* Function Defenitions Section */
function openModal(modalClass) {
	var backModal = document.getElementsByClassName('modal')[0];
	var currentModal = document.getElementsByClassName(modalClass)[0];

	openedModal = modalClass;
	backModal.style.display = 'block';
	currentModal.style.display = 'block';
}

function closeModal() {
	if (openedModal === null) return;

	var backModal = document.getElementsByClassName('modal')[0];
	var currentModal = document.getElementsByClassName(openedModal)[0];

	currentModal.style.display = 'none';
	backModal.style.display = 'none';		
}

function addRow() {
	var rRank = availableRows.length;
	var rName = 'deviceRow' + availableRows.length;
	var rNumberOfChildren = 0;
	availableRows.push({rank: rRank, name: rName, numberOfChildren: rNumberOfChildren});

	var workspace = document.getElementById('workspace');
	var newRow = document.createElement('div');
	newRow.id = rName;
	newRow.className = 'device-row';
	workspace.appendChild(newRow);
}

function deviceIcon(deviceType) {
	return 'img/'+deviceType+'.png';
}

function getAvailableRow() {
	for (var i = 0; i < availableRows.length; i++)
		if (availableRows[i].numberOfChildren < 6)
			return availableRows[i];

	addRow();
	return availableRows[availableRows.length-1];		
}

function addDeviceBox(type, deviceInfo) {
	var currentRow = getAvailableRow();
	var boxRow = document.getElementById(currentRow.name);
	
	var newBox = document.createElement('div');
	newBox.className = 'device-box';
	
	var powerStateBox = document.createElement('div');
	powerStateBox.className = 'device-name';
	
	var powerStateLabel = document.createElement('p');
	powerStateLabel.id = 'powerStateLabel' + devices.length;
	powerStateLabel.innerHTML = deviceInfo.deviceState;

	powerStateBox.appendChild(powerStateLabel);

	var newDevImg = document.createElement('div');
	newDevImg.className = 'device-image';
	
	var newImg = document.createElement('img');
	newImg.src = deviceIcon(deviceInfo.deviceType);
	newImg.height = '100';
	newImg.width = '100';

	newDevImg.appendChild(newImg);
	
	var newDevName = document.createElement('div');
	newDevName.className = 'device-name';
	
	var newName = document.createElement('p');
	newName.innerHTML = deviceInfo.deviceName;

	newDevName.appendChild(newName);

	newBox.appendChild(powerStateBox);
	newBox.appendChild(newDevImg);
	newBox.appendChild(newDevName);

	//Extra things depending on the type
	if (type == "create solar power") {

	} else if (type == "create battery") {

	} else if (type == "create device") {
		var powerLabel = document.createElement('div');
		powerLabel.className = 'device-name';
	
		var powerUsage = document.createElement('p');
		powerUsage.innerHTML = 'Power Usage';

		var powerUse = document.createElement('p');
		powerUse.id = "powerUsage" + devices.length; //remember to update with websocket
		powerUse.innerHTML = '0w';

		powerLabel.appendChild(powerUsage);
		powerLabel.appendChild(powerUse);

		var powerState = document.createElement('div');
		powerState.className = 'device-state';
		powerState.id = 'powerState';

		var powerButton = document.createElement('div');
		powerButton.className = 'power-button';
		powerButton.id = 'powerButton' + devices.length;

		var device_socket = new WebSocket('ws://localhost:3000');
		device_socket.addEventListener('open', () => {
			device_socket.send(JSON.stringify({type: 'name', deviceName: deviceInfo.deviceName}));
		});
		
		addToDevices({deviceName: deviceInfo.deviceName, deviceState: (deviceInfo.deviceState == 'ON'), deviceUsage: powerUse, powerLabel: powerStateLabel, powerBtn : powerButton, deviceSocket: device_socket});

		var powerIcon = document.createElement('img');
		powerIcon.src = 'img/power-button.png';
		powerIcon.height = '30';
		powerIcon.width = '30';

		powerButton.appendChild(powerIcon);
		powerState.appendChild(powerButton);

		newBox.appendChild(powerLabel);
		newBox.appendChild(powerState);
	} 

	boxRow.appendChild(newBox);
	currentRow.numberOfChildren++;
}

function addToDevices(deviceObject) {
	devices.push(deviceObject);
	
	deviceObject.deviceSocket.addEventListener('message', (event) => {
		var responseObject = JSON.parse(event.data);
		if (responseObject.type == 'consumption' && deviceObject.deviceState)
			deviceObject.deviceUsage.innerHTML = responseObject.consumption + 'w';
		if (responseObject.type == 'command')
			if (responseObject.powerState != deviceObject.deviceState) //this is assuming that power is sent as a boolean and not ON and OFF
				toggleDevice(deviceObject);	
	});

	deviceObject.powerBtn.addEventListener('click', () => {
		toggleDevice(deviceObject); 
		// send a message to the server
		deviceObject.deviceSocket.send(JSON.stringify({type: 'command',Power : deviceObject.deviceState}));
	}); 
}

function toggleDevice(deviceObject) {
	deviceObject.deviceState = !deviceObject.deviceState;
	deviceObject.powerLabel.innerHTML = deviceObject.deviceState?'ON':'OFF';
	deviceObject.powerLabel.style = 'color: ' + (deviceObject.deviceState?'red':'#565656') + ';';
	if (!deviceObject.deviceState) deviceObject.deviceUsage.innerHTML = '0w'; 
}

function sendDeviceData(deviceInfo) {
	var ajaxRequest = requestObject();
	var urlEndPoint = 'http://localhost:3000/add/device';

	ajaxRequest.onreadystatechange = () => {
		if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
			//console.log(ajaxRequest.responseText);
			//alert(ajaxRequest.responseText);
			addDeviceBox("create device", {deviceName: deviceInfo.deviceName, deviceType: deviceInfo.deviceType, deviceState: (deviceInfo.deviceState?'ON':'OFF')});		
		}
	};

	ajaxRequest.open('POST', urlEndPoint, true);
	ajaxRequest.setRequestHeader('Content-Type','application/json');
	ajaxRequest.send(JSON.stringify({name: deviceInfo.deviceName, type: deviceInfo.deviceType, state: (deviceInfo.deviceState == 'ON'?true:false), topic: deviceInfo.topic, minWatt: Number.parseInt(deviceInfo.minWatt), maxWatt: Number.parseInt(deviceInfo.maxWatt), consumption: 0}));
}

function requestObject() {
	var XMLRequestObject;

	try {
		XMLRequestObject = new XMLHttpRequest();
	} catch(e) {
		try {
			XMLRequestObject = new ActiveXObject("Msxml2.XMLHTTP");
		} catch(e) {
			try {
				XMLRequestObject = new ActiveXObject("Microsoft.XMLHTTP");
			} catch(e) {
				return null;
			}
		}
	}

	return XMLRequestObject;
}

function addDevice() {
	var device_name = document.getElementsByName('deviceName')[0].value;
	var topic_ = document.getElementsByName('topic')[0].value;
	var min_watt = document.getElementsByName('minWatt')[0].value;
	var max_watt = document.getElementsByName('maxWatt')[0].value;
	var device_type = document.getElementsByName('deviceType')[0].value;
	var device_state = document.getElementsByName('deviceState')[0].value;

	//console.log(device_name); 
	//console.log({deviceName: device_name, deviceType: device_type, deviceState: device_state});
	//addDeviceBox("create device", {deviceName: device_name, deviceType: device_type, deviceState: device_state});
	sendDeviceData({deviceName: device_name, deviceType: device_type, deviceState: (device_state == 'ON'?true:false), topic: topic_, minWatt: Number.parseInt(min_watt), maxWatt: Number.parseInt(max_watt), consumption: 0});
} 

function addBattery() {

}

function addSolarSystem() {

}


function loadDevices() {
	var ajaxRequest = requestObject();
	var urlEndPoint = 'http://localhost:3000/view/devices';

	ajaxRequest.onreadystatechange = () => {
		if (ajaxRequest.readyState == 4 && ajaxRequest.status == 200) {
			var responseObject = JSON.parse(ajaxRequest.responseText);
			//loop through this object array and create device box
			//console.log(responseObject);
			for (var i=0; i < responseObject.length; i++) {
				addDeviceBox("create device", {deviceName: responseObject[i].name, deviceType: responseObject[i].type, deviceState: (responseObject[i].state?'ON':'OFF')});			
			}
		}
	};

	//ajaxRequest.setRequestHeader('Content-Type','application/json');
	ajaxRequest.open('GET', urlEndPoint, true);
	ajaxRequest.send();
}

function main() {
	var controls = [document.getElementById('crtSolarBtn'), 
					document.getElementById('crtBatteryBtn'), 
					document.getElementById('crtDeviceBtn'),
					document.getElementById('closeDevice'),
					document.getElementById('closeSolar'),
					document.getElementById('closeBattery'),
					document.getElementById('closeConfig'),
					document.getElementById('createDeviceButton')];

	loadDevices();

	controls[0].addEventListener('click', ()=> {
		openModal('form-solar');
	}, false);
	controls[1].addEventListener('click', ()=> {
		openModal('form-battery');
	}, false);
	controls[2].addEventListener('click', ()=> {
		openModal('form-device');
	}, false);

	for (var i=3; i < controls.length; i++)
		controls[i].addEventListener('click', ()=> {
			closeModal();
		}, false);

	controls[7].addEventListener('click', () => {
		addDevice();
	});
	//add event listener for create buttons in forms	
}

main();