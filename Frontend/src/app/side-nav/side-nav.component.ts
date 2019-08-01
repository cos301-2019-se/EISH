import { Component, OnInit  } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MAT_DRAWER_DEFAULT_AUTOSIZE } from '@angular/material';
import { UserAccessControlService } from '../services/user/user-access-control.service';

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
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver, private userService: UserAccessControlService) {
    this.initials = 'EU';
  }

  ngOnInit() {
    this.initials = 'EU';
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
    this.userService.getUserPresence(); /* .pipe(
      map(response => {
          userList  = response;
      )
    ).subscribe*/
  }
}
