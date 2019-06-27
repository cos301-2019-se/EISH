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
      'userEmail':[null,[Validators.required,Validators.email]],
      'userPassword':[null,[Validators.required,Validators.minLength(8),Validators.maxLength(40)]]
    });
  }

  ngOnInit() {
    this.incorrectCredentials = false;
    this.formHeading="Login";
    this.loginForm = this.fb.group({
      'userEmail':[null,[Validators.required,Validators.email]],
      'userPassword':[null,[Validators.required,Validators.minLength(8),Validators.maxLength(40)]]
    });
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
    
    
    if(this.loginForm.invalid)
    {
      return;
    }else{
      
      let result = this.AuthenticationServices.authenticateUser(formData.value);

      if(result){
        this.route.navigate(['/register']);
        }else{
        this.incorrectCredentials = true;
        return;
        //possibly need to clear the fields if they arent already;
      }
    }
  
  }
 

}
