import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { RequestsService } from 'src/app/requests.service';
import {Device} from 'src/app/pages/devices/devices.component';
import {Generator} from 'src/app/pages/generators/generators.component';

import { Chart } from 'chart.js';
import {ChartsComponent} from 'src/app/charts/charts.component'
import { Consumption } from 'src/app/consumption.component';

import { map } from 'rxjs-compat/operator/map';

@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  /*
  * Variables:
  */
   
  public deviceList: Observable<Device []>;
  public generatorList : Observable<Generator [] >;
  public consumptionList : Observable<Consumption[]>;
  service: RequestsService;
  charts: ChartsComponent;
  
  public consumption: Array<Consumption>

  constructor(service: RequestsService, cc: ChartsComponent) { 
    this.service = service;
    this.charts = cc;
  }

  ngOnInit() {
    
    this.deviceList = this.service.getDevicesList()
    this.generatorList = this.service.getGeneratorList();
    this.charts.drawBatteryCapacity();
    this.printConsumption();
    this.charts.drawTotalConsumption();
  }

  printConsumption(){
    
  }
}
