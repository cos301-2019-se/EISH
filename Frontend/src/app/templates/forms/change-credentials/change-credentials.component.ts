import { Component, OnInit } from '@angular/core';

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

  constructor() { }

  ngOnInit() {
  }

  /**
   * Called upon button click. Chooses which function to call based on submissionType
   */
  submit(){
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
