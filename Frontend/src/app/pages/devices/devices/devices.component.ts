import { Component, OnInit } from '@angular/core';
import { ConsumptionService } from 'src/app/services/consumption/consumption.service';
import { DeviceService } from 'src/app/services/devices/device.service';
import {map, startWith} from 'rxjs/operators';
import { FormControl } from '@angular/forms';
import { Observable } from 'rxjs';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
})
export class DevicesComponent implements OnInit {
// for search  bar
  panelOpenState = false;
  userDeviceName = new FormControl();
  filteredOptions: Observable<string[]>;
  deviceNames: string[];
  deviceArray: Devices[] = [
    {deviceId: 1, deviceName: 'Sony TV', deviceConsumption: 30, devicePriority:'PRIORITY_MUSTHAVE'},
    {deviceId: 2, deviceName: 'Samsung Fridge', deviceConsumption: 60,devicePriority:'PRIORITY_MUSTHAVE'},
    {deviceId: 3, deviceName: 'Kettle', deviceConsumption: 12, devicePriority:'PRIORITY_NEUTRAL'},
    {deviceId: 4, deviceName: 'Sony Home Theatre', deviceConsumption: 8, devicePriority:'PRIORITY_NICETOHAVE'},
    {deviceId: 5, deviceName: 'Sony Playstation', deviceConsumption: 15,devicePriority:'PRIORITY_NICETOHAVE'},
  ];
  deviceList: any[];
  deviceResult: any[];
  consumptionArray: any[];
  lastHour: any;

  constructor(private consumptionService: ConsumptionService, private deviceService: DeviceService) { }

  ngOnInit() {

      this.deviceService.getDeviceJSONArray().pipe(
        map( response => {
            this.deviceList =  response,
            JSON.stringify(this.deviceList);
            this.deviceResult = this.deviceList;
            console.log(this.deviceList);

            let devices: string[];
            devices = [];
            for (let index = 0; index < this.deviceList.length; index++) {
                devices[index] = this.deviceList[index].deviceName;
              }
            this.deviceNames = devices;
            console.log(this.deviceNames);
            this.filteredOptions = this.userDeviceName.valueChanges
              .pipe(
                startWith(' '),
                map(value => this._filter(value))
  
              );

          })
        ).subscribe();

      this.consumptionService.getJSONConsumption().pipe(
          map( response => {
              this.consumptionArray =  response,
              JSON.stringify(this.consumptionArray);
              console.log(this.consumptionArray);
              this.progressBarAgregation();
            })
          ).subscribe();
      // this.progressBarAgregation();
  }

  sortByPriority(value: string){
    console.log('Sorting now')
    console.log(value);
    const option = value.toLowerCase();
    this.deviceResult = null;
    this.deviceResult = [];
    let arrayIndex = 0;
    for (let index = 0; index < this.deviceList.length; index++) {
        if (this.deviceList[index].devicePriorityType.toLowerCase().includes(option) ) {
          console.log('device result: ' + this.deviceResult);
          this.deviceResult[arrayIndex] = this.deviceList[index];
          arrayIndex++;
        }
      }
    JSON.stringify(this.deviceResult);
    console.log(this.deviceResult);
    return;
  }
  /**
   *
   */
  progressBarAgregation() {
    // console.log('progress: '+ JSON.stringify(this.consumptionArray))
    let total = 0;
    // tslint:disable-next-line: prefer-for-of
    for (let index = 0; index < this.consumptionArray.length; index++) {
      total += this.consumptionArray[index].deviceConsumption;
    }

    const deviceIds: any[] = [];
    // tslint:disable-next-line: prefer-for-of
    for (let index = 0; index < this.consumptionArray.length; index++) {
     if (deviceIds.indexOf(this.consumptionArray[index].deviceConsumptionId.deviceId) === -1) {
        deviceIds.push(this.consumptionArray[index].deviceConsumptionId.deviceId);
      }
    }

    const consumptions: any[] = [];
    let sum = 0;
    let deviceName: string;
    deviceIds.forEach(element => {
      sum = 0;
      // tslint:disable-next-line: prefer-for-of
      for (let index = 0; index < this.consumptionArray.length; index++) {
        if (this.consumptionArray[index].deviceConsumptionId.deviceId === element) {
          sum += this.consumptionArray[index].deviceConsumption;
        }
      }
      // tslint:disable-next-line: prefer-for-of
      for (let index = 0; index < this.deviceList.length; index++) {
       if (this.deviceList[index].deviceId === element){
        deviceName = this.deviceList[index].deviceName;
       }
      }
      consumptions.push(
        {
          deviceId: element,
          deviceName : deviceName,
          deviceConsumption : (sum / total) * 100
        }
      );
    });

    console.log('device Ids: ' + deviceIds);
    // console.log('consumption: ' + JSON.stringify(consumptions));
    console.log('total: ' + total);
    this.lastHour = consumptions;
    console.log('last hour: ' + JSON.stringify(this.lastHour));
  }

  getDeviceState(deviceId) {
    // get device topic using id
    // let topic;
    /*for (let index = 0; index < this.deviceList.length; index++) {
      if(this.deviceList[index].toLower().includes(deviceId)){
            topic = this.deviceList[index].deviceTopic
            //subscribe to socket
            break;
      }

    }*/
  }
  toggelDevice(deviceId) {

  }
  toggleDevice() {
    console.log('Toggling Device!!!!');
  }

  private _filter(value: string): string[] {
    const filterValue = value.toLowerCase();
    return this.deviceNames.filter(option => option.toLowerCase().includes(filterValue));
  }

  returnDevice() {

    if (this.userDeviceName.value === null || this.userDeviceName.value == null) {
      return;
    }

    const option = this.userDeviceName.value.toLowerCase();
    this.deviceResult = null;
    this.deviceResult = [];
    let arrayIndex = 0;
    for (let index = 0; index < this.deviceList.length; index++) {
        if (this.deviceList[index].deviceName.toLowerCase().includes(option) ) {
          console.log('device result: ' + this.deviceResult);
          this.deviceResult[arrayIndex] = this.deviceList[index];
          arrayIndex++;
        }
      }
    JSON.stringify(this.deviceResult);
    console.log(this.deviceResult);
    return;
  }

}

export interface Devices {
  deviceId: number;
  deviceName: string;
  deviceConsumption: number;
  devicePriority: string;
}
