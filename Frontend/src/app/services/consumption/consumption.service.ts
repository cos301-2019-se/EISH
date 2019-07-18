import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Device } from 'src/app/models/device-model';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  ROOT_URL = 'http://192.168.8.102:8080/api/';

  constructor(private http: HttpClient) {}

  getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.ROOT_URL + 'devices');
  }

  getCustomDeviceConsumption(deviceId, startTimeStamp, endTimeStamp): Observable<[]> {
    const params = new HttpParams().set('deviceId', deviceId).set('startTimeStamp', startTimeStamp).set('endTimeStamp', endTimeStamp);
    return this.http.get<[]>(this.ROOT_URL + 'device/consumption', { params });
  }

  getCustomHomeConsumption(startTimeStamp, endTimeStamp): Observable<[]> {
    const params = new HttpParams().set('startTimeStamp', startTimeStamp).set('endTimeStamp', endTimeStamp);
    return this.http.get<[]>(this.ROOT_URL + 'home/consumption', { params });
  }

  getSpecialDeviceConsumption(deviceId, specialRange): Observable<[]> {
    const params = new HttpParams().set('deviceId', deviceId).set('interval', specialRange);
    return this.http.get<[]>(this.ROOT_URL + 'device/consumption', { params });
  }

  getSpecialHomeConsumption(specialRange): Observable<[]> {
    const params = new HttpParams().set('interval', specialRange);
    return this.http.get<[]>(this.ROOT_URL + 'home/consumption', { params });
  }

}
