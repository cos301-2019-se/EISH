import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map, catchError } from 'rxjs/operators';
import {Device} from 'src/app/models/device-model';
import { environment } from 'src/environments/environment';
// import { Socket } from "ngx-socket-io";
@Injectable({
  providedIn: 'root'
})
export class DeviceService {
/**
 * Variables
 */
  ROOT_URL = environment.ROOT_URL;
  JSON_URL = 'assets/data/';

  constructor(private http: HttpClient) {
    console.log(environment.ROOT_URL);
  }

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
   * Sends request to add new device through API
   * POST Reqest
   * @param deviceForm;
   */
  addDevice(deviceForm) {
    console.log('new device: ' + JSON.stringify(deviceForm));
    this.http.post(this.ROOT_URL + 'device', deviceForm).subscribe(
      /*( res: Response) => {
        if (res.status !== 200 || res.ok === false ) {
          console.log('error');
        }*/
     );

  }


  /**
   * Sends a request to edit an existing device througth API
   * PUT Request
   * @param deviceForm;
   */
  editDevice(deviceForm) {
    console.log('editing service: ' + JSON.stringify(deviceForm));
    return this.http.put(this.ROOT_URL + 'device', deviceForm);
  }

  /**
   * Sends a request to remove an existing device through API
   * DELETE Request
   * @param deviceId;
   */
  removeDevice(deviceId): Observable<any> {
    return this.http.delete(this.ROOT_URL  + 'device/' + deviceId );
  }

  /**
   * Send a request to change a devices' power consumption state
   * PATCH Request
   * @param deviceId; ID of device in question
   */
  controlDevice(device) {
    device.deviceState = (device.deviceState === 'ON') ? 'OFF' : 'ON';
    this.http.patch(this.ROOT_URL + 'device/' + device.deviceId + '/' + device.deviceState, {}).subscribe();

    // {deviceId}/{deviceState}
    /*
    let deviceState;
    this.getCurrentState().pipe(
      map( response => {
        deviceState =  response,
        JSON.stringify(deviceState);

      })
    ); // .subscribe();
    */

  }
  getState(deviceId): any {
    return this.http.get(this.ROOT_URL + 'devicestate/' +deviceId )
  }

}
