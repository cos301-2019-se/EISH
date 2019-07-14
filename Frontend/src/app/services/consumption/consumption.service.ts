import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Observable } from 'rxjs';
import { Device } from 'src/app/models/device-model';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  ROOT_URL = 'http://192.168.8.102:8080/api/';

  constructor(private http: HttpClient) { }

  getAllDevicesConsumption() {

  }

  getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.ROOT_URL + 'devices');
  }

  getCustomDeviceConsumption(deviceId, startTimeStamp, endTimeStamp): Observable<[]> {
    const params = new HttpParams().set('deviceId', deviceId).set('startTimeStamp', startTimeStamp).set('endTimeStamp', endTimeStamp);
    return this.http.get<[]>(this.ROOT_URL + 'device/consumption', { params });
  }

  getSpecialDeviceConsumption(deviceId, specialRange): Observable<[]> {
    return null;
  }

  convertSpecialRangeToTimestamps(specialRange) {
    const CurrDate = new Date();
    let pastDate = null;
    switch (specialRange) {
      case 'Last Hour':
        pastDate = new Date();
        break;
      case 'Today':
        break;
      case 'This Week':
        break;
      case 'This Month':
        break;
      case 'This Year':
        break;
      default:

    }
  }

  newDate(objDate, year, month, day, hour, minute): Date {
    return null;
  }

}
