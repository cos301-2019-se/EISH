import { Component, OnInit } from '@angular/core';
import { ConsumptionService } from 'src/app/services/consumption/consumption.service';

@Component({
  selector: 'app-consumption',
  templateUrl: './consumption.component.html',
  styleUrls: ['./consumption.component.css']
})
export class ConsumptionComponent implements OnInit {

  devices = ["LightBulb", "Laptop Charger"];
  selectedDevice = "Home";
  selectedRange = "Last Hour";
  custom = false;

  constructor(private consumptionService: ConsumptionService) { }

  ngOnInit() {
    this.getDevices();
  }

  getDevices() {
    this.consumptionService.getAllDevices().subscribe(
      (res) => {
        res.forEach((device) => {
          this.devices.push(device.deviceName);
        });
      }
    );
  }

  selectDevice(dropDownValue) {
    this.selectedDevice = dropDownValue;
    console.log("Selected Device: " + this.selectedDevice);
  }

  isCustom(dropDownValue) {
    this.selectedRange = dropDownValue;
    this.custom = dropDownValue == "Custom";
    console.log("Selected Range: " + this.selectedRange);
  }

}
