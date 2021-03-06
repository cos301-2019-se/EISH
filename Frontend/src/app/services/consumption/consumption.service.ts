import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consumption } from 'src/app/models/consumption-model';
import {HttpClient, HttpParams} from '@angular/common/http';
import { Device } from 'src/app/models/device-model';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  /**
   * Variables:
   */
  ROOT_URL = environment.ROOT_URL;
  JSON_URL = 'assets/data/';

  constructor(private http: HttpClient) {}

  getCustomDeviceConsumption(deviceId, startTimeStamp, endTimeStamp): Observable<[]> {
    const params = new HttpParams().set('deviceId', deviceId).set('startTimeStamp', startTimeStamp).set('endTimeStamp', endTimeStamp);
    return this.http.get<[]>(this.ROOT_URL + 'device/consumption', { params });
  }
  /**
   *
   */
  getJSONConsumption(): Observable<Consumption[]> {
    return this.http.get<Consumption[]>(this.JSON_URL + 'consumption.json');
  }

  getDayTotalConsumption(): Observable<any> {
    return this.http.get(this.ROOT_URL + 'home/consumption/day');
  }

  getWeekTotalConsumption(): Observable<any> {
    return this.http.get(this.ROOT_URL + 'home/consumption/week');
  }

  getMonthTotalConsumption(): Observable<any> {
    return this.http.get(this.ROOT_URL + 'home/consumption/month');
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
