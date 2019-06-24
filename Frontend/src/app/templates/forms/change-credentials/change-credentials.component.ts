import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from '../login/user';

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
  submissionType:String //values will be admin, edit or register
  credentialsForm: FormGroup;
  userType: Array<string> = [
    'admin',
    'guest',
    'resident'
  ];
  Action: String;

  constructor(private fb: FormBuilder) {
    this.credentialsForm = this.fb.group({
      'userName':['',[Validators.required]],
      'userEmail':['',[Validators.required,Validators.EmailValidator]],
      'userType': ['',[Validators.required]],
      'userPassword':['',[Validators.required,Validators.minLength(8),Validators.maxLength(40)]],
      'userLocationTopic':['',[Validators.minLength(3),Validators.maxLength(25)]]
    });
   }

  ngOnInit() {
    
  }

  /**
   * Called upon button click. Chooses which function to call based on submissionType
   */
  submit(form){
    //call sanitize and validate

    //when both are true call one of latter function
  }

  /**
   * Carry out change of admin default credentials
   */
  adminCredentials(){

  }

  /**
   * Carry out change of user credentials
   */
  editCredentials(){

  }

  /**
   * Carry out registration of new user
   */
  registerCredentials(){

  }
}
