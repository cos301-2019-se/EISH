import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from './user.ts';
import {accessControl} from 'src/app/services/user/user-access-control.service';

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
  formHeading: String;
  loginForm: FormGroup;
  user = new User();
  userType: Array<string> = [
    'admin',
    'guest',
    'resident'
  ];
  constructor(private fb: FormBuilder) {
    this.loginForm = this.fb.group({
      'userEmail':['',[Validators.required,Validators.EmailValidator]],
      'userPassword':['',[Validators.required,Validators.minLength(8),Validators.maxLength(40)]]
    });
   }

  ngOnInit() {
    this.formHeading="Login";
    //do you call constructor here?
  }

  /**
   * Submits credentials;
   * if default admin credentials routes to changeCredentials
   * if guest has expired, route to keyPage
   */
  login(formData){
    //sanitize and validate
    //if admin admin: go to server, according to response:  make token and  route to change credntials or wrong credntials
    const regform = formData;
    //send username and password
    if(formData.userName === "admin")
    {
      ///handle response from authenticateUser()
      //if success route to changeCredentials else reload login with error
    }
    else{
      //handle response from authenticateUSer()
      //if success route to system
    }
  
  }
 

}
