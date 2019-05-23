import { Component, OnInit, TemplateRef } from '@angular/core';

import {HttpClient} from '@angular/common/http';
import { RequestsService } from 'src/app/requests.service';
import { Observable } from 'rxjs';
import {Generator} from '../generators/generators.component';
import { element } from '@angular/core/src/render3';


export class Device{
  device_id: number
  device_name: string
  topic: string
  min_watts: number
  max_watts: number
  device_type: string
  device_priority: string
  auto_start: boolean
  device_state: boolean
  //check with Nare if state returned
  constructor(){}
 
}


@Component({
  selector: 'app-devices',
  templateUrl: './devices.component.html',
  styleUrls: ['./devices.component.css']
  
})
export class DevicesComponent implements OnInit {

  /**
   * varaiables:
   */
  public deviceList: Observable<Device []>;
  public generatorList : Observable<Generator [] >;

  service: RequestsService
//  webSocket: SocketService

  constructor(service: RequestsService,) { 
    
    this.service = service;
    

  }

  ngOnInit() {
    this.deviceList = this.service.getDevicesList();
    this.service.getDevicesList().subscribe(data =>{
      console.log(data);
    });
  }

  control(device: Device){
    this.service.control_device(device.device_id);
    //route to page again if socket isn't working
    //this.webSocket()

  }
   
  changeState(message){
    this.deviceList.forEach(element => {
      element.toString()
      /**
         * if(element.device_id == message.device_id){
          element.state = message.state;
        }
       */
    })
  }

  }


