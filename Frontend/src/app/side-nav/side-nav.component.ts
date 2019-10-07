import { Component, OnInit  } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MAT_DRAWER_DEFAULT_AUTOSIZE } from '@angular/material';
import { UserAccessControlService } from '../services/user/user-access-control.service';
import { NotificationsService } from '../services/notifications/notifications.service';
import { NotifierService } from 'angular-notifier';
import { RxStompService } from '@stomp/ng2-stompjs';
import { Message} from '@stomp/stompjs';
import { Router } from '@angular/router';


@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent implements OnInit {
  /**
   * Variables
   */
  initials: string;
  userList: any;
  nrPeople: any;
  dataReady: boolean;
  users = [ 'Koki', 'Lebo', 'Aviwe' ];
  notificationsArray = ['Battery Full',
                   'New device added',
                   'Given left',
                   'Panasonic TV is offline' ];
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
  );
  isAdmin: boolean;
  notification: any;
  private notifier: NotifierService;
  constructor(private breakpointObserver: BreakpointObserver,
              private userService: UserAccessControlService,
              private notificationService: NotificationsService,
              private route: Router, private notifierService: NotifierService,
              private rxStompService: RxStompService) {

  }

  ngOnInit() {
    this.notifier = this.notifierService;
    this.rxStompService.watch('/notification').subscribe((message: Message) => {
      this.notification = JSON.parse(message.body);
      this.createNotification();
    });


    const name = sessionStorage.getItem('userName');
    this.initials = name.charAt(0).toLocaleUpperCase();
    if (sessionStorage.getItem('userType') === 'ROLE_ADMIN') {
      this.isAdmin = true;
    } else {
     this.isAdmin = false;
    }
    this.getUserPresence();
    this.dataReady = true;
  }

  createNotification() {
    
    if (this.notification.priority == 'PRIORITY_CRITICAL') {
      this.notifier.notify('error', this.notification.message);
    } else if (this.notification.priority == 'PRIORITY_WARNING') {
      this.notifier.notify('warning', this.notification.message);
    } else if (this.notification.priority == 'PRIORITY_MINOR') {
      this.notifier.notify('success', this.notification.message)
    } else {
      this.notifier.notify('neutral', this.notification.message)
    }
    
  }

  toggle(drawer) {
    this.isHandset$.subscribe(result => {
      console.log(result);
      if (result) {
        drawer.toggle();
      }
    });
  }

  getUserPresence() {
    console.log('getting user presence');
    this.userService.getUserPresence().pipe(
      map(response => {
          console.log(response);
          this.userList  = response;
          this.nrPeople = this.userList.length;
      })
    ).subscribe();
  }

  logout() {
    console.log('logging out');
    this.userService.userLogOut();
  }

  routeToChange() {
    this.route.navigate(['register', 'Change']);
  }
}
