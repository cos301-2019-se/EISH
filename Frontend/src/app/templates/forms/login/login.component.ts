import { Component, OnInit } from '@angular/core';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {
  /**
   * Class is responsible for loggin into the  system
   * Route to key, change credentials or homepage
   */
  constructor() { }

  ngOnInit() {
  }

  /**
   * Submits credentials;
   * if default admin credentials routes to changeCredentials
   * if guest has expired, route to keyPage
   */
  login(){
    //sanitize and validate
    //if admin admin: go to server, according to response:  make token and  route to change credntials or wrong credntials
  }
 

}
