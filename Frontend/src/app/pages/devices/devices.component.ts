import { Component, OnInit } from '@angular/core';
import {HomeComponent} from '../home/home.component';

@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
})
export class DevicesComponent implements OnInit {

  constructor() { }

  ngOnInit() {
  }
  deviceList = [
    {"device_name":"Fridge","device_type":"Fridge","device_state":true},
    {"device_name":"Microwave","device_type":"Microwave","device_state":true},
    {"device_name":"Toaster","device_type":"Toaster","device_state":false},
    {"device_name":"Dining Room Light","device_type":"Light","device_state":true},
    {"device_name":"Bedroom Light","device_type":"Light","device_state":false}
  ];

  openModal(): void{
    console.log('Works');
  }
}
