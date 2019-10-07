import { Component, OnInit } from '@angular/core';
import { FormGroup, FormBuilder, Validators } from '@angular/forms';
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
  formHeading: string;
  loginForm: FormGroup;
  incorrectCredentials: boolean;
  userLoginForm: FormGroup;
  constructor(private route: Router, private fb: FormBuilder, private AuthenticationServices: UserAccessControlService) {

  }

  ngOnInit() {

    this.userLoginForm = this.fb.group({
      userName: [null, [Validators.required]],
      userPassword: [null, [Validators.required, Validators.minLength(5), Validators.maxLength(40)]]
    });
    this.incorrectCredentials = false;
    this.formHeading = 'Login';
  }

  routeToChange(): void {
    this.route.navigate(['register', 'Change']);
  }

  routeToHomepage(): void {

  }

  loginError(): void {
    this.incorrectCredentials = true;
    // this.loginForm;
  }

  get getVariables() {
    return this.userLoginForm;
  }

  /**
   * Submits credentials;
   * if default admin credentials routes to changeCredentials
   * if guest has expired, route to keyPage
   */
  login() {
    if (!this.userLoginForm.invalid) {
      console.log(this.userLoginForm);
      this.AuthenticationServices.authenticateUser(this.userLoginForm.value, this);
    }

    return;
  }
}
