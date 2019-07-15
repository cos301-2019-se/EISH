import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import {Device} from 'src/app/models/device-model';
// import { Socket } from "ngx-socket-io";
@Injectable({
  providedIn: 'root'
})
export class DeviceService {
/**
 * Variables
 */
  ROOT_URL = 'http://192.168.8.102:8080/api/';
  JSON_URL = 'assets/data/';

  constructor(private http: HttpClient) { }

  getDeviceJSONArray(): Observable<Device[]> {
    console.log('inside getDeviceArray');
    return this.http.get<Device []>(this.JSON_URL + 'device.json');
  }

  /**
   * Retrieves list of devices
   * GET Request
   * @returns Observable array of type Device
   */
  getAllDevices(): Observable<Device[]> {
    return this.http.get<Device[]>(this.ROOT_URL + 'devices');
  }

  /**
   * Retrieves object given specified deviceId
   * GET Request
   * @param deviceId;
   * @returns Observable object of type Device
   */
  getDevice(deviceId): Observable<Device[]> {
    const params = new HttpParams().set('deviceId', deviceId);
    return this.http.get<Device[]>(this.ROOT_URL + 'device', {params});
  }

  /**
   * Get devices' current power/consumption state
   */
  getCurrentState() {
    return this.http.get(this.ROOT_URL + '');
  }

  /**
   * Sends request to add new device through API
   * POST Reqest
   * @param deviceForm;
   */
  addDevice(deviceForm) {
    this.http.post(this.ROOT_URL + 'device', deviceForm);
  }


  /**
   * Sends a request to edit an existing device througth API
   * PUT Request
   * @param deviceForm;
   */
  editDevice(deviceForm) {
    console.log('editing service: ' + deviceForm.deviceName);
    this.http.put(this.ROOT_URL + 'device', deviceForm);
  }

  /**
   * Sends a request to remove an existing device through API
   * DELETE Request
   * @param deviceId;
   */
  removeDevice(deviceId) {
    console.log('inside service, device id: ' + deviceId);
    const params = new HttpParams().set('deviceId', deviceId);
    this.http.delete(this.ROOT_URL  + 'device', {params} );
  }

  /**
   * Send a request to change a devices' power consumption state
   * PATCH Request
   * @param deviceId; ID of device in question
   */
  controlDevice(deviceId) {
    // {deviceId}/{deviceState}
    let deviceState;
    this.getCurrentState().pipe(
      map( response => {
        deviceState =  response,
        JSON.stringify(deviceState);

      })
    ); // .subscribe();

    const params = new HttpParams().set('deviceId', deviceId);
    params.set('deviceState', deviceState);
    this.http.patch(this.ROOT_URL + '', {HttpParams});
  }

}
