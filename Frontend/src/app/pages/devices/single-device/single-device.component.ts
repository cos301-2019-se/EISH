import { Component, OnInit, Input, OnDestroy } from '@angular/core';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message} from '@stomp/stompjs';
import { DeviceService } from 'src/app/services/devices/device.service';
import { Device } from 'src/app/models/device-model';
import {map, startWith} from 'rxjs/operators';
import {Observable} from 'rxjs';
@Component({
  selector: 'app-single-device',
  templateUrl: './single-device.component.html',
  styleUrls: ['./single-device.component.css']
})
export class SingleDeviceComponent implements OnInit, OnDestroy {
  @Input() device: Device;

  deviceState = 'OFF';
  socket: any;
  deviceConsumption: number;
 
  constructor(private rxStompService: RxStompService, private deviceService: DeviceService) { }

  ngOnInit() {
    console.log(this.device.deviceName);

    this.socket = this.rxStompService.watch('/device/' + this.device.deviceTopic + '/state').subscribe((message: Message) => {
      this.deviceState = JSON.parse(message.body).state;
      console.log(this.deviceState);
    });

    this.socket = this.rxStompService.watch('/device/' + this.device.deviceTopic + '/consumption').subscribe((message: Message) => {
      this.deviceConsumption = (JSON.parse(message.body)).deviceConsumption;
      console.log(this.deviceConsumption);
    });

    this.deviceService.getState(this.device.deviceId).pipe(
      map( response => {
        console.log(response)
        this.deviceState =JSON.stringify( response);
      })
     ).subscribe();

  }


  ngOnDestroy(): void {
    this.socket.unsubscribe();
  }

  toggleState() {
    const deviceObj = {
      deviceId: this.device.deviceId,
      deviceState: this.deviceState
    };
    this.deviceService.controlDevice(deviceObj);
  }
}
