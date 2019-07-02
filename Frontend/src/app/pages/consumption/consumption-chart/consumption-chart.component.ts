import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-consumption-chart',
  templateUrl: './consumption-chart.component.html',
  styleUrls: ['./consumption-chart.component.css']
})
export class ConsumptionChartComponent implements OnInit {

  /**
   * Variables:
   */
  deviceChart: any;
  totalConsumption: any;

  constructor() { }

  ngOnInit() {
  }

  /**
   * Draw graph for total consumption of all devices
   */
  drawTotalConsumption(){

  }

  /**
   * Draw graph for total consumption of a specific devices
   */
  drawDeviceConsumption(){

  }

}
