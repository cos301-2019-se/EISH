import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user-model';
import { UserAccessControlService} from 'src/app/services/user/user-access-control.service';
import {Router, ActivatedRoute} from '@angular/router';

@Component({
  selector: 'app-change-credentials',
  templateUrl: './change-credentials.component.html',
  styleUrls: ['./change-credentials.component.css']
})
export class ChangeCredentialsComponent implements OnInit {
  /**
   * Will be used for changing the admin default credentials, user registration and editing user credentials
   */

   /*variables*/
  submissionType: string; // values will be admin, edit or register
  credentialsForm: FormGroup;
  formHeading: string;
  Action: string;
  user = new User();
  incorrectCredentials: boolean;


  constructor(private router: ActivatedRoute,
              private routes: Router,
              private fb: FormBuilder,
              private authenticationServices: UserAccessControlService) {
    if (this.router.snapshot.paramMap.get('regType') === 'Register') {
      this.formHeading = 'Register';
      this.credentialsForm = this.fb.group({
        userName: [null, [Validators.required]],
        userEmail: [null, [Validators.required, Validators.email]],
        userPassword: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(40)]],
        userDeviceName: [null, [Validators.required, Validators.minLength(3), Validators.maxLength(25)]]
      });
      this.Action = 'Submit';
    } else {
      this.formHeading = 'Change Credentials';
      const theForm = this;
      const observable = this.authenticationServices.getUser().subscribe(res => {
        theForm.user.userId = res.userId;
        theForm.credentialsForm = this.fb.group({
          userName: [res.userName, [Validators.required]],
          userEmail: [res.userEmail, [Validators.required, Validators.email]],
          userPassword: [null, [Validators.required, Validators.minLength(8), Validators.maxLength(40)]],
          userDeviceName: [res.userLocationTopic, [Validators.required, Validators.minLength(3), Validators.maxLength(25)]]
        });
      });
      this.Action = 'Submit';
    }
   }

  ngOnInit() {
    this.Action = 'Submit';
  }

  route(route, routeLocation) {
    this.routes.navigate([route, routeLocation]);
  }
  get getVariables() {
    return this.credentialsForm;
  }

  /**
   * Called upon button click. Chooses which function to call based on submissionType
   */
  submit(formData): void {
    if (!this.credentialsForm.invalid) {
      const userLoggedIn = this.authenticationServices.isUserLoggedIn();
      if (userLoggedIn) {
        this.user.userName = formData.value.userName;
        this.user.userEmail = formData.value.userEmail;
        this.user.userPassword = formData.value.userPassword;
        this.user.userLocationTopic = formData.value.userDeviceName;
        this.editCredentials(this.user);
      } else {
        this.registerCredentials(formData.value);
      }
    }

    return;
  }

  /**
   * Carry out change of user credentials
   */
  editCredentials(credentials) {
    return this.authenticationServices.changeCredentials(credentials, this);
  }

  /**
   * Carry out registration of new user
   */
  registerCredentials(credentials) {
    return this.authenticationServices.registerUser(credentials, this);
  }

  error() {

  }
}
