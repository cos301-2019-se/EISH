import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Consumption } from 'src/app/models/consumption-model';
import { HttpClient } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class ConsumptionService {

  /**
   * Variables:
   */
  ROOT_URL='http://192.168.8.102:8080/api/';
  JSON_URL ='assets/data/';

  constructor(private http: HttpClient) { }

 
  getAllDevicesConsumption(){

  }
  getDeviceConsumption(){

  }
  /**
   * 
   */
  getJSONConsumption():Observable<Consumption[]>{
    return this.http.get<Consumption[]>(this.JSON_URL + 'consumption.json');
  }
}
