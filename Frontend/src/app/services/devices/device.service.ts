import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import { Device } from 'src/app/models/device-model';
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
   * @param deviceId The device id is used find a device with that id.
   * @returns Observable object of type Device.
   */
  getDevice(deviceId): Observable<Device[]> {
    const params = new HttpParams().set('deviceId', deviceId);
    return this.http.get<Device[]>(this.ROOT_URL + 'device', {params});
  }

  /**
   * Get devices' current power/consumption state
   */
  getCurrentState() {

  }

  /**
   * Sends request to add new device through API
   * POST Reqest
   * @param deviceForm is the information that the user has provided.
   */
  addDevice(deviceForm) {
    this.http.post(this.ROOT_URL + 'device', deviceForm);
  }


  /**
   * Sends a request to edit an existing device througth API
   * PUT Request
   * @param deviceForm is the information that the user has provided.
   */
  editDevice(deviceForm) {
    console.log('editing service: ' + deviceForm.deviceName);
    this.http.put(this.ROOT_URL + 'device', deviceForm);
  }

  /**
   * Sends a request to remove an existing device through API
   * DELETE Request
   * @param deviceId The device id is used find a device with that id.
   */
  removeDevice(deviceId) {
    console.log('inside service, device id: ' + deviceId);
    const params = new HttpParams().set('deviceId', deviceId);
    this.http.delete(this.ROOT_URL  + 'device', {params} );
  }

  controlDevice() {

  }
}
