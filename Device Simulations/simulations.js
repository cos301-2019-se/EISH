// Variable Defenitions
var openedModal = null;

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

var buttonCreate = document.getElementById("createDevice");

buttonCreate.addEventListener('click', () => {
	sendDevice();
});

var xhr = new XMLHttpRequest();

function sendDevice(){
	var info = null;
    
	var deviceName = document.getElementById('devName');
	var devPublish = document.getElementById('devPubTopic');
	var devSubscribe = document.getElementById('devSubTopic');
	var devMinWatt = document.getElementById('devName');
	var devMaxWatt = document.getElementById('devName');
	info = {device_name:deviceName.value,
			device_publish: devPublish.value,
			device_subscribe: devSubscribe.value,
			device_minWatt: devMinWatt.value,
			device_maxWatt: devMaxWatt.value};
	var myObj = JSON.stringify(info);

	xhr.open('POST', 'http://localhost:3000');
	xhr.setRequestHeader('Content-Type', 'application/json');
	xhr.onreadystatechange = function() {
    	if (xhr.status == 200) {
        	alert('Device received ' + xhr.responseText);
    	}
   		else if (xhr.status !== 200) {
        	alert('Request failed.  Returned status of ' + xhr.status);
    	}
	};
	xhr.send(myObj);
}


function main() {
	var controls = [document.getElementById('crtSolarBtn'), 
					document.getElementById('crtBatteryBtn'), 
					document.getElementById('crtDeviceBtn'),
					document.getElementById('closeDevice'),
					document.getElementById('closeSolar'),
					document.getElementById('closeBattery'),
					document.getElementById('closeConfig')];

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
}

main();