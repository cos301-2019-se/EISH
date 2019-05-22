import { Component, OnInit, TemplateRef } from '@angular/core';

import {HttpClient} from '@angular/common/http';
import { RequestsService } from 'src/app/requests.service';
import { Observable } from 'rxjs';
import {Generator} from '../generators/generators.component';

export interface Device{
  device_name: string,
  topic: string,
  min_watts: DoubleRange, 
  max_watts: DoubleRange,
  device_type: string,
  state: string
}


@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
  
})
export class DevicesComponent implements OnInit {

  public deviceList: Observable<Device []>;
  public generatorList : Observable<Generator [] >;
  device:any;

  constructor(service: RequestsService) { 
    this.deviceList = service.getDevicesList();
    this.generatorList = service.getGeneratorList();
  
  }

  ngOnInit() {
    
  }

   


  }


