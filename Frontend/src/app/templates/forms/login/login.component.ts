import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { UserAccessControlService } from 'src/app/services/user/user-access-control.service';
import { Router} from '@angular/router';

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
  incorrectCredentials: boolean;

  constructor(private route: Router,private fb: FormBuilder, private AuthenticationServices: UserAccessControlService) {
    this.incorrectCredentials = false;
    this.formHeading="Login";
    this.loginForm = this.fb.group({
      'userName':[null,[Validators.required]],
      'userPassword':[null,[Validators.required,Validators.minLength(5),Validators.maxLength(40)]]
    });
  }

  ngOnInit() {
    this.incorrectCredentials = false;
    this.formHeading="Login";
    this.loginForm = this.fb.group({
      'userName':[null,[Validators.required]],
      'userPassword':[null,[Validators.required,Validators.minLength(5),Validators.maxLength(40)]]
    });
  }

  switcher(){
    this.route.navigate(['register','Change']);      
  }

  error(){
    this.incorrectCredentials = true;

  }

  get getVariables(){
    return this.loginForm.controls;
  }
  /**
   * Submits credentials;
   * if default admin credentials routes to changeCredentials
   * if guest has expired, route to keyPage
   */
  login(formData){
    //sanitize and validate
    //if admin admin: go to server, according to response:  make token and  route to change credntials or wrong credntials
    
    if(this.loginForm.invalid){
      return;
    }else {
      console.log(formData.value);
     this.AuthenticationServices.authenticateUser(formData.value, this);
    }

  }
}
