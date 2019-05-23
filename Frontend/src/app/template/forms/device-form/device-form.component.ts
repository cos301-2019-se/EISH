import { Component, OnInit } from '@angular/core';
import { RequestsService } from 'src/app/requests.service';
import { FormsModule }   from '@angular/forms';
import {Device} from 'src/app/pages/devices/devices.component'
@Component({
  selector: 'app-device-form',
  templateUrl: './device-form.component.html',
  styleUrls: ['./device-form.component.css']
})
export class DeviceFormComponent implements OnInit {
 
  /**
   * Variables:
   */
  device = new Device();
  service: RequestsService

  /**
   * constructor
   */
  constructor(service: RequestsService) {
    this.service = service;
   }
  
  ngOnInit() {
  }
  
  /*
  *call to add new device to system
  */
  addDevice(formData){
   
    this.service.add_device(this.device);  
  }

}
