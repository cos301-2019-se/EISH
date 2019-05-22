import { Component, OnInit } from '@angular/core';
import { RequestsService } from 'src/app/requests.service';
import { FormsModule }   from '@angular/forms';
@Component({
  selector: 'app-device-form',
  templateUrl: './device-form.component.html',
  styleUrls: ['./device-form.component.css']
})
export class DeviceFormComponent implements OnInit {
  device = {device_name: '', topic: '', minWatt: '', maxWatt: '', device_type: ''}
  constructor(service: RequestsService) { }
  service: RequestsService
  ngOnInit() {
  }
  
  addDevice(){
    console.log(deviceForm);
    this.service.add_device(deviceForm);
  }

}
