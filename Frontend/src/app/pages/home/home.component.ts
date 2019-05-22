import { Component, OnInit } from '@angular/core';
import { Observable } from 'rxjs';

import { RequestsService } from 'src/app/requests.service';
import {Device} from 'src/app/pages/devices/devices.component';
import {Generator} from 'src/app/pages/generators/generators.component';

import { GoogleChartsModule } from 'angular-google-charts';


@Component({
  selector: 'app-home',
  templateUrl: './home.component.html',
  styleUrls: ['./home.component.css']
})
export class HomeComponent implements OnInit {
  
  public deviceList: Observable<Device []>;
  public generatorList : Observable<Generator [] >;

  title = 'Current Battery Capacity';
    type = 'PieChart';
    data = [
      ['Battery Filled',     85],
      ['Empty',      15]
    ];
    columnNames =  ['Task', 'Hours per Day'];
    options = { 
      colors: ['#54B754','#DCDCDC'],
      pieHole: 0.4   
    };
    
  constructor(service: RequestsService) { 
    this.deviceList = service.getDevicesList();
    this.generatorList = service.getGeneratorList();
  }

  ngOnInit() {
   
  }

   drawBatteryChart() {
    
  }
}
