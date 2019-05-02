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