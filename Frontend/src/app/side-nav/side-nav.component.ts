import { Component, OnInit  } from '@angular/core';
import { BreakpointObserver, Breakpoints } from '@angular/cdk/layout';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { MAT_DRAWER_DEFAULT_AUTOSIZE } from '@angular/material';

@Component({
  selector: 'app-side-nav',
  templateUrl: './side-nav.component.html',
  styleUrls: ['./side-nav.component.css']
})
export class SideNavComponent implements OnInit {
  initials: string;
  userList: any;
  users = [ 'Koki', 'Lebo', 'Aviwe' ];
  isHandset$: Observable<boolean> = this.breakpointObserver.observe(Breakpoints.Handset)
    .pipe(
      map(result => result.matches)
    );

  constructor(private breakpointObserver: BreakpointObserver) {
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
}
