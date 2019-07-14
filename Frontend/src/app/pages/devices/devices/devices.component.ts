import { Component, OnInit } from '@angular/core';
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

  ];
  constructor() { }

  ngOnInit() {
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
