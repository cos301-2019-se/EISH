import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-consumption',
  templateUrl: './consumption.component.html'
})
export class Consumption {
  device_id: number
  date_time: number
  consumption: number
}
export class ConsumptionComponent implements OnInit {
  consumption = new Consumption;

  constructor() { }

  ngOnInit() {
  }

}
