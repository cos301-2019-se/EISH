import { Component, OnInit, Input } from '@angular/core';

@Component({
  selector: 'app-single-device',
  templateUrl: './single-device.component.html',
  styleUrls: ['./single-device.component.css']
})
export class SingleDeviceComponent implements OnInit {
  @Input() device:Device;
  deviceState='ON';
  
  constructor() { }

  ngOnInit() {
  }
  toggleState(){
    console.log("let's toggle !!!")
    if(this.deviceState==='ON')
      this.deviceState = 'OFF';
    else 
      this.deviceState = 'ON';
  }
}
export interface Device {
  deviceId: number;
  deviceName: string;
  deviceConsumption: number;
}