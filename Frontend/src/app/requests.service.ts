import { Injectable } from '@angular/core';
import {HttpClient, HttpHeaders} from '@angular/common/http';
import 'rxjs/add/operator/map'

import {Device} from './pages/devices/devices.component';
import {Generator} from './pages/generators/generators.component'
import {Consumption} from './consumption.component'
import { Observable } from 'rxjs';
import { map } from 'rxjs-compat/operator/map';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

    /**
     * public variables
     */
    URL = 'http://192.168.8.100:8080/api/'
    HTTPURL= 'http://localhost:8080/api/'

    devices: Device;
    generator : Generator;

    //Constructor, gets Injectable http client instance
    constructor(private http: HttpClient) { }

    //API call to get entire list of devices
    //returns Observable, Device array
    getDevicesList() :Observable<Device []>{
     
      return this.http.get<Device []>(this.URL + 'view/devices');
      
    }

    //API call to get entire list of generators
    //returns Observable, Generator array
    getGeneratorList() :Observable<Generator []>{
      return this.http.get<Generator []>(this.URL + 'view/generators');
    }

    //API call to add new device entry
    add_device(body: Device){
      //console.log('inside add_device');
      //console.log(body.device_name + ', ' + body.topic, body.max_watts, body.max_watts, body.device_type);   
      this.http.post(this.URL + 'add/device', body).subscribe(data =>console.log(data));

    }

    add_generator(body: Generator){
      //console.log('inside add_generator');
      //console.log(body.generator_name + ', ' + body.topic, body.max_capacity, body.max_capacity, body.generator_type);   
      this.http.post(this.URL + 'add/generator', body).subscribe();
    }
    control_device(deviceID){
      var body  = {"device_id": deviceID}

      this.http.patch(this.URL + 'control/device',body).subscribe();
    }

    getTotalConsumption(): Observable<Consumption []> {
      return this.http.get<Consumption []>(this.URL + 'consumption');
    }
  }