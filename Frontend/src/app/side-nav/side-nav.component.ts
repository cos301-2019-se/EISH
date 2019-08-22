import { Component, OnInit  } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MAT_DRAWER_DEFAULT_AUTOSIZE } from '@angular/material';
import { UserAccessControlService } from '../services/user/user-access-control.service';
import { NotificationsService } from '../services/notifications/notifications.service';
import { Message } from '@stomp/stompjs';
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

  users = [ 'Koki', 'Lebo', 'Aviwe' ];

  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
  );
  isAdmin: boolean;

  constructor(private breakpointObserver: BreakpointObserver,
              private userService: UserAccessControlService,
              private notificationService: NotificationsService,
              private route: Router) {
    this.initials = 'EU';

  }

  ngOnInit() {
    this.initials = 'EU';
    this.notificationService.notificationSocket().subscribe();
    sessionStorage.setItem('userType', 'ROLE_ADMIN');
    if (sessionStorage.getItem('userType') === 'ROLE_ADMIN') {
      this.isAdmin = true;
    } else {
     this.isAdmin = false;
    }
    this.getUserPresence();
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
