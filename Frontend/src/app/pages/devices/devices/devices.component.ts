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
  deviceArray: Devices[] = [
    {deviceId: 1, deviceName: 'Sony TV', deviceConsumption: 30},
    {deviceId: 2, deviceName: 'Samsung Fridge', deviceConsumption: 60},
    {deviceId: 3, deviceName: 'Kettle', deviceConsumption: 12},
    {deviceId: 4, deviceName: 'Sony Home Theatre', deviceConsumption: 8},
    {deviceId: 5, deviceName: 'Sony Playstation', deviceConsumption: 15},
  ]
  deviceList: any;
  consumptionArray: any;
  lastHourTotal: number;

  constructor(private consumptionService: ConsumptionService, private deviceService: DeviceService) { }

  ngOnInit() {

      this.deviceService.getDeviceJSONArray().pipe(
        map( response => {
            this.deviceList =  response,
            JSON.stringify(this.deviceList);
            console.log(this.deviceList)
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
  }

  /**
   * 
   */
  progressBarAgregation(){
    //console.log('progress: '+ JSON.stringify(this.consumptionArray))
    let total = 0;
    for (let index = 0; index < this.consumptionArray.length; index++) {
      //console.log(this.consumptionArray[index].deviceConsumption)
      total += this.consumptionArray[index].deviceConsumption;
    }

   /* let keys = Object.keys(this.consumptionArray)
    let deviceIds;
    for (let index = 0; index < keys.length; index++) {
      if(deviceIds.includes(this.consumptionArray[index].deviceId))
          continue
      else
          deviceIds.push(this.consumptionArray[index].deviceId)
      
    }
    console.log('device Ids: ' + deviceIds)
    console.log('keys '+ keys)*/
    console.log('total: '+ total);
  }

  getDeviceState(deviceId){
    //get device topic using id
    let topic;
    /*for (let index = 0; index < this.deviceList.length; index++) {
      if(this.deviceList[index].toLower().includes(deviceId)){
            topic = this.deviceList[index].deviceTopic
            //subscribe to socket
            break;
      }
      
    }*/
  }
  toggelDevice(deviceId){

  }
  toggleDevice() {
    console.log('Toggling Device!!!!');
  }
}

export interface Devices {
  deviceId: number;
  deviceName: string;
  deviceConsumption: number;
}
