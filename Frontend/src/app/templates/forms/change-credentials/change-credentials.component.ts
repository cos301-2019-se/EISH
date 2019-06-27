import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { User } from 'src/app/models/user-model';
import { UserAccessControlService} from 'src/app/services/user/user-access-control.service';
import {Router,ActivatedRoute} from '@angular/router';

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
  formHeading: String;
  Action: String;
  user = new User();
  incorrectCredentials: boolean;
  
  
  constructor(private router: ActivatedRoute,private routes: Router,private fb: FormBuilder,private authenticationServices: UserAccessControlService) {
    if(this.router.snapshot.paramMap.get("regType") == ""){
      this.formHeading = "Change Credentials";
    }else{
      this.formHeading = "Register";
    }
    this.Action = "Submit!";
    this.credentialsForm = this.fb.group({
      'userName':[null,[Validators.required]],
      'userEmail':[null,[Validators.required,Validators.email]],
      'userPassword':[null,[Validators.required,Validators.minLength(8),Validators.maxLength(40)]],
      'userDeviceName':[null,[Validators.minLength(3),Validators.maxLength(25)]]
    });
   }

  ngOnInit() {
    if(this.router.snapshot.paramMap.get("regType") == ""){
      this.formHeading = "Change Credentials";
    }else{
      this.formHeading = "Register";
    }
    this.Action = "Submit!";
    this.credentialsForm = this.fb.group({
      'userName':['',[Validators.required]],
      'userEmail':['',[Validators.required,Validators.email]],
      'userPassword':['',[Validators.required,Validators.minLength(8),Validators.maxLength(40)]],
      'userDeviceName':['',[Validators.minLength(3),Validators.maxLength(25)]]
    });
  }

  get getVariables(){
    return this.credentialsForm.controls;
  }
  /**
   * Called upon button click. Chooses which function to call based on submissionType
   */
  submit(formData){
    if(this.credentialsForm.invalid)
    {
      return;
    }
    else{
      let presence = this.authenticationServices.isUserLoggedIn();
      let result = this.authenticationServices.authenticateUser(formData.value);
      if(presence && result){
        this.editCredentials(formData.value);
        this.routes.navigate(['/dashboard']);
      }else if(presence == false){
        let response = this.registerCredentials(formData.value);
        if(response){
          this.routes.navigate(['/dashboard']);
        }else{
          return;
        }
      }
    }
   }

  /**
   * Carry out change of admin default credentials
   */
  adminCredentials(){

  }

  /**
   * Carry out change of user credentials
   */
  editCredentials(credentials){
    return this.authenticationServices.changeCredentials(credentials);
  }

  /**
   * Carry out registration of new user
   */
  registerCredentials(credentials){
    return this.authenticationServices.registerUser(credentials);
  }
}
