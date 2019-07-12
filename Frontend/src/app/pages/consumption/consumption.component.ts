import { Component, OnInit, ViewChild } from '@angular/core';
import { webSocket } from "rxjs/webSocket";
import { ConsumptionService } from 'src/app/services/consumption/consumption.service';
import { ConsumptionChartComponent } from './consumption-chart/consumption-chart.component';

@Component({
  selector: 'app-consumption',
  templateUrl: './consumption.component.html',
  styleUrls: ['./consumption.component.css'], 
})
export class ConsumptionComponent implements OnInit {
  @ViewChild(ConsumptionChartComponent, {static:true}) consumptionChart: ConsumptionChartComponent; 

  deviceIds = [];
  devices = [];
  deviceTopics = [];
  startTime = null;
  endTime = null;
  selectedDevice = "Home";
  selectedRange = "Last Hour";
  custom = false;
  socketUrl = "ws://192.168.8.102/consumption";

  constructor(private consumptionService: ConsumptionService) { 
    this.startTime = this.toDateString(new Date());
    this.endTime = this.toDateString(new Date());
  }

  ngOnInit() {
    this.getDevices();
    this.consumptionChart.setHeading("Home Consumption");
  }

  private toDateString(date: Date): string {
    return (date.getFullYear().toString() + '-' 
       + ("0" + (date.getMonth() + 1)).slice(-2) + '-' 
       + ("0" + (date.getDate())).slice(-2))
       + 'T' + date.toTimeString().slice(0,5);
  }

  private addSeconds(dateTime: String) {
    var dateString = dateTime.replace(/T/g, " ") + ':00';  
    console.log(dateString);
    return dateString;
  }

  getDevices() {
    this.consumptionService.getAllDevices().subscribe(
      (res) => {
        res.forEach((device) => {
          this.devices.push(device.deviceName);
          this.deviceTopics.push(device.deviceTopic);
          this.deviceIds.push(device.deviceId);
        });
      }
    );
  }

  isDatesValid(): Boolean {
    return false;
  }

  isCustomValid(): Boolean {
    return this.selectedDevice != "" && this.selectedRange == "Custom" && this.startTime != null && this.endTime != null;
  }

  getDeviceConsumption(deviceId, startTimestamp, endTimestamp) {
    this.consumptionService.getCustomDeviceConsumption(deviceId, startTimestamp, endTimestamp).subscribe(
      (res) => {
        this.consumptionChart.addBulkData(res);
      }
    );
  }

  getCurrentDeviceConsumption(deviceId, specialRange) {
    this.consumptionService.getSpecialDeviceConsumption(deviceId, specialRange).subscribe(
      (res) => {
        this.consumptionChart.addBulkData(res);
        const Socket = webSocket(this.socketUrl);
        Socket.subscribe(
          (msg) => {
            console.log(msg);
          }
        );
      }
    );
  }

  selectDevice(dropDownValue) {
    this.selectedDevice = dropDownValue;
    if (!this.custom)
      this.updateSpecial();
    else
      this.updateCustom();
  }

  selectRange(dropDownValue) {
    this.selectedRange = dropDownValue;
    this.custom = dropDownValue == "Custom";
    if (!this.custom)
      this.updateSpecial();
    else
      this.updateCustom();  
  }

  setStartDateTime(dateTimeValue) {
    this.startTime = dateTimeValue;
    this.updateCustom();
  }

  setEndDateTime(dateTimeValue) {
    this.endTime = dateTimeValue;
    this.updateCustom();
  }

  getDeviceId(deviceName) {
    for (var i=0; i < this.devices.length; i++) {
      if (this.devices[i] == deviceName)
        return this.deviceIds[i];
    }
  }

  getDeviceTopic(deviceName) {
    for (var i=0; i < this.devices.length; i++) {
      if (this.devices[i] == deviceName)
        return this.deviceTopics[i];
    }
  }

  updateCustom() {
    if (this.selectedDevice == "Home") {
      this.consumptionChart.setHeading("Home Consumption");
      this.consumptionChart.setChartHeading("Consumption");
      return;
    } else {
      this.consumptionChart.setHeading("Device Consumption");
      this.consumptionChart.setChartHeading(this.selectedDevice + " Consumption");
      if (this.isCustomValid())
        this.getDeviceConsumption(this.getDeviceId(this.selectedDevice), this.addSeconds(this.startTime), this.addSeconds(this.endTime));
    }
      
    //this.consumptionChart.setHeading("This is working Kokza!");
  }

  updateSpecial() {

  }

}
