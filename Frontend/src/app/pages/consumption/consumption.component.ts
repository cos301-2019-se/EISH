import { Component, OnInit, ViewChild } from '@angular/core';
import { ConsumptionService } from 'src/app/services/consumption/consumption.service';
import { ConsumptionChartComponent } from './consumption-chart/consumption-chart.component';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message } from '@stomp/stompjs';

@Component({
  selector: 'app-consumption',
  templateUrl: './consumption.component.html',
  styleUrls: ['./consumption.component.css'],
})
export class ConsumptionComponent implements OnInit {
  @ViewChild(ConsumptionChartComponent, {static: true}) consumptionChart: ConsumptionChartComponent;

  deviceIds = [];
  devices = [];
  deviceTopics = [];
  startTime = null;
  endTime = null;
  selectedDevice = 'Home';
  selectedRange = 'lasthour';
  consumptionType = 'home';
  custom = false;
  topicSubscription = null;

  constructor(private consumptionService: ConsumptionService, private rxStompService: RxStompService) {
    this.startTime = this.toDateString(new Date());
    this.endTime = this.toDateString(new Date());
  }

  ngOnInit() {
    this.getDevices();
    this.consumptionChart.setHeading('Home Consumption');
  }

  private toDateString(date: Date): string {
    return (date.getFullYear().toString() + '-'
       + ('0' + (date.getMonth() + 1)).slice(-2) + '-'
       + ('0' + (date.getDate())).slice(-2))
       + 'T' + date.toTimeString().slice(0, 5);
  }

  private addSeconds(dateTime: string) {
    const DateString = dateTime.replace(/T/g, ' ') + ':00';
    console.log(DateString);
    return DateString;
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

  isDatesValid(): boolean {
    return false;
  }

  isCustomValid(): boolean {
    return this.selectedDevice !== '' && this.selectedRange === 'Custom' && this.startTime != null && this.endTime != null;
  }

  getDeviceConsumption(deviceId, startTimestamp, endTimestamp) {
    this.consumptionService.getCustomDeviceConsumption(deviceId, startTimestamp, endTimestamp).subscribe(
      (res) => {
        this.consumptionChart.addBulkData(res);
      }
    );
  }

  getHomeConsumption(startTimestamp, endTimestamp) {
    this.consumptionService.getCustomHomeConsumption(startTimestamp, endTimestamp).subscribe(
      (res) => {
        this.consumptionChart.addBulkData(res);
      }
    );
  }

  getSpecialDeviceConsumption(deviceId, range) {
    this.consumptionService.getSpecialDeviceConsumption(deviceId, range).subscribe(
      (res) => {
        this.consumptionChart.addBulkData(res);
      }
    );
  }

  getSpecialHomeConsumption(range) {
    this.consumptionService.getSpecialHomeConsumption(range).subscribe(
      (res) => {
        this.consumptionChart.addBulkData(res);
      }
    );
  }

  getCurrentDeviceConsumption(deviceTopic) {
    if (this.topicSubscription != null) {
      this.topicSubscription.unsubscribe();
    }

    this.consumptionChart.clearChart();

    this.topicSubscription = this.rxStompService.watch('/device/' + deviceTopic + '/consumption').subscribe((message: Message) => {
      const consumptionData = JSON.parse(message.body);
        // add data point to chart
      this.consumptionChart.addDataPoint(consumptionData);
      this.consumptionChart.updateChart();
    });
  }

  getCurrentHomeConsumption() {
    if (this.topicSubscription != null) {
      this.topicSubscription.unsubscribe();
    }

    this.consumptionChart.clearChart();

    this.topicSubscription = this.rxStompService.watch('/home/consumption').subscribe((message: Message) => {
      const consumptionData = JSON.parse(message.body);
        // add data point to chart
      console.log(consumptionData);
      this.consumptionChart.addDataPoint(consumptionData);
      this.consumptionChart.updateChart();
    });
  }

  selectDevice(dropDownValue) {
    console.log(dropDownValue);
    this.selectedDevice = dropDownValue;
    this.consumptionType = (dropDownValue === 'Home') ? 'home' : 'device';
    if (!this.custom) {
      this.updateSpecial();
    } else {
      this.updateCustom();
    }
  }

  selectRange(dropDownValue) {
    this.selectedRange = dropDownValue;
    this.custom = (dropDownValue === 'Custom');
    if (!this.custom) {
      this.updateSpecial();
    } else {
      this.updateCustom();
    }
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
    for (let i = 0; i < this.devices.length; i++) {
      if (this.devices[i] === deviceName) {
        return this.deviceIds[i];
      }
    }
  }

  getDeviceTopic(deviceName) {
    for (let i = 0; i < this.devices.length; i++) {
      if (this.devices[i] === deviceName) {
        console.log(deviceName);
        return this.deviceTopics[i];
      }
    }
  }

  updateCustom() {
    if (this.topicSubscription != null) {
      this.topicSubscription.unsubscribe();
    }
    if (this.selectedDevice === 'Home') {
      this.consumptionChart.setHeading('Home Consumption');
      this.consumptionChart.setChartHeading('Consumption');
      this.consumptionChart.setDatasetHeading('Home Consumption');
      this.consumptionChart.clearChart();
      if (this.isCustomValid()) {
        this.getHomeConsumption(this.addSeconds(this.startTime), this.addSeconds(this.endTime));
      }
    } else {
      this.consumptionChart.setHeading('Device Consumption');
      this.consumptionChart.setChartHeading(this.selectedDevice + ' Consumption');
      this.consumptionChart.setDatasetHeading('Device Consumption');
      this.consumptionChart.clearChart();
      if (this.isCustomValid()) {
        this.getDeviceConsumption(this.getDeviceId(this.selectedDevice), this.addSeconds(this.startTime), this.addSeconds(this.endTime));
      }
    }
  }

  updateSpecial() {
    if (this.selectedRange === 'Current') {
      if (this.consumptionType === 'home') {
        this.consumptionChart.setHeading('Home Consumption');
        this.consumptionChart.setChartHeading('Consumption');
        this.consumptionChart.setDatasetHeading('Home Consumption');
        this.consumptionChart.clearChart();
        this.getCurrentHomeConsumption();
      }

      if (this.consumptionType === 'device') {
        this.consumptionChart.setHeading('Device Consumption');
        this.consumptionChart.setChartHeading(this.selectedDevice + ' Consumption');
        this.consumptionChart.setDatasetHeading('Device Consumption');
        this.consumptionChart.clearChart();
        this.getCurrentDeviceConsumption(this.getDeviceTopic(this.selectedDevice));
      }
    } else {
      if (this.topicSubscription != null) {
        this.topicSubscription.unsubscribe();
      }

      if (this.consumptionType === 'home') {
        this.consumptionChart.setHeading('Home Consumption');
        this.consumptionChart.setChartHeading('Consumption');
        this.consumptionChart.setDatasetHeading('Home Consumption');
        this.consumptionChart.clearChart();
        this.getSpecialHomeConsumption(this.selectedRange);
      }

      if (this.consumptionType === 'device') {
        this.consumptionChart.setHeading('Device Consumption');
        this.consumptionChart.setChartHeading(this.selectedDevice + ' Consumption');
        this.consumptionChart.setDatasetHeading('Device Consumption');
        this.consumptionChart.clearChart();
        this.getSpecialDeviceConsumption(this.getDeviceId(this.selectedDevice), this.selectedRange);
      }
    }
  }

}
