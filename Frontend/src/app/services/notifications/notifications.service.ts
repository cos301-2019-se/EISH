import { Injectable } from '@angular/core';
import {HttpClient, HttpParams} from '@angular/common/http';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message, IMessage} from '@stomp/stompjs';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class NotificationsService {
  /**
   * Variables:
   */
  ROOT_URL = 'http://192.168.8.100:8080/api/';
  constructor(private http: HttpClient, private rxStompService: RxStompService) { }

  /**
   * GET Request
   * Retrieves All Notifications
   */
  getAllNotifications() {
    this.http.get(this.ROOT_URL + '');
  }

  /**
   * Subscribes to notification socket
   */
  notificationSocket(): Observable<IMessage> {
    return this.rxStompService.watch('');
  }
}
