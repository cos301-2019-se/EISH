import { Injectable } from '@angular/core';
import {HttpClient} from '@angular/common/http';
import 'rxjs/add/operator/map'

import {Device} from './pages/devices/devices.component';
import {Generator} from './pages/generators/generators.component'
import { Observable } from 'rxjs';
import { map } from 'rxjs-compat/operator/map';

@Injectable({
  providedIn: 'root'
})
export class RequestsService {

    /**
     * public variables
     */
    devices: Device;
    generator : Generator;

    //Constructor, gets Injectable http client instance
    constructor(private http: HttpClient) { }

    //API call to get entire list of devices
    //returns Observable, Device array
    getDevicesList() :Observable<Device []>{
      return this.http.get<Device []>('http://localhost:3000/api/devices');
      
    }

    //API call to get entire list of generators
    //returns Observable, Generator array
    getGeneratorList() :Observable<Generator []>{
      return this.http.get<Generator []>('http://localhost:3000/api/generators');
    }

    add_device(body){
       return this.http.post('http://localhost:3000/api/add_device', body);
    }

}
