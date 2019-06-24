import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user-model';
import { UserAccessControlService } from 'src/app/services/user/user-access-control.service';

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
  constructor(private fb: FormBuilder) {
   }

  ngOnInit() {
    this.formHeading="Login";
    this.loginForm = this.fb.group({
      'userEmail':['',[Validators.required,Validators.email]],
      'userPassword':['',[Validators.required,Validators.minLength(8),Validators.maxLength(40)]]
    });
    //do you call constructor here?
  }

  get getVariables(){
    return this.loginForm.controls;
  }
  /**
   * Submits credentials;
   * if default admin credentials routes to changeCredentials
   * if guest has expired, route to keyPage
   */
  login(){
    //sanitize and validate
    //if admin admin: go to server, according to response:  make token and  route to change credntials or wrong credntials
    if(this.loginForm.invalid)
    {
      return;
    }
    else{
      alert('SUCCESS!! :-)\n\n' + JSON.stringify(this.loginForm.value, null, 4))
    }
    //send username and password
   // if(this.getVariables().userName === "admin")
    {
      ///handle response from authenticateUser()
      //if success route to changeCredentials else reload login with error
    }
    //else
    {
      //handle response from authenticateUSer()
      //if success route to system
    }
  
  }
 

}
