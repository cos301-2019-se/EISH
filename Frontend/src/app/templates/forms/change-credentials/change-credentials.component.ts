import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user-model';
import { UserAccessControlService} from 'src/app/services/user/user-access-control.service';
import {Router, ActivatedRoute} from '@angular/router';
import { pipe } from 'rxjs';
import 'rxjs/add/operator/map';
import { map, catchError } from 'rxjs/operators';

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
  retrievedDetails: any;


  constructor(private router: ActivatedRoute,
              private routes: Router,
              private fb: FormBuilder,
              private authenticationServices: UserAccessControlService) {
    if (this.router.snapshot.paramMap.get('regType') === 'Register') {
      this.formHeading = 'Register';
      this.credentialsForm = this.fb.group({
        userName: ['', [Validators.required]],
        userEmail: ['', [Validators.required, Validators.email]],
        userPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(40)]],
        userLocationTopic: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25)]]
      });
      this.Action = 'Submit';
    } else {
        this.formHeading = 'Change Credentials';

        this.credentialsForm = this.fb.group({
          userName: ['', [Validators.required]],
          userEmail: ['', [Validators.required, Validators.email]],
          userPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(40)]],
          userLocationTopic: ['', [Validators.required, Validators.minLength(3), Validators.maxLength(25)]]

         /* userName: [this.retrievedDetails.userName, [Validators.required]],
          userEmail: [this.retrievedDetails.userEmail, [Validators.required, Validators.email]],
          userPassword: ['', [Validators.required, Validators.minLength(8), Validators.maxLength(40)]],
          userLocationTopic: [this.retrievedDetails.userLocationTopic,
            [Validators.required, Validators.minLength(3), Validators.maxLength(25)]]*/
        });
        this.Action = 'Submit';
    }
   }

  ngOnInit() {
    this.Action = 'Submit';
    this.authenticationServices.getUser().pipe(
      map(  res => {
        this.retrievedDetails = res;
        JSON.stringify(this.retrievedDetails);
      })).subscribe();

  }

  route(route, routeLocation) {
    this.routes.navigate([route, routeLocation]);
  }
  get getVariables() {
    return this.credentialsForm;
  }

  /**
   * Called upon button click. Chooses which function to call based on login status
   */
  submit(): void {
    if (!this.credentialsForm.invalid) {
      const userLoggedIn = this.authenticationServices.isUserLoggedIn();
      if (userLoggedIn) {
        this.editCredentials(this.credentialsForm.value);
      } else {
        this.registerCredentials(this.credentialsForm);
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

  routeToChange() {

  }
}
