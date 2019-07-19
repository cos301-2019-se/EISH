import { Injectable } from '@angular/core';
/*import * as Stomp from 'stompjs';
import * as SockJS from 'sockjs-client';*/
import {Device} from './pages/devices/devices.component';

@Injectable({
  providedIn: 'root'
})
export class SocketService {
  

  /*private serverUrl = 'http://localhost:8080/socket'
  private stompClient: Stomp;
  device: Device*/
  constructor() { 
    this.initializeWebSocketConnection();
  }

  initializeWebSocketConnection() {
  /*  let ws = new SockJS(this.serverUrl);
    this.stompClient = Stomp.over(ws);

    let that = this;
    this.stompClient.connect({}, function(frame) {
      that.stompClient.subscribe(
        /*
        *subscribe body:
         */
         /*
          '/linkToSpecificSubscription/', message) => { call appropriate function for message received
          //
                 console.log(message)
                 this.device.changeState(message);
                }
         */
}
}
