import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  home = HomeComponent;
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

  generatorList = [
    {"generator_name":"Solar Power System","generator_type":"Solar Power","generator_state":true},
    {"generator_name":"Diesel Generator","generator_type":"Standby Generator","generator_state":false}
];


}
